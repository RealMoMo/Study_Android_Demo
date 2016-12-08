package com.example.day11_popupwindow_demo;

import java.text.DecimalFormat;
import java.util.Random;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	
	PopupWindow popWindow;
	
	// 点击，弹出一个PopupWindow
	public void doClick(View view){
		if(popWindow==null){
		// 如果popWindow为null，说明是第一次点击，就创建一个新的popupwindow实例
		// 1.获取LayoutInflater	
		LayoutInflater inflater = LayoutInflater.from(this);
		// 2.layout.xml-->view
		View popView = inflater.inflate(R.layout.popupwindow_layout, null);
		// 3.new PopupWindow
		popWindow = new PopupWindow(popView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		// 4.设置点击空白处让popuoWindow消失:设置一个透明的背景，设置四周可触摸
		popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//可以不用设置
		popWindow.setOutsideTouchable(true);
		
		Log.d("Tag", "执行了popwindow==null");
		
		//		popWindow.showAsDropDown(view);// 在控件的下方弹出
		//5.popupwindow.show...	
		popWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
		// 6.监听事件,先找到popwindow布局中的控件，再设置监听
		popView.findViewById(R.id.send_money).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Random random = new Random();
				float num = random.nextFloat()*100;
				DecimalFormat df = new DecimalFormat("#.00");
				String money = df.format(num);
				Toast.makeText(MainActivity.this, "恭喜你抢到"+money+"钱", Toast.LENGTH_SHORT).show();
				
				Log.d("Tag", "执行了onClick");
				
				// popwindow消失
				popWindow.dismiss();
			}
		});	
		
		Log.d("Tag", "执行了popwindow==null,last");
		
		}else{
			// 其他情况：没显示出来，但已经创建了；正在显示
			if(popWindow.isShowing()){
				// 如果正在显示，就消失
				popWindow.dismiss();
			}else{
				// 显示出来
				popWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
			}
		}
	}
}
