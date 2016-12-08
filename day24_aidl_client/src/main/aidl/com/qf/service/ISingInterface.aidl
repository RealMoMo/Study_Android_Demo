// ISingInterface.aidl
package com.qf.service;

// Declare any non-default types here with import statements

interface ISingInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);


    void sing(String name);


    int add(int a,int b);
}
