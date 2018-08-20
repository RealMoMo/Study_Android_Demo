package com.realmo.activitylifecyclecallbacksdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startA(View view) {
        Intent intent = new Intent(this,ActivtyA.class);
        startActivity(intent);
    }

    public void startB(View view) {
        Intent intent = new Intent(this,ActivityB.class);
        startActivity(intent);
    }
}
