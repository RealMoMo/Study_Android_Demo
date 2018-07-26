package com.realmo.demo_productflavor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * 多渠道设置需配置地方：
 * 1.项目结构     （根据渠道在src目录下，建相应的渠道文件夹）
 * 2.build.gradle (主要配置的地方)
 * 3.AndroidManifeset
 */

/**
 * 本项目主要展示多渠道设置的不同点：
 * 1.applicationId versionName等
 * 2.Res资源(color dimen text mipmap)
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
