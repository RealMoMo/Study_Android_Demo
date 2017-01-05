package momo.com.day64_toucheventdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;


public class CustomViewGroup extends RelativeLayout
{
    public CustomViewGroup(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
    {
        Log.i("info",
                "CustomViewGroup===dispatchTouchEvent==="
                        + ActionUtil.getActionName(event.getAction()));
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
        Log.e("info",
                "CustomViewGroup===onInterceptTouchEvent==="
                        + ActionUtil.getActionName(event.getAction()));
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        Log.i("info",
                "CustomViewGroup===onTouchEvent==="
                        + ActionUtil.getActionName(event.getAction()));
        return super.onTouchEvent(event);
    }
}
