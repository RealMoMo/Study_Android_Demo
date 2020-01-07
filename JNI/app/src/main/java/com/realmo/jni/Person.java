package com.realmo.jni;

import android.util.Log;

public class Person {

    private final static String TAG = Person.class.getSimpleName();

    public Person(){
        Log.d(TAG,"no par consturce init");
    }

    public Person(int test){
        Log.d(TAG,"have par consturce init");
    }

    public void setStudent(Student student) {
        Log.d(TAG,student.toString());
    }
}
