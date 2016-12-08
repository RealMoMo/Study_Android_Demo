package momo.com.day24_aidl_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.qf.service.ISingInterface;

/**
 * AIDL远程调用：
 *
 * 写一个class,继承自Service
 *
 * 定义内部类：继承自 ISingInterface.Stub 并实现接口的方法
 *
 * 重写onBind方法，返回内部类的实例
 */
public class SingService extends Service {

    class MyStub extends ISingInterface.Stub{

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void sing(String name) throws RemoteException {
            Log.d("Tag","Service sing:"+name);
        }

        @Override
        public int add(int a, int b) throws RemoteException {
            Log.d("Tag","Service add:"+a+b);
            return a+b;
        }
    }

    MyStub stub;

    @Override
    public void onCreate() {
        super.onCreate();
        stub = new MyStub();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }
}
