package momo.com.day24_aidl_client;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.qf.service.ISingInterface;

public class MainActivity extends AppCompatActivity {

    //声明代理窗口
    ISingInterface singBinder;
    //绑定服务对象
    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //初始化代理窗口
            singBinder= ISingInterface.Stub.asInterface(service);
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
        //跨进程，启动服务
        /**
         * 5.0之后跨进程服务必须显示意图
         *
         * 参数一： 包名（最好在该模块的build.gradle找）
         *
         * 参数二：包名+类名
         * */
        ComponentName name = new ComponentName("momo.com.day24_aidl_service",
                "momo.com.day24_aidl_service.SingService");
        //设置Intent的Component
        intent.setComponent(name);
        //绑定
        bindService(intent,conn,BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑
        unbindService(conn);
    }

    //点击，远程调用sing
    public void sing(View view){
        try {
            singBinder.sing("泡沫");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    //点击，远程调用add
    public void add(View view){

        try {
            int value = singBinder.add(50,50);
            Button btn = (Button) view;
            btn.setText(""+value);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
