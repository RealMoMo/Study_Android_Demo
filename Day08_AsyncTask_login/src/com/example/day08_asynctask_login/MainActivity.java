package com.example.day08_asynctask_login;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.day08_asynctask_login.LoginTask.CallBack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements CallBack  {

	
	EditText et_name,et_password;
	Button btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		setupView();
	}
	private void setupView() {
		// TODO Auto-generated method stub
		et_name = (EditText) findViewById(R.id.name);
		et_password=(EditText) findViewById(R.id.password);
		btn =(Button) findViewById(R.id.login);
	}
	
	// 点击，登录
	public void login(View view){
		LoginTask task = new LoginTask(this);
		task.execute(et_name.getText().toString(),et_password.getText().toString());
	}
	@Override
	public void startButton() {
		// TODO Auto-generated method stub
		// 登录开始，禁用按钮
		btn.setEnabled(false);
		
		
	}
	
	// {
	// "status": 1,
	// "msg": "ok",
	// "data": [
	// {
	// "id": "1",
	// "name": "10",
	// "phone": "101",
	// "sex": "男",
	// "qq": "1010",
	// "email": "010"
	// }
	
	@Override
	public void endButton(String result) {
		// TODO Auto-generated method stub
		// 联网完成后，放开按钮,把结果传入到另一个界面
		btn.setEnabled(true);
		// 如果登录成功，跳转到主界面
		// 如果登录失败，弹出提示
		try {
			JSONObject json = new JSONObject(result);
			if(json.getInt("status") ==0){
				Toast.makeText(this, json.optString("msg"), Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(this, json.optString("msg"), Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(this,UserInfo.class);
				String data = json.optString("data");
				
				intent.putExtra("data", data);
				
				startActivity(intent);
			}
		} catch (JSONException e) {
			Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT).show();

		}
		
		
		
	}


}
