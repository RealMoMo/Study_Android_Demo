package top.vimerzhao.plugin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


public abstract class PluginActivity extends Activity implements Pluginable, Attachable<Activity> {
    public final static String TAG = PluginActivity.class.getSimpleName();
    protected Activity mProxyActivity;
    private Resources mResources;
    PluginApk mPluginApk;

    @Override
    public void attach(Activity proxy, PluginApk apk) {
        mProxyActivity = proxy;
        mPluginApk = apk;
        mResources = apk.pluginRes;
    }

    @Override
    public void setContentView(int layoutResID) {
        mProxyActivity.setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        mProxyActivity.setContentView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        mProxyActivity.setContentView(view, params);
    }


    @Override
    public View findViewById(int id) {
        return mProxyActivity.findViewById(id);
    }

    @Override
    public Resources getResources() {
        return mResources;
    }

    @Override
    public WindowManager getWindowManager() {
        return mProxyActivity.getWindowManager();
    }

    @Override
    public ClassLoader getClassLoader() {
        return mProxyActivity.getClassLoader();
    }

    @Override
    public Context getApplicationContext() {
        return mProxyActivity.getApplicationContext();
    }

    @Override
    public MenuInflater getMenuInflater() {
        return mProxyActivity.getMenuInflater();
    }


    @Override
    public Window getWindow() {
        return mProxyActivity.getWindow();
    }

    @Override
    public Intent getIntent() {
        return mProxyActivity.getIntent();
    }

    @Override
    public LayoutInflater getLayoutInflater() {
        return mProxyActivity.getLayoutInflater();
    }

    @Override
    public String getPackageName() {
        return mPluginApk.packageInfo.packageName;
    }


    @Override
    public void onCreate(Bundle bundle) {
        // DO NOT CALL super.onCreate(bundle)
        // following same
        VLog.log(TAG + ": onCreate");
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
    }
}
