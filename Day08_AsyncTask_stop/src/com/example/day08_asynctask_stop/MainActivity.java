package com.example.day08_asynctask_stop;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	MyTask task = new MyTask();
	
	
	public void startAsync(View view){
		
		task.execute();
		// 防止用户多次点击
		view.setClickable(false);
		
		
	}
	
	// 方法1：声明一个线程结束的标识变量
	boolean isStop;
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//停止异步任务方法1：设置停止标识
//		isStop = true;
		//方法2:
		task.cancel(true);
	}
	
	class MyTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			int i =0;
			//方法1: while(!isStop)
			//方法2:
			while(!isCancelled()){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i++;
				Log.d("TAG", "异步任务："+i);
				
			}
			
			return null;
			
		}
		
	}
}
