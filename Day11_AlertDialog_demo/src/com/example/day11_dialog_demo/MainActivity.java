package com.example.day11_dialog_demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	// 点击自定义登录对话框
	public void dialog3(View view) {
		// 声明弹出的对话框
		final AlertDialog dialog;
		// 1.Builder
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// 2.LayoutInflater-->layout.xml-->view
		View loginView = LayoutInflater.from(this).inflate(
				R.layout.layout_dialog_login, null);
		// 3.设置标题及内容,
		builder.setTitle("登录");
		builder.setView(loginView);
		// 4.显示
		dialog = builder.show();
		// 5.找到对应的控件，设置交互事件
		final EditText name = (EditText) loginView.findViewById(R.id.name);
		final EditText password = (EditText) loginView
				.findViewById(R.id.password);

		Button sure = (Button) loginView.findViewById(R.id.sure);
		Button cancel = (Button) loginView.findViewById(R.id.cancel);

		// 点击登录按钮
		sure.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 把用户输入的信息提示出来
				String name_info = name.getText().toString();
				String password_info = password.getText().toString();
				Toast.makeText(MainActivity.this,
						name_info + ":" + password_info, Toast.LENGTH_SHORT)
						.show();
				// 消失对话框
				dialog.dismiss();
			}
		});
		// 点击取消登录按钮
		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 消失对话框
				dialog.dismiss();

			}
		});

	}

	String[] items = { "皇马", "巴塞", "拜仁", "阿森纳" };
	boolean[] isChecks = { true, false, false, false };

	// 点击单选对话框按钮
	public void dialog1(View view) {
		// 1.Builder
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// 2.设置标题、内容、监听
		builder.setTitle("你最喜欢那支球队");
		builder.setSingleChoiceItems(items, 0, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 弹出当前选中项的数据
				Toast.makeText(MainActivity.this, items[which],
						Toast.LENGTH_SHORT).show();

			}
		});
		// 3.显示
		builder.show();

	}

	// 点击多选对话框按钮
	public void dialog2(View view) {
		// 1.Builder
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// 2.设置标题、内容、监听
		builder.setTitle("你最喜欢那支球队");
		/*
		 * 参数1：数据源数组
		 * 
		 * 参数2:每个选项的默认初始的点击状态boolean数组
		 * 
		 */
		builder.setMultiChoiceItems(items, isChecks,
				new OnMultiChoiceClickListener() {

					// 参数三：当前项是被选中还是取消
					@Override
					public void onClick(DialogInterface dialog, int which,
							boolean isChecked) {
						// 声明一个结果字符串
						StringBuilder sb = new StringBuilder();
						// 循环遍历选中数据，获取选中项的内容，并拼接到结果中
						for (int i = 0; i < items.length; i++) {
							// 如果当前项是选中，拼接到结果中
							if (isChecks[i]) {
								sb.append(items[i]).append("\n");
							}
						}
						Toast.makeText(MainActivity.this, sb.toString(),
								Toast.LENGTH_SHORT).show();
					}
				});
		// 3.显示
		builder.show();
	}

	// 点击普通对话框按钮
	public void dialog(View view) {
		// 创建警告对话框
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// 2.设置Builder标题、图标
		// 3.设置文本内容,监听
		builder.setTitle("简单对话框");
		builder.setIcon(R.drawable.ic_launcher);
		builder.setMessage("哈哈").setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 参数一：Dialog,当前显示的对话框
				// 参数二：表示被点击的位置
				finish();
			}
		});
		// 4.创建对话框
		// 5.show
		builder.create().show();

	}

	
	// 按返回键时，弹出对话框
	/*
	 * onKeyDown用来处理按键处理
	 * 
	 * 参数一：当前按下键的键值keyCode
	 * 
	 * 参数二：KeyEvent按下键的事件封装成的对象
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 当按下返回键时，弹出提示,两个条件同时满足：事件是按下事件，键是返回键
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			// 写一个对话框
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("退出提示").setMessage("是否退出？");
			// 两个按钮
			builder.setPositiveButton("确定", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// 确定退出
					finish();
				}
			});

			builder.setNegativeButton("不退出", null);

			builder.show();
		}

		return super.onKeyDown(keyCode, event);

	}
}
