package momo.com.day64_toucheventdemo;

import android.view.MotionEvent;


public class ActionUtil
{

    public static String getActionName(int action)
    {
        String actionName = "";
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                actionName = "ACTION_DOWN";
                break;
            case MotionEvent.ACTION_MOVE:
                actionName = "ACTION_MOVE";
                break;
            case MotionEvent.ACTION_UP:
                actionName = "ACTION_UP";
                break;
        }
        return actionName;
    }
}
