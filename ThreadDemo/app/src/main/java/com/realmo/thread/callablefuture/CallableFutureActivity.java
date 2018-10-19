package com.realmo.thread.callablefuture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.realmo.thread.R;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


/**
 * 使用Callable&Future创建线程
 * 1.创建Callable接口的实现类，并实现call()方法，该call()方法将作为线程执行体，且该call()方法有返回值 ｛@link MyCallable｝
 * 2.创建Callable实现类的实例，使用FutureTask类来包装Callable对象，该FutureTask对象封装了该Callable对象的call()方法的返回值
 * 3.使用FutureTask对象作为Thread对象的target创建并启动线程
 * 4.调用FutureTask对象的get()方法来获得子线程执行结束后的返回值
 */
public class CallableFutureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callable_future);

        testCallable();
    }

    private void testCallable() {
        MyCallable myCallable = new MyCallable();
        FutureTask<Integer> task = new FutureTask<Integer>(myCallable);
        for (int i = 0; i < 100; i++) {
            Log.d("realmo",Thread.currentThread().getName() + "== i:" + i);
            if(i == 20){
                new Thread(task).start();
            }
        }

        try {
            //get()方法 会返回Callable任务里call()方法的返回值。调用该方法将导致程序阻塞，必须等到子线程结束后才会得到返回值
            Log.d("realmo","子线程返回值:"+task.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
