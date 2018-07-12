package com.realmo.annotation.thread;

import android.support.annotation.BinderThread;
import android.support.annotation.MainThread;
import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;
import android.util.Log;

/**
 * @author Realmo
 * @version 1.0.0
 * @name Demo
 * @email momo.weiye@gmail.com
 * @time 2018/7/11 22:53
 * @describe
 */
public class ThreadAnnotation {

    @UiThread
    public void workOnUiThread(){
       logThreadInfo();
    }

    @MainThread
    public void workOnMainThread(){
        logThreadInfo();
    }

    @WorkerThread
    public  void workOnWorkerThread(){
        logThreadInfo();
    }


    @BinderThread
    public void workOnBinderThread(){
        logThreadInfo();
    }


    private void logThreadInfo(){
        Log.d("realmo","thread name:"+Thread.currentThread().getName());
    }

}
