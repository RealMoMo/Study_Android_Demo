package com.realmo.annotation.other;

import android.Manifest;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Keep;
import android.support.annotation.RequiresPermission;
import android.support.annotation.VisibleForTesting;
import android.view.View;
import android.widget.TextView;

/**
 * @author Realmo
 * @version 1.0.0
 * @name Other Annotation
 * @email momo.weiye@gmail.com
 * @time 2018/7/12 10:49
 * @describe
 */
public class OtherAnnotation {

    //1. @ColorInt  传一个RGB或者ARGB的颜色值
    private Context context;
    private TextView tv ;
    public void setTextColor(@ColorInt int color){
        tv.setTextColor(color);
    }

    public void setTextColor2(@ColorRes int color){
        tv.setTextColor(context.getResources().getColor(color));
    }

    //============================

    //2.RequiresPermission
    @RequiresPermission(Manifest.permission.SET_WALLPAPER)
    public void setWallpaper(){

    }

    //需多个权限
    @RequiresPermission(allOf = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE})
    public void setAllOfPermission(){

    }

    //需其中至少一个权限
    @RequiresPermission(anyOf = { Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION})
    public void setAnyOfPermission(){

    }

    //=======================

    //3.VisibleForTesting
    @VisibleForTesting
    private void forTest(){

    }

    //=================

    //4.
    @Keep
    private void notProguard(){

    }

    //===============
}
