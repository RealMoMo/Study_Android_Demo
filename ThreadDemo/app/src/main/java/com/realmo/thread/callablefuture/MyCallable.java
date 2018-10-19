package com.realmo.thread.callablefuture;

import android.util.Log;

import java.util.concurrent.Callable;

/**
 * @author Realmo
 * @version 1.0.0
 * @name Demo
 * @email momo.weiye@gmail.com
 * @time 2018/10/19 14:56
 * @describe
 */
public class MyCallable implements Callable {
    @Override
    public Integer call(){
        int i = 0;
        for (; i < 100; i++) {
            Log.d("realmo",Thread.currentThread().getName() + "== i:" + i);
        }
        return i;
    }
}
