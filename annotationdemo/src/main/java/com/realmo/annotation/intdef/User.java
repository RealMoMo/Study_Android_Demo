package com.realmo.annotation.intdef;

import android.support.annotation.IntDef;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author Realmo
 * @version 1.0.0
 * @name IntDefault & StringDef
 * @email momo.weiye@gmail.com
 * @time 2018/7/11 16:06
 * @describe
 */
public class User {

    /**
     *   定义
     @Retention(SOURCE)
     @Target({ANNOTATION_TYPE})
     public @interface IntDef {

     int[] value() default {};

     boolean flag() default false;
}
     */


    /**IntDef flag作用  (比较少用，主要是定义的常量比较多会用)
     * https://stackoverflow.com/questions/34583796/android-intdef-for-flags-how-to-use-it
     *
     *  then you can use bit operations to combine these int flags(like | ,&...).
     *  可以通过位运算操作符，组合更多常量
     */

    private int userType;

      //两种写法都OK 看个人习惯

      //第一种写法
//    public static final int STUDENT = 0;
//    public static final int IT_WORKER = 1;
//    public static final int OTHER = 2;
//
//
//    @IntDef({STUDENT,IT_WORKER,OTHER})
//    @Retention(RetentionPolicy.SOURCE)
//    public @interface UserType{}


    //第二种写法
    @IntDef({UserType.STUDENT,UserType.IT_WORKER,UserType.OTHER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface UserType{
        int STUDENT = 0;
        int IT_WORKER = 1;
        int OTHER = 2;
    }

    @UserType
    public int getUserType() {
        return userType;
    }

    public void setUserType(@UserType int userType) {
        this.userType = userType;
    }

    /**
     * StringDef定义
     @Retention(SOURCE)
     @Target({ANNOTATION_TYPE})
     public @interface StringDef {

     String[] value() default {};
     }
     */



}
