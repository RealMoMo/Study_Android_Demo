package momo.com.day43_databinding_listview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    ListView lv;
    List<Food> list;
    MyAdapter<Food> adapter;

    String url ="http://www.tngou.net/api/food/list?id=1";

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            adapter = new MyAdapter<>(list,R.layout.listview_item,MainActivity.this, momo.com.day43_databinding_listview.BR.food);
            lv.setAdapter(adapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.lv);
        initData();
        

    }

    private void initData() {
        list = new ArrayList<>();

        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONArray tngou = new JSONObject(response.body().string()).getJSONArray("tngou");
                        for (int i = 0; i < tngou.length(); i++) {
                            JSONObject data = tngou.getJSONObject(i);
                            list.add(new Food(data.getString("description"), "http://tnfs.tngou.net/image"+data.getString("img")));
                        }
                        handler.sendEmptyMessage(0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
