package com.example.day08_asynctask_downimage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {
	
	ImageView iv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		iv =(ImageView) findViewById(R.id.iv);
	}
	
	// 点击，下载图片，并展示出来
	public void download(View view ){
		
		MyTask task = new MyTask();
		task.execute();
	}

	
	// 使用异步任务
		/*
		 * 1.写一个class,继承自AsyncTask
		 * 
		 * 2.确定三个泛型
		 * 
		 * 3.重写doInBackground
		 * 
		 * 4.重写onPostExecute
		 */
	
	class MyTask extends AsyncTask<Void, Void, Bitmap>{

		@Override
		protected Bitmap doInBackground(Void... params) {
			
			HttpURLConnection conn =null;
			Bitmap bitmap= null;
			try {
				URL url = new URL("http://www.ytmfdw.com/image/img2.jpg");
				conn = (HttpURLConnection) url.openConnection();
				// 把InputStream转换成Bitmap
				InputStream in = conn.getInputStream();
				bitmap = BitmapFactory.decodeStream(in);
				
				in.close();
				// 把bitmap返回
				return bitmap;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(conn != null){
					conn.disconnect();
				}
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// 判断结果是否为null,如果不是null，设置给ImageView
			if(result != null){
			iv.setImageBitmap(result);
			}
		}
		
		
	}

}
