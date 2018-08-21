package com.realmo.transitionanimationdemo;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 共享元素动画Demo   api>=21
 * 1.styles.xml 添加允许内容过度属性
 * <item name="android:windowContentTransitions">true</item>
 * 2. android:transitionName="xxx" 对成出现绑定
 * 3. ActivityOptionsCompat.makeSceneTransitionAnimation 绑定需要共享元素动画的View等设置
 * 4.startActivity
 */
public class MainActivity extends AppCompatActivity  {

    private ImageView iv;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = findViewById(R.id.imageview_main);
        tv = findViewById(R.id.tv_main);


    }


    /**
     * 单个共享元素
     */
    private void singleToActivityB(){
        Intent intent = new Intent(this, ActivityB.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat optionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this, iv, "test_image");
            startActivity(intent,optionsCompat.toBundle());
        }else{
            startActivity(intent);
        }
    }

    /**
     * 多个共享元素
     */
    private void moreToActivityB(){
        Intent intent = new Intent(this, ActivityB.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Pair<View, String> pair1 = new Pair<View, String>(iv, "test_image");
            Pair<View, String> pair2 = new Pair<View, String>(tv, "test_tv");
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,pair1,pair2);
            startActivity(intent,options.toBundle());

        }else{
            startActivity(intent);
        }
    }

    public void single(View view) {
        singleToActivityB();
    }

    public void more(View view) {
        moreToActivityB();
    }
}
