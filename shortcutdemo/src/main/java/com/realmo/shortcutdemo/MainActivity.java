package com.realmo.shortcutdemo;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    //创建桌面快捷方式
    private void createShortCut(){
        //创建Intent对象
        Intent shortcutIntent = new Intent();

        //设置点击快捷方式，进入指定的Activity
        //注意：因为是从Lanucher中启动，所以这里用到了ComponentName
        //其中new ComponentName这里的第二个参数，是Activity的全路径名，也就是包名类名要写全。

        shortcutIntent.setComponent(new ComponentName(this.getPackageName(), "com.realmo.shortcutdemo.ShortCutActvivity"));

        //给Intent添加 对应的flag
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS|Intent.FLAG_ACTIVITY_NEW_TASK);

        Intent resultIntent = new Intent();
        // Intent.ShortcutIconResource.fromContext 这个就是设置快捷方式的图标

        resultIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(this,
                        R.mipmap.momo));
        //启动的Intent
        resultIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);

        //这里可以设置快捷方式的名称
        resultIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "MoMo");
        //不允许重复创建
        resultIntent.putExtra("duplicate", false);
        //设置Action
        resultIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");

        //发送广播、通知系统创建桌面快捷方式
        sendBroadcast(resultIntent);
    }



    /**
     * 删除程序的快捷方式 （碎片化和厂商定制 测试大部分机型不生效）  手动删除吧~~~
     *
     */
    private void delShortcut(){
        Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");

        //快捷方式的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, "MoMo");

        ComponentName comp = new ComponentName(this.getPackageName(), "com.realmo.shortcutdemo.ShortCutActvivity");
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp));

        sendBroadcast(shortcut);

    }


    public void createShotCut(View view) {
        createShortCut();
    }

    public void delShortCut(View view) {
        delShortcut();
    }

    public void android7(View view) {
        Intent intent = new Intent(this,ShortCut7_0Activity.class);
        startActivity(intent);
    }

    public void android8(View view) {
        Intent intent = new Intent(this,ShortCut8_0Activity.class);
        startActivity(intent);
    }
}
