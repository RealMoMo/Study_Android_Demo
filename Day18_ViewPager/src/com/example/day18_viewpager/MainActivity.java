package com.example.day18_viewpager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	ViewPager vp;
	// 数据源
	List<View> views;
	// 声明子控件的父布局
	LinearLayout point_layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// eclipse去掉标题栏
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// android studio隐藏ActionBar
		// getSupportActionBar().hide();

		// 找到控件和初始化数据源
		vp = (ViewPager) findViewById(R.id.viewpager);
		// 初始化LinearLayout
		point_layout = (LinearLayout) findViewById(R.id.point_layout);

		// 初始化数据
		initData();

		// 初始化圆点
		initPoint();

		// 使用Adapter
		MyAdapter adapter = new MyAdapter();
		vp.setAdapter(adapter);

		// 设置ViewPager的页面切换监听
		vp.setOnPageChangeListener(new OnPageChangeListener() {

			/*
			 * 当页面进行切换时，回调
			 * 
			 * 参数：当前切换的页面的下标
			 */
			@Override
			public void onPageSelected(int position) {

				// 获取dot_layout子控件的个数
				int viewCount = point_layout.getChildCount();
				// 循环遍历每个子控件，并强转成ImageView
				for (int i = 0; i < viewCount; i++) {
					// 获取每个子控件
					ImageView iv = (ImageView) point_layout.getChildAt(i);
					// 设置ImageView的图片
					if (position == i) {
						// 当前选中的页面
						iv.setImageResource(R.drawable.page_now);
					} else {
						iv.setImageResource(R.drawable.page);
					}
				}
				
				Log.d("Tag", "getChildCount:"+vp.getChildCount());

			}

			// 页面滑动时回调
			/*
			 * 参数一：当前被拖动的页面的下标
			 * 
			 * 参数二：移动的百分比
			 * 
			 * 参数三：移动的像素点
			 */
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub

			}

			// 滑动状态发生改变时回调
			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub

			}
		});

	}
	

	/*
	 * 动态加载点
	 */
	private void initPoint() {
		// 知道views的大小,来确定圆点的个数
		// 循环创建ImageView,并把创建出来的ImageView添加到point_layout
		int size = views.size();
		// 在线性布局中的属性
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
		// 设置外边距
		params.setMargins(5, 5, 5, 5);

		for (int i = 0; i < size; i++) {
			// 创建一个ImageView
			ImageView iv = new ImageView(this);

			// 设置iv的大小
			// 方法1
			iv.setLayoutParams(params);

			if (i == 0) {
				// 默认第一张图片为亮点
				iv.setImageResource(R.drawable.page_now);
			} else {
				iv.setImageResource(R.drawable.page);
			}
			// 加载到dot_layout
			point_layout.addView(iv);
			// 方法2
			// point_layout.addView(iv, params);
		}

	}

	private void initData() {
		// TODO Auto-generated method stub
		views = new ArrayList<>();
		LayoutInflater inflater = LayoutInflater.from(this);
		// ----------第1个界面
		View view1 = inflater.inflate(R.layout.page1, null);
		views.add(view1);
		// -----------第2个界面
		View view2 = inflater.inflate(R.layout.page2, null);
		views.add(view2);
		// -----------第3个界面
		View view3 = inflater.inflate(R.layout.page3, null);
		views.add(view3);
		// -----------第4个界面
		View view4 = inflater.inflate(R.layout.page4, null);
		views.add(view4);

	}

	/*
	 * ViewPager的适合器：
	 * 
	 * 1.写一个class,继承自PagerAdatepr
	 * 
	 * 2.重写相应方法
	 */

	class MyAdapter extends PagerAdapter {

		// 返回View的个数
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return views.size();
		}

		// 完成两件事：
		/*
		 * 一.加载View
		 * 
		 * 二。返回当前被加载的View
		 * 
		 * 参数一：ViewGroup:加载View的父控件
		 * 
		 * 参数二：int :当前被加载的View在数据源中的位置
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// 加载position位置的View
			container.addView(views.get(position));
			// 返回刚被加载的View
			return views.get(position);
		}

		/*
		 * 移除不使用的View
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// 下面的super代码必须去掉
			// super.destroyItem(container, position, object);
			// 写法一：
			container.removeView(views.get(position));
			// 写法二：
			// container.removeView(((View)object));
		}

		// 判断instantiateItem返回的object是不是view
		@Override
		public boolean isViewFromObject(View view, Object object) {
			// 固定写法view == object;
			return view == object;
		}

	}

}
