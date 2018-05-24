package com.realmo.androiddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextClock;


/**
 * TextClock 建议布局均设置:
 * 12hourformat与24hourformat
 */
public class MainActivity extends AppCompatActivity {

    private TextClock tc12,tc24;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tc12 = findViewById(R.id.test_12);
        tc24 = findViewById(R.id.test_24);

    }


    @Override
    protected void onResume() {
        super.onResume();

        Log.d("realmo","tc12.is24HourModeEnabled()"+tc12.is24HourModeEnabled());
        Log.d("realmo","tc24.is24HourModeEnabled()"+tc24.is24HourModeEnabled());

        Log.d("realmo","tc12.getFormat12Hour()"+tc12.getFormat12Hour());
        Log.d("realmo","tc12.getFormat24Hour()"+tc12.getFormat24Hour());
        Log.d("realmo","tc24.getFormat12Hour()"+tc24.getFormat12Hour());
        Log.d("realmo","tc24.getFormat24Hour()"+tc24.getFormat24Hour());
    }
}
