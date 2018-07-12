package com.realmo.annotation.callsuper;

import android.support.annotation.CallSuper;
import android.util.Log;

/**
 * @author Realmo
 * @version 1.0.0
 * @name Demo
 * @email momo.weiye@gmail.com
 * @time 2018/7/12 10:18
 * @describe
 */
public class Father {

    protected String name = "Father";

    @CallSuper
    public void getMemberName(){
        Log.d("realmo",Father.class.getSimpleName()+" member name:"+name);
    }
}
