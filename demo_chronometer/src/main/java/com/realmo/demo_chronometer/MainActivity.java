package com.realmo.demo_chronometer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Chronometer;


/**
*
*	Chronometer控件默认格式 mm:ss
*	当达到小时以上，格式会自动变化 hh:mm:ss
**/
public class MainActivity extends AppCompatActivity {

    private Chronometer chronometer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        chronometer  = findViewById(R.id.chronometer);
        chronometer.start();
    }
}
