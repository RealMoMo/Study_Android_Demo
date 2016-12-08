package momo.com.day24_messenger_service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2016/11/10 0010.
 */
public class MyService extends Service {


    /*
    * 通过Messenger来实现跨进程服务
    *
    * 注意：not Message
    * */
    private Messenger messenger = new Messenger(new Handler(){
        @Override
        public void handleMessage(Message msgFromClient) {
            super.handleMessage(msgFromClient);
            //msgFromClient :从客户端来的
            Log.d("Tag","Service收到客户端的消息："+msgFromClient.what);
            //获取客户端传入的data
            Bundle bundle = msgFromClient.getData();
            Log.d("Tag", "Service 收到客户端的消息 bundle=" + bundle.getString("key"));

            //创建消息
            Message msgToClient = Message.obtain();
            msgToClient.what=100;
            msgToClient.arg1=66;

            //带个参数
//            msgToClient.obj = "惟有套路得人心";      行不通,obj需要序列化
            Bundle bundleToClient = new Bundle();
            bundleToClient.putString("key", "惟有套路得人心");
            msgToClient.setData(bundleToClient);
            //模拟在操作，延迟一定时间，向客户端发消息。
            SystemClock.sleep(2000);

            try {
                //自己发，自己处理
//                messenger.send(msgToClient);

                //客户端发，客户端来处理
                msgFromClient.replyTo.send(msgToClient);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    });

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
