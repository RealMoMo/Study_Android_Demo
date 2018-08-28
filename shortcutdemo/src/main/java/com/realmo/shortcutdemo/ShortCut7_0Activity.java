package com.realmo.shortcutdemo;


import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.pm.ShortcutManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Arrays;

/**
 *
 * 7.0系统创建Activity快捷方式
 *
 * 本activity是动态注册
 *
 * 静态见AndroidManifest中添加
 *  <meta-data
    android:name="android.app.shortcuts"
    android:resource="@xml/shortcut"/>

 */

public class ShortCut7_0Activity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_7);


        findViewById(R.id.create_7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createShortCut();
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    private void createShortCut() {
            //获取系统服务得到ShortcutManager对象 (若考虑兼容性可用ShortcutManagerCompat)
        ShortcutManager  systemService = getSystemService(ShortcutManager.class);

            if (Build.VERSION.SDK_INT >= 25) {

                //设置Intent跳转逻辑
                Intent intent = new Intent(ShortCut7_0Activity.this, ShortCutActvivity.class);
                intent.setAction(Intent.ACTION_VIEW);

                //设置ID
                ShortcutInfo shortcutInfo = new ShortcutInfo.Builder(this, "onlyId")
                        //设置短标题
                        .setShortLabel("雅蠛蝶")
                        //设置长标题
                        .setLongLabel("江山留胜迹")
                        //设置icon
                        .setIcon(Icon.createWithResource(this, R.mipmap.momo))
                        //设置Intent
                        .setIntent(intent)
                        .build();
                //这样就可以通过长按图标显示出快捷方式了
                systemService.setDynamicShortcuts(Arrays.asList(shortcutInfo));
        }


    }
/*

    Intent secondIntent = new Intent(ShortCut7_0Activity.this, TargetActivity.class);
            intent.setAction(Intent.ACTION_VIEW);

    ShortcutInfo info = new ShortcutInfo.Builder(this, "onlyId2")
            .setShortLabel("雅蠛蝶")
            .setLongLabel("我辈复登临")
            .setIcon(Icon.createWithResource(this, R.drawable.yuanbao))
            .setIntent(intent)
            .build();
            systemService.setDynamicShortcuts(Arrays.asList(info));*/
}
