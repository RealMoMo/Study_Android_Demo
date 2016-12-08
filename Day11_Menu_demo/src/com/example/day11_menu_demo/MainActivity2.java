package com.example.day11_menu_demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.TextView;

public class MainActivity2 extends Activity {

	TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		tv = (TextView) findViewById(R.id.tv);
		
		
	}
	
	/**
	 * 创建菜单
	 * 
	 * 重写onCreateOptionsMenu
	 * 
	 * 通过代码形式添加菜单：
	 * */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//重写 Activity 的 onCreateOptionsMenu(Menu menu) 方法，在方法内调用 Menu 对象的方法来添加菜单项或子菜单。
		// 添加一个普通菜单(一级菜单)
		// groupId,itemId,orderId,title
		MenuItem add_item = menu.add(0, 0x666, 0, "添加");
		// 设置图片
		add_item.setIcon(R.drawable.d03);
		// 显示在菜单上
		add_item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		
		//创建二级菜单
		SubMenu subMenu = menu.addSubMenu("文本大小");
		//代码中创建的菜单，设置二级菜单的图片没什么卵用,没反应。(xml可以)
//		subMenu.setHeaderIcon(R.drawable.ic_launcher);
//		subMenu.setIcon(R.drawable.ic_launcher);
		subMenu.add(1, 0x101, 0, "大");
		subMenu.add(1, 0x102, 0, "中");
		subMenu.add(1, 0x103, 0, "小");
		
		return super.onCreateOptionsMenu(menu);
	}
	
	/**
	 * 选菜单的点击事件
	 * 
	 * 重写onOptionsItemSelected
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		// 根据id判断，做相应处理
		case 0x666:
			tv.setText("点击了"+item.getTitle());
			break;
		case 0x101:
			tv.setTextSize(50);
			break;
		case 0x102:
			tv.setTextSize(30);
			break;
		case 0x103:
			tv.setTextSize(20);
			break;

		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
