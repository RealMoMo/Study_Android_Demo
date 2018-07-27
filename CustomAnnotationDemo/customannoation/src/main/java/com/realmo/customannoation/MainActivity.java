package com.realmo.customannoation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hht.annotationprocessor.ClassAnnotation;
import com.realmo.customannoation.bean.Person;
import com.realmo.customannoation.myannotation.RunTimeAnnotation;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    //test RetentionPolicy.Runtime annotation
    @RunTimeAnnotation(name = "momo",age = 66)
    private Person person;

    //test RetentionPolicy.Class annotation (code in AnnotationProcessor java module)
    @ClassAnnotation(value = R.id.tv)
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testRuntimeAnnotation();

    }

    /**
     * 可参考 https://blog.csdn.net/jsonChumpKlutz/article/details/78599277
     */
    private void testRuntimeAnnotation() {
        try {
            //获取要解析的类
            Class clz = Class.forName("com.realmo.customannoation.MainActivity");
            //获取所有Field
            Field[] fields = clz.getDeclaredFields();
            for (Field field : fields ){
                //获取Field上有RunTimeAnnotation注解的字段
                boolean annotationPresent = field.isAnnotationPresent(RunTimeAnnotation.class);
                if(annotationPresent){
                    RunTimeAnnotation annotation = field.getAnnotation(RunTimeAnnotation.class);
                    Log.d("realmo","age:"+annotation.age()); //66
                    Log.d("realmo","name:"+annotation.name()); //momo
                    Log.d("realmo","person is null:"+(person == null)); //true

                    person = new Person(annotation.name(),annotation.age());
                    Log.d("realmo","person is null:"+(person == null)); //false

                }

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
