package com.realmo.annotation.value_constraints;

import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.Size;

/**
 * @author Realmo
 * @version 1.0.0
 * @name 值约束
 * @email momo.weiye@gmail.com
 * @time 2018/7/12 10:06
 * @describe
 */
public class ValueConstraintsAnnotation {

    public void setSizeString(@Size(min = 1,max =2)String str){

    }

    public void setIntRange(@IntRange(from = 1,to = 10)int i){

    }

    public void setFloatRange(@FloatRange(from = 1.0f,to = 10.0f)float f){

    }


}
