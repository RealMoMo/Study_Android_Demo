package com.hht.autotrack.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;


/**
 * 参考：
 *
 * Android十八章：Java线程中的Runnable，Callable，Future，FutureTask - 云+社区 - 腾讯云
 * https://cloud.tencent.com/developer/article/1328956
 */
public class MainActivity extends AppCompatActivity {


    private ExecutorService executorService;
    private FutureTask futureCallable;
    private FutureTask futureRunnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        executorService = Executors.newSingleThreadExecutor();
        futureCallable = new FutureTask(new MyCallable(1,1000));
        executorService.execute(futureCallable);


        Future<String> future1 = executorService.submit(new Runnable() {
            @Override
            public void run() {

            }
        }, "realmo");


        futureRunnable = new FutureTask(new Runnable() {
            @Override
            public void run() {

            }
        },"futureRunnable");

        Future<?> future2 = executorService.submit(futureRunnable);

        long timeMillis = System.currentTimeMillis();
        Log.d("realmo","running futureCallable:"+timeMillis);
        try {
            Log.d("realmo","futureCallable:"+ this.futureCallable.get());//futureCallable:500500
            Log.d("realmo","future1:"+future1.get());//future1:realmo
            Log.d("realmo","future2:"+futureRunnable.get());//future2:futureRunnable
            Log.d("realmo","future2:"+future2.get());//future2:null
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("realmo","futureCallable time:"+(System.currentTimeMillis()-timeMillis));//futureCallable time:2008

    }
    

    class MyCallable implements Callable<Integer>{

        Integer mFirst;
        Integer mSecond;

        public MyCallable(int first ,int second){
            mFirst = first;
            mSecond = second;
        }


        @Override
        public Integer call() throws Exception {
            Log.d("realmo","callable do something");

            Thread.sleep(2000);
            int sum = 0;
            for(int i = mFirst ; i<= mSecond ;i++ ){
                sum += i;
            }

            return sum;
        }
    }
}
