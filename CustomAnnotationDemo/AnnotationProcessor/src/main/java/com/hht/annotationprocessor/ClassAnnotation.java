package com.hht.annotationprocessor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Realmo
 * @version 1.0.0
 * @name Demo
 * @email momo.weiye@gmail.com
 * @time 2018/7/27 9:51
 * @describe
 */

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.FIELD,ElementType.METHOD})
public @interface ClassAnnotation {


    int value() default 1;
}
