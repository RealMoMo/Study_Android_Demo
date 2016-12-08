package com.example.day11_menu_demo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.TextView;

public class MainActivity3 extends Activity {

	TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tv = (TextView) findViewById(R.id.tv);

		// 注册上下文菜单
		registerForContextMenu(tv);
	}

	
	/*
	 * 使用上下文菜单：
	 * 
	 * 重写onCreateContextMenu方法
	 * 
	 * 注册到对应的控件上
	 * 
	 * 参数一：ContextMenu
	 * 
	 * 参数二：View 注册上下文菜单的View
	 * 
	 * 参数三：ContextMenuInfo 菜单的扩展信息
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		// 参数一：menu资源的id
		// 参数二：构造菜单
		getMenuInflater().inflate(R.menu.main, menu);
	}

	
	/*
	 * contextMenu的点击事件
	 * 
	 * 重写onContextItemSelected
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		

		switch (item.getItemId()) {

		case R.id.add: {
			tv.setText("hahhahahahahhahah");
		}
			break;
		case R.id.red: {
			tv.setTextColor(Color.RED);
		}
			break;

		case R.id.green: {
			tv.setTextColor(Color.GREEN);
		}
			break;

		case R.id.bule: {
			tv.setTextColor(Color.BLUE);
		}
			break;

		default:
			break;
		}

		return super.onContextItemSelected(item);
	}
}
