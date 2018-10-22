package top.vimerzhao.plugindemo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import java.io.File;

import top.vimerzhao.plugin.Constants;
import top.vimerzhao.plugin.PluginManager;
import top.vimerzhao.plugin.VLog;


public class MainActivity extends Activity {
    public final static String PLUGIN_NAME = "plugin.apk";
    public final static String PLUGIN_PACKAGE_NAME = "top.vimerzhao.imageloader";
    public final static String PLUGIN_CLAZZ_NAME = "top.vimerzhao.imageloader.MainActivity";
    PluginManager mPluginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(Constants.PACKAGE_NAME, PLUGIN_PACKAGE_NAME);
                intent.putExtra(Constants.PLUGIN_CLASS_NAME, PLUGIN_CLAZZ_NAME);
                mPluginManager.startActivity(intent);
            }
        });
        Utils.verifyStoragePermissions(this);

        PluginManager.init(getApplicationContext());
        mPluginManager = PluginManager.getInstance();
        String pluginApkPath = Environment.getExternalStorageDirectory() +
                File.separator + "plugins" + File.separator + PLUGIN_NAME;


        VLog.log("can read: " + Environment.getExternalStorageDirectory().canRead());
        VLog.log(pluginApkPath);
        mPluginManager.loadApk(pluginApkPath);
    }
}
