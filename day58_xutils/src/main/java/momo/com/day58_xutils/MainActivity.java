package momo.com.day58_xutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * XUtils的简单使用(国人开发，膜拜)
 *
 * github:
 * https://github.com/wyouflf/xUtils3
 *
 * 建议自己脑补：java的反射
 */

//绑定布局
//@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    //绑定viewId
    @ViewInject(value = R.id.tv)
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //反射注入
//        x.view().inject(this);

        //实现xutils绑定控件注解的原理方法，只做绑定tv。
        injectView();

        tv.setText("realmo");

        //实现xutils绑定控件的点击方法
        injectEvent();
    }

    //绑定viewId的点击方法，其方法必须是private
    @Event(R.id.btn)
    private void changeText(View view){
        tv.setText("realmo change");
    }

    //实现xutils绑定控件注解的原理方法
    private void injectView(){
        Class clazz = getClass();
        try {
            //找到名为tv的成员变量
            Field field = clazz.getDeclaredField("tv");
            //获取tv的注解
            ViewInject viewInject = field.getAnnotation(ViewInject.class);
            //获取tv的viewid
            int viewId = viewInject.value();
            //绑定该控件
            tv = (TextView) findViewById(viewId);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


    //实现xutils绑定控件的点击方法
    private void injectEvent(){
        Class clazz = getClass();
        try {
            //参数2：方法的参数.class的Class数组
            final Method method = clazz.getDeclaredMethod("changeText",new Class[]{View.class});
            //允许访问私有方法
            method.setAccessible(true);
            Event event = method.getAnnotation(Event.class);
            //可能多个控件共用该方法，所以返回一个viewId的int数组
            int[] ids = event.value();
            //因为我们知道只有一个控件用了该方法，所以用ids[0]找到该控件，并监听其点击方法
            findViewById(ids[0]).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        //invoke（调用）就是调用Method类代表的方法
                        method.invoke(MainActivity.this,view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

}
