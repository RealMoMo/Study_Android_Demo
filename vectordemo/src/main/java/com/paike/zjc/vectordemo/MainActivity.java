package com.paike.zjc.vectordemo;

import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileOutputStream;


/**
 * Vector Drawable Demo
 * 1.Vector的drawable文件放在drawable文件夹
 * 2.定义Vector的动画放在animator文件夹
 * 3.定义Vector真正动画执行到Vector drawalbe对应path动画 放在drawable文件夹
 *   xml根标签：animated-vector
 * 4.gralde需添加
 *  defaultConfig {
 *       vectorDrawables.useSupportLibrary = true
 *  ｝
 *  5.布局需设置特定的属性
 *  如：
 *  ImageView app:srcCompat="@drawable/test"
 *
 *  参考：
 *  例子：
 *  https://blog.csdn.net/gjnm820/article/details/78590313
 *  外文例子：（大干货）
 *  https://www.androiddesignpatterns.com/2016/11/introduction-to-icon-animation-techniques.html
 *  Vector pathdata属性介绍：
 *  https://blog.csdn.net/n4167/article/details/79184286
 *
 *
 *  备注：
 *  VectorDrawable minSDK>=21
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn1,btn2;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();


    }

    private void initView() {
        btn1 = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);
        iv = findViewById(R.id.imageView);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:{
                if(iv.getDrawable() instanceof Animatable){
                    ((Animatable)iv.getDrawable()).start();
                }

            }break;
            case R.id.button2:{
                if(iv.getDrawable() instanceof Animatable){
                    ((Animatable)iv.getDrawable()).stop();
                }

                iv.getDrawable().setTint(Color.BLUE);
            }break;
            default:{

            }break;
        }
    }
}
