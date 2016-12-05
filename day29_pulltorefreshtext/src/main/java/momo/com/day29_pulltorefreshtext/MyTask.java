package momo.com.day29_pulltorefreshtext;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/11/17 0017.
 */
public class MyTask extends AsyncTask<String,Void,String> {
    //通过handler来发数据
    Handler handler;

    public MyTask(Handler handler){
        this.handler =handler;
    }

    @Override
    protected String doInBackground(String... params) {
        //联网加载
        HttpURLConnection conn=null;
        try {
            URL url = new URL("http://www.ytmfdw.com/coupon/index.php?c=user&a=getonejoke");
            conn = (HttpURLConnection) url.openConnection();
            InputStream in = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in,"utf-8"));
            StringBuilder sb = new StringBuilder();
            String temp;

            while((temp =br.readLine())!=null){
                sb.append(temp);
            }

            br.close();

            //解析JSONObject
            JSONObject json = new JSONObject(sb.toString());
            JSONObject dataJson = json.optJSONObject("data");
            JokeBean bean = new JokeBean();
            bean.content = dataJson.optString("content");
            bean.img = dataJson.optString("img");

            //下载完成，handler发下载完成的消息
            Message msg = Message.obtain();
            msg.what=100;
            msg.obj=bean;
            handler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(conn!=null){
                conn.disconnect();
            }
        }


        return null;
    }



}
