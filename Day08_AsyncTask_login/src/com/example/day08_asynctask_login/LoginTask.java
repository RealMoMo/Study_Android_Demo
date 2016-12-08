package com.example.day08_asynctask_login;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;

public class LoginTask extends AsyncTask<String, Void, String> {

	String http = "http://www.ytmfdw.com/coupon/admin.php?a=login";
	// 采用接口回调方式　
	public interface CallBack {
		/**
		 * 开始登录时调用　
		 */
		public void startButton();
		/**
		 * 登录结束时调用　
		 */
		public void endButton(String result);
	}

	CallBack cb;
	
	
	

	public LoginTask(CallBack cb) {
		super();
		this.cb = cb;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		// 开始登录，登录按钮不可点击
		if(cb!=null){
		cb.startButton();
		}
	}

	/*
	 * 点击登录时，传入账号和密码
	 */
	@Override
	protected String doInBackground(String... params) {
		// 1.HttpUrlConnection
		HttpURLConnection conn = null;
		// 联网登录,登录时，传入两个参数：name,password
		String name =params[0];
		String password =params[1];
		// 转换结果
		StringBuilder sb = new StringBuilder();

		try {
			// 2.URL
			URL url = new URL(http+"&name="+name+"&password="+password);
			// 3.url.openConnection
			conn = (HttpURLConnection) url.openConnection();
//			//POST请求设置=============================================
//			conn.setRequestMethod("POST");// 提交模式
//			conn.setDoOutput(true);// 是否输入参数
//			String param = "name=" + name + "&password=" + password;// post提交参数
//			byte[] bytes = param.getBytes();
//			conn.getOutputStream().write(bytes);// 输入参数
//			//================POST设置完成==========================
			// 4.InputStream
			InputStream in = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String temp = null;
			// 5.InputStream-->String
			while ((temp = br.readLine()) != null) {
				sb.append(temp).append("\n");
			}
			// 6.关闭流
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 7.断开连接
			if (conn != null) {
				conn.disconnect();
			}
		}
		// 8.把转换结果返回到onPostExecute
		return sb.toString();
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		// 恢复按钮可点
		if(cb!=null){
		cb.endButton(result);
		}
	}

}
