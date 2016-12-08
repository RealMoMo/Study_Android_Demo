package com.example.day08_asynctask_login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class UserInfo extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		
		setContentView(tv);
		
		Intent intent =getIntent();
		String data = intent.getStringExtra("data");
		
		tv.setText("data:"+data);
	}

}
