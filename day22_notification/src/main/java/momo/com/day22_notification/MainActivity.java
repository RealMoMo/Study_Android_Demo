package momo.com.day22_notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //点击，发送普通通知
    public void sendNormal(View view) {
        //NOTIFICATION_SERVICE是Context的内容
        //1.获取NotificationManager
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //2.创建Notification对象
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        //设置小图标,必须设置SmallIcon和Ticker否则不弹出通知
        builder.setSmallIcon(R.mipmap.sing_icon)
                .setTicker("中国银行通知")
                .setContentText("工资----？？？？")
                .setContentTitle("工资详情");

        //设置通知铃声……
        builder.setDefaults(Notification.DEFAULT_ALL);
        //创建对象
        Notification notification = builder.build();
        //3.通过manager发通知　
        manager.notify(100, notification);
    }

    //点击，发送通知，点击通知能跳转
    public void sendGoto(View view) {
        //NOTIFICATION_SERVICE是Context的内容
        //1.获取NotificationManager
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //2.创建Notification对象
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        //设置小图标
        builder.setSmallIcon(R.mipmap.sing_icon)
                .setTicker("震惊！一对情侣竟然...")
                .setContentText("上课不学习，并找到满意的工作")
                .setContentTitle("月薪过万");

        //跳转到指定的Activity
        Intent intent = new Intent(this, MainActivity.class);
        //Intent-->PenddingIntent
        /**
         * 参数1： 上下文环境对象Context
         *
         * 参数2：Intent 的识别码
         *
         * 参数3：intent
         *
         * 参数4：Flag,表示当前的PendingIntent会把前面创建的PendingIntent更新
         */
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                1,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        //builder关联PendigngIntent
        builder.setContentIntent(pendingIntent);

        //设置点击后，自动取消
        builder.setAutoCancel(true);

        //设置通知铃声……
        builder.setDefaults(Notification.DEFAULT_ALL);
        //创建对象
        Notification notification = builder.build();
        //3.通过manager发通知　
        manager.notify(101, notification);


    }

    //点击，发送进度条的通知
    public void sendProgess(View view) {
        //NOTIFICATION_SERVICE是Context的内容
        //1.获取NotificationManager
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //2.创建Notification对象
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        //设置小图标
        builder.setSmallIcon(R.mipmap.sing_icon)
                .setTicker("下载提示")
                .setContentTitle("当前下载进度");

        //设置进度条
        /*
        * 设置进度：
        * 参数一：最大值
        * 参数二：当前值
        * 参数三：是否精确进度，false表示精确，true表示不精确
        * */
        builder.setProgress(100, 66, false);

        //设置通知铃声……
        builder.setDefaults(Notification.DEFAULT_ALL);
        //创建对象
        Notification notification = builder.build();
        //3.通过manager发通知　
        manager.notify(102, notification);


    }

    //点击，发送带大图片的通知
    public void sendBig(View view) {
        //1.创建Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.hmv).setTicker("大图标通知");
        //2.创建BigPictureStyle
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle(builder);
        //3.设置BigPictureStyle
        //标题
        bigPictureStyle.setSummaryText("大图标内容文本")
                .setBigContentTitle("大图标标题");
        //设置大图片,bitmap对象
        //把R.drawable.id-->Bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.big);
        bigPictureStyle.bigPicture(bitmap);
        //4.发送
        Notification notification = builder.build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(103, notification);


    }

    //点击，发送自定义视图的通知
    public void sendMyView(View view) {
        //1.创建Builder，设置smallIcon,Ticker
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.hmv)
                .setTicker("正在播放hahahaha");
        //2.创建远程视图 RemoteViews
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.remoteview_layout);
        remoteViews.setTextViewText(R.id.tv_title, "BangBangBang...");

        //按钮的点击事件,取消通知显示在通知栏
        //构建一个广播的PendingIntent,创建广播接收器,业务逻辑由广播接收器来处理
        Intent intent = new Intent("com.qf.action.CANCEL");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.btn_play, pendingIntent);

        //3.关联Builder与RemoteViews
        builder.setContent(remoteViews);

        Notification notification = builder.build();
        //设置常驻标识,时刻在通知栏
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        //4.获取Manager
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //5.发送
        notificationManager.notify(104, notification);


    }
}
