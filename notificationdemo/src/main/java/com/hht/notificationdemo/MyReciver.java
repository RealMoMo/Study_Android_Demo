package com.hht.notificationdemo;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2016/11/8 0008.
 */
public class MyReciver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        //收到取消通知的广播
        //1.构建NotificationManager
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //2.manager.cancel();
        manager.cancel(104);

    }
}
