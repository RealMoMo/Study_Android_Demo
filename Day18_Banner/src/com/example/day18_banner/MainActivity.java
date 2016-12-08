package com.example.day18_banner;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

public class MainActivity extends Activity {

	//控件声明
	ViewPager vp;
	LinearLayout point_layout;
	ListView lv;
	
	
	List<View> views;
	//适配器
	ViewPagesAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//初始化控件
		setupView();
		//初始化数据
		initData();
		//初始化点
		initPoint();
		 //设置Adapter
		adapter = new ViewPagesAdapter(views);
		vp.setAdapter(adapter);
		
		//viewpager设置监听
		vp.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {

				//对点进行图片设置
                //先获取圆点的个数 point_layout.getChildCount()
				int pointCount = point_layout.getChildCount();

				for (int i = 0; i < pointCount; i++) {
					//循环获取每个子控件，并强转成ImageView
					ImageView iv = (ImageView) point_layout.getChildAt(i);
					//设置ImageView 的图片
					if (i == arg0) {
						// 选中的
						iv.setImageResource(R.drawable.pager_select);
					} else {
						iv.setImageResource(R.drawable.pager_normal);

					}

				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		//启动线程，让轮播图片动起来
		startTask();
		

	}

	private void startTask() {
		 //创建线程，并启动
		new Thread() {
			public void run() {
				//死循环
				while(true){
					//每隔2000ms切换一次图片
					SystemClock.sleep(2000);
					//切换图片的设置在UI线程中完成
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							//获取当前显示的下标
							int currentIndex = vp.getCurrentItem();
							//在当前基础上，再加1
							currentIndex++;
							vp.setCurrentItem(currentIndex%views.size());
							
						}
					});
					
				}
			};
		}.start();
		
	}

	private void initPoint() {
		//创建LayoutParams
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
		params.setMargins(5, 5, 5, 5);
		//获取数据源的大小
		int pointCount = views.size();
		//循环创建ImageView
		for (int i = 0; i < pointCount; i++) {
			ImageView iv = new ImageView(this);
			if (i == 0) {
				iv.setImageResource(R.drawable.pager_select);
			} else {

				iv.setImageResource(R.drawable.pager_normal);
			}
			//添加到point_layout
			point_layout.addView(iv, params);

		}

	}

	//声明图片资源的id数组
	int[] imgId = { R.drawable.banner1, R.drawable.banner2, R.drawable.banner3,
			R.drawable.banner4, R.drawable.banner5 };

	private void initData() {
		//初始化集合
		views = new ArrayList<View>();
		//循环添加控件
		for (int i = 0; i < 5; i++) {
			//创建ImageView
			ImageView iv = new ImageView(this);
			//设置ImageView的拉伸模式
			iv.setScaleType(ScaleType.FIT_XY);
			//设置ImageView的图片
			iv.setImageResource(imgId[i]);
			//添加到集合中
			views.add(iv);

		}

	}

	private void setupView() {
		vp = (ViewPager) findViewById(R.id.viewpager);
		point_layout = (LinearLayout) findViewById(R.id.point_layout);
		
		//===========================设置ListView
		
		lv =(ListView) findViewById(R.id.lv);
		List<String> data = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			data.add("测试"+i);
		}
		
		ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
		lv.setAdapter(listAdapter);
		
		//ListView有个方法：addHeaderView(View)
	
	}

}
