package momo.com.day64_toucheventdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;


public class CustomView extends TextView
{
    public CustomView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
    {
        Log.i("info",
                "CustomView===dispatchTouchEvent==="
                        + ActionUtil.getActionName(event.getAction()));
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        Log.i("info",
                "CustomView===onTouchEvent==="
                        + ActionUtil.getActionName(event.getAction()));
        return super.onTouchEvent(event);
    }
}
