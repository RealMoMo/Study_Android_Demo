package com.example.day16_dialoganimation;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	public void doClick(View view) {
		//AlertDialog动画
		// 1.创建AlertDialog
		// AlertDialog dialog = new AlertDialog.Builder(this).setTitle("title")
		// .setMessage("message").create();
		// 2.通过Dialog获取Window
		// Window window = dialog.getWindow();
		// window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
		// 3.对Window对象设置动画风格
		// window.setWindowAnimations(R.style.dialogWindowAnim); // 添加动画
		// 显示对话框
		// dialog.show();
		
		//popupwindow的动画
		View popupwindow = getLayoutInflater().inflate(
				R.layout.popupwindow_layout, null);
		PopupWindow pop = new PopupWindow(popupwindow,
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		pop.setAnimationStyle(R.style.dialogWindowAnim);
		pop.showAsDropDown(view);
	}

}
