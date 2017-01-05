package com.example.day11_listview_checkbox;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity {

	
	ListView lv;
	List<Bean> data;
	MyAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		lv = (ListView) findViewById(R.id.lv);
		data = new ArrayList<Bean>();
		for (int i = 0; i < 100; i++) {
			Bean bean = new Bean();
			bean.name=""+i;
			data.add(bean);
		}
		
		adapter = new MyAdapter(data, this);
		lv.setAdapter(adapter);
		
		
		
		// ListView的Item点击事件
		//若有其他按钮在ListView里，ListView会失去焦点。首先，得在item的布局的根节点里先声明android:descendantFocusability="blocksDescendants"
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Toast.makeText(MainActivity.this, data.get(position).name, Toast.LENGTH_SHORT).show();
				
			}
		});
		
		// 给ListView注册上下文菜单
		registerForContextMenu(lv);
		
	}
	
	/*
	 * 
	 * 重写onCreateContextMenu
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		// 添加3个菜单选项
		menu.add(1, 1, 0, "添加");
		menu.add(1, 2, 0, "删除");
		menu.add(1, 3, 0, "插入");
		
	}
	
	
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch (item.getItemId()) {
		case 1:{
			// 在最前面添加一条数据
			Bean bean = new Bean();
			bean.name= "这是新增加的信息";
			// 向数据集合中添加数据
			data.add(0,bean);
			// 通知adapter数据发生改变，更新界面
			adapter.notifyDataSetChanged();
		}
			
			break;
		case 2:{
			//删除功能
			// 1.先获取当前长按项的position
			// 通过MenuItem获取ContextMenuInfo
			// 把ContextmenuInfo强转成AdapterContextMenuInfo
			AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
			int position = menuInfo.position;
			// 2.通过集合删除指定位置的数据
			data.remove(position);
			// 3.通知界面更新
			adapter.notifyDataSetChanged();
		}
		case 3:{
			//插入功能
			AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
			int position = menuInfo.position;
			Bean bean = new Bean();
			bean.name= "这是新插入的信息";
			data.add(position,bean);
			adapter.notifyDataSetChanged();
		}
		
		break;

		default:
			break;
		}
		
		return super.onContextItemSelected(item);
	}


}
