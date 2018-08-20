package com.realmo.activitylifecyclecallbacksdemo;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

/**
 * @author Realmo
 * @version 1.0.0
 * @name ActivityLifecycleCallbacks Demo
 * @email momo.weiye@gmail.com
 * @time 2018/8/20 15:35
 * @describe
 */
public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {

    private static final String TAG = "realmo";

    @Override
    public void onCreate() {
        super.onCreate();
        //注册Activity生命周期监听
        this.registerActivityLifecycleCallbacks(this);

        //对应有反注册
        //this.unregisterActivityLifecycleCallbacks(this);

    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
       Log.d(TAG,activity.getClass().getName() + "  onActivityCreated");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.d(TAG,activity.getClass().getName() + "  onActivityStarted");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.d(TAG,activity.getClass().getName() + "  onActivityResumed");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.d(TAG,activity.getClass().getName() + "  onActivityPaused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.d(TAG,activity.getClass().getName() + "  onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.d(TAG,activity.getClass().getName() + "  onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.d(TAG,activity.getClass().getName() + "  onActivityDestroyed");
    }


}
