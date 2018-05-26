package com.hht.test;

import android.app.Application;

/**
 * @author Realmo
 * @version 1.0.0
 * @name Demo
 * @email momo.weiye@gmail.com
 * @time 2018/5/26 10:12
 * @describe
 */
public class TestApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
    }
}
