package com.realmo.annotation.callsuper;

import android.util.Log;

/**
 * @author Realmo
 * @version 1.0.0
 * @name Demo
 * @email momo.weiye@gmail.com
 * @time 2018/7/12 10:18
 * @describe
 */
public class Son extends Father {

    private String name = "Son";
    @Override
    public void getMemberName() {
        super.getMemberName(); //父类该方法加了CallSuper,需调用父类方法。否则，报警告
        Log.d("realmo",Son.class.getSimpleName()+" member name:"+name);
    }
}
