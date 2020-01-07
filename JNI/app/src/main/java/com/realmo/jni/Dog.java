package com.realmo.jni;

import android.util.Log;

public class Dog {
    private static final String TAG = Dog.class.getSimpleName();
    public Dog() {
        Log.d(TAG, "Dog: 构造方法被C++中直接实例化了....");
    }

}
