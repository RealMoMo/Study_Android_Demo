package com.realmo.annotation.res;

import android.support.annotation.AnimRes;
import android.support.annotation.AnimatorRes;
import android.support.annotation.AnyRes;
import android.support.annotation.ArrayRes;
import android.support.annotation.AttrRes;
import android.support.annotation.BoolRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.InterpolatorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.annotation.RawRes;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.annotation.StyleableRes;
import android.support.annotation.TransitionRes;

import com.realmo.annotation.R;

/**
 * @author Realmo
 * @version 1.0.0
 * @name Res Annotation
 * @email momo.weiye@gmail.com
 * @time 2018/7/11 17:36
 * @describe
 */
public class ResAnnotation {

    //任何资源类型
    @AnyRes
    private int anyRes = R.mipmap.ic_launcher;

    //动画
    @AnimRes
    private int animRes = R.anim.anim_test;

    //TODO
    //animator资源类型
    @AnimatorRes
    private int animatorRes;

    //数组资源类型
    @ArrayRes
    private int arrayRes = R.array.arrayRes;

    //自定义属性资源类型
    @AttrRes
    private int attrRes = R.attr.actionBarDivider;

    //bool类型资源类型
    @BoolRes
    private int boolRes = R.bool.boolRes;

    //Color资源类型
    @ColorRes
    private int colorRes = R.color.colorAccent;

    //dimen资源类型
    @DimenRes
    private int dimenRes = R.dimen.dimenRes;

    //图片资源类型
    @DrawableRes
    private int drawableRes = R.mipmap.ic_launcher;

    //id资源
    @IdRes
    private int idRes = R.id.all;

    //TODO
    //动画插值器资源
    @InterpolatorRes
    private int interpolatorRes;

    //layout资源
    @LayoutRes
    private int layoutRes = R.layout.activity_main;

    //TODO
    //menu资源
    @MenuRes
    private int menuRes;

    //TODO
    //Raw资源
    @RawRes
    private int rawRes;

    //字符串资源
    @StringRes
    private int strRes = R.string.app_name;

    //Style资源
    @StyleRes
    private int styleRes = R.style.AppTheme;

    //StyleableRes
    @StyleableRes
    private int styleableRes = R.styleable.attrRes_test1;

    //TODO
    //TransitionRes资源
    @TransitionRes
    private int transitionRes;

    //TODO
    //Xml资源
    private int xmlRes;

}
