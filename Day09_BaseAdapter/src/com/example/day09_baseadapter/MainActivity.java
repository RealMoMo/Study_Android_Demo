package com.example.day09_baseadapter;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	// 声明控件，数据，adapter
	ListView lv;
	ArrayList<String> data;
	MyAdapter adapter;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//初始化控件
		lv =(ListView) findViewById(R.id.lv);
		//初始化数据
		data = new ArrayList<>();	
		for (int i = 0; i < 200; i++) {
			data.add("momo"+i);
		}
		//初始化adpater
		adapter = new MyAdapter();
		//给ListView设置Adapter
		lv.setAdapter(adapter);
	}
	
	/**
	 * 自定义Adapter
	 * 
	 * 1.写一个class,继承自BaseAdapters(抽象类)
	 * 
	 * 2.实现BaseAdapter中的抽象方法
	 * */
	class MyAdapter extends BaseAdapter{
		
		//初始化LayoutInflater(大致理解：布局打气筒)，作用把布局转换为View
		LayoutInflater flater ;
		
		public MyAdapter(){
			flater = LayoutInflater.from(MainActivity.this);
		}
		
		/* 告诉ListView要加载多少条数据
		 * 
		 * 一般是写成数据源的大小
		 */
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}

	
		/* 返回每个Item所需要的视图
		 * 
		 * 完成两件事：一，创建视图；二，把数据跟视图绑定
		 * 
		 * 参数一：int 代表是当前创建视图在列表中的位置
		 * 
		 * 参数二：已经创建过的视图，即可复用的视图
		 * 
		 * 参数三：布局控件的父布局
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			Log.d("TAG", "position"+position);
			//根据convertView判断是否有可用视图，如果convertView为null，就创建一个新的视图，如果不为null，说明已经创建过了，就可以直接利用
			//先声明ViewHolder
			//如果convertView为null，就创建一个新的ViewHolder,并对其中的控件进行初始化
			//把ViewHoder与convertView进行关联
			ViewHolder holder;

//			View view = flater.inflate(R.layout.item_list, null);
			if(convertView == null){
				Log.d("TAG","convertView");
				//通过LayoutInflater把xml布局文件转换成View
				//参数一：需要转换的layout的id
				//参数二：加载该View的父控件，简单可写成null
//				convertView = flater.inflate(R.layout.item_list, null);
				//标准写法
				convertView = flater.inflate(R.layout.item_list, parent,false);
				holder = new ViewHolder();
				holder.tv =(TextView) convertView.findViewById(R.id.tv);
				
				//给convertView设置标记,即ViewHolder与convertView进行关联
				convertView.setTag(holder);
			}else{
				//有可用视图,取出Tag,强转成ViewHolder
				holder = (ViewHolder) convertView.getTag();
			}
			//先找到存放数据的控件：TextView
			
			//从数据源中取出数据
			String value = data.get(position);
			//给TextView设置数据
			holder.tv.setText(value);
			
			return convertView;
		}
		
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		class ViewHolder{
			TextView tv;
			
		}
		
	}


}
