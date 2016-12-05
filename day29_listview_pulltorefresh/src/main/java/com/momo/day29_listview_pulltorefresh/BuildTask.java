package com.momo.day29_listview_pulltorefresh;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-11-17.
 */
public class BuildTask extends AsyncTask<Integer,Void,List<BuildBean>> {

    public List<BuildBean> data = new ArrayList<>();


    public interface CallBack{

        public void getData(List<BuildBean> list);
    }


    CallBack cb;



    public BuildTask(CallBack cb) {
        super();
        this.cb = cb;
    }



    @Override
    protected List<BuildBean> doInBackground(Integer... params) {
        Log.d("Tag","page:"+params[0]);
        HttpURLConnection conn = null;
        StringBuilder sb = new StringBuilder();

        try {
            URL url = new URL(
                    "http://ikft.house.qq.com/index.php?guid=866500021200250&devua=appkft_1080_1920_XiaomiMI4LTE_1.8.3_Android19&order=0&searchtype=normal&devid=866500021200250&appname=QQHouse&mod=appkft&act=searchhouse&channel=71&page="+params[0]+"&rn=20&cityid=1");
            conn = (HttpURLConnection) url.openConnection();
            InputStream in = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in,"utf-8"));

            String temp =null;
            while((temp=br.readLine())!=null){

                sb.append(temp);
            }
            //log打印解析出来的Json数据
            Log.d("Tag", sb.toString());

            //json解析
            JSONObject obj = new JSONObject(sb.toString());
            JSONArray arr = obj.getJSONArray("data");
            int len = arr.length();
            Log.d("Tag",len+"");
            for (int i = 0; i < len; i++) {
                JSONObject buildObj =  arr.getJSONObject(i);
                BuildBean bean = new BuildBean(buildObj);
                data.add(bean);
                Log.d("Tag",bean.faddress);
            }
            br.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if(conn!=null){
                conn.disconnect();
            }
        }
        //返回数据
        return data;
    }

    @Override
    protected void onPostExecute(List<BuildBean> result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        if(result != null){
            cb.getData(result);
        }
    }

}
