package com.realmo.jni;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());

        int[] intArrays = new int[]{10,20,30,44,55};
        String[] strArrays = new String[]{"a","b","c","d"};
        test(666,"realmo",intArrays,strArrays);

        for (int array : intArrays) {
            Log.d("momo","java int array value:"+array);
        }


        Student student = new Student();
        student.age = 26;
        student.name="realmo";
        putStudent(student);


        testObject();


        createObject();
        createObject();
        releaseObject();
        createObject();


        registerJava01("动态注册jni");
        registerJava02(99);

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();


    public native void test(int number,String text,int[] intArrays,String[] strArrays);

    public native void putStudent(Student student);

    public native void testObject();

    public native void createObject();

    public native void releaseObject();


    // 动态注册的函数
    public native void registerJava01(String text);
    public native void registerJava02(int number);
}
