package momo.com.day24_messenger_client;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    //声明Messenger，用来向远程服务发数据
    Messenger messengerService ;

    //声明ServiceConnnection
    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messengerService = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //绑定服务
        Intent intent = new Intent();
        ComponentName name = new ComponentName("momo.com.day24_messenger_service",
                "momo.com.day24_messenger_service.MyService");
        intent.setComponent(name);
        bindService(intent,conn,BIND_AUTO_CREATE);
    }

    //定义一个Messenger，用来接收远程的message
    Messenger messengerClient = new Messenger(new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d("Tag","客户端收到的消息："+msg.what);
            Log.d("Tag", "客户端收到的消息 arg1:" + msg.arg1);
            //获取服务端传入的data
            Bundle data = msg.getData();
            Log.d("Tag", "客户端收到的消息 bundle:" + data.getString("key"));
        }
    });

    //点击，通过Messenger发消息
    public void send(View view){

        //通过Messenger发消息
        Message msgToService = Message.obtain();
        msgToService.what=200;

        //带上参数
//        msgToService.obj = "自古真情留不住";         不行,要序列化
        Bundle bundle = new Bundle();
        bundle.putString("key","自古真情留不住");
        msgToService.setData(bundle);

        //Message有个变量，指定谁来接收返回的消息
        msgToService.replyTo = messengerClient;
        try {
            messengerService.send(msgToService);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }
}
