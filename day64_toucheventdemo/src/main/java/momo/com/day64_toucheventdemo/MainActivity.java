package momo.com.day64_toucheventdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
    {
        Log.i("info",
                "Activity===dispatchTouchEvent==="
                        + ActionUtil.getActionName(event.getAction()));
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        Log.i("info",
                "Activity===onTouchEvent==="
                        + ActionUtil.getActionName(event.getAction()));
        return super.onTouchEvent(event);
    }

}
