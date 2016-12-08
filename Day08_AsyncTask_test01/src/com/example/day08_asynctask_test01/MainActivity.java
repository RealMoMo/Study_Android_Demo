package com.example.day08_asynctask_test01;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends Activity {
	Button btn;
	ProgressBar pb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn = (Button) findViewById(R.id.btn);
		pb = (ProgressBar) findViewById(R.id.pb);
		pb.setMax(10000);
	}

	// 按钮点击事件，启动异步任务执行耗时，并在相应View中设置值
	public void startAsynTask(View view) {
		// 1.创建AsyncTask对象
		MyTask task = new MyTask();
		// 2.启动
		task.execute(1L, 10000L, 100L);
	}

	/*
	 * 
	 * 使用AsyncTask (抽象类)
	 * 1.写一个class ,继承自AsyncTask<> 
	 * 2.重写doInBackground方法 
	 * 三个泛型:
	 * 第一个：表示execute方法传入参数类型 
	 * 第二个：发布进度值的类型 
	 * 第三个：子线程(doInBackground)返回值类型
	 */

	class MyTask extends AsyncTask<Long, Integer, Integer> {

		
		/*
		 * 在子线程运行之前调用　
		 * 
		 * 运行在主线程中
		 */
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			btn.setText("开始任务！！");
		}

		
		/*
		 * 执行在子线程中
		 * 
		 * 执行耗时操作或联网操作
		 * 
		 * 参数(可变参数... params:取的时候当成数组元素取就是)：
		 * 是由execute传入的
		 */
		@Override
		protected Integer doInBackground(Long... params) {
			// TODO Auto-generated method stub
			int sum = 0;
			for (long i = params[0]; i < params[1]; i += params[2]) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.d("TAG", "当前执行了" + i);
				// 给btn设置文本,在子线程中运行，不能更新UI
				// btn.setText("当前i=" + i);
				// 发布进行更新
				publishProgress((int) i);
				//累加
				sum += i;
			}
			return sum;
		}

		/*
		 * 执行在UI线程
		 * doInBackground执行完成后，会把执行结果带到该方法中
		 */
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// 把子线程执行结果设置给btn
			btn.setText("累加结果" + result);

		}

		@Override
		/*
		 * UI线程执行
		 */
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			// 取出发布进度的值
			int value = values[0];
			// 设置给Button
			btn.setText("" + value);
			pb.setProgress(value);
		}

	}

}
