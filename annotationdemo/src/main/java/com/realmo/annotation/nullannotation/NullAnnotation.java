package com.realmo.annotation.nullannotation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author Realmo
 * @version 1.0.0
 * @name Demo
 * @email momo.weiye@gmail.com
 * @time 2018/7/11 21:46
 * @describe
 */
public class NullAnnotation {

    private String nonNull ;

    private String nullable ;

    /**
     *
     * @NonNull:不能为空
     */
    public void setNonNull(@NonNull String str){
        nonNull = str;
    }

    public String getNonNull(){
        return nonNull;
    }

    /**
     *
     * @Nullable:可以为空
     */
    public void setNullable(@Nullable String str){
        nullable = str;
    }

    public String getNullable(){
        return nullable;
    }
}
