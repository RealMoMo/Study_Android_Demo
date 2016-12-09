package momo.com.day45_listview_vedio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ListView lv;
    private List<VideoBean> list;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.lv);
        initData();
        adapter = new MyAdapter(list,this);
        lv.setAdapter(adapter);
    }

    private void initData() {
        try {
            StringBuffer result = new StringBuffer();
            BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("video1.json")));
            String str;
            //读取文件数据
            while((str = br.readLine())!=null){
                result.append(str);
            }
            br.close();
            //解析json数据
            parseJson(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseJson(String s) {
        list = new ArrayList<>();
        try {
            JSONArray dataArr = new JSONObject(s).getJSONObject("data").getJSONArray("data");
            for (int i = 0; i < dataArr.length(); i++) {
                try {
                    JSONObject group = dataArr.getJSONObject(i).getJSONObject("group");
                    String p360Video = group.getJSONObject("360p_video").getJSONArray("url_list").getJSONObject(0).getString("url");
                    String title = group.getString("title");
                    int video_height = group.getInt("video_height");
                    String coverUrl = group.getJSONObject("medium_cover").getJSONArray("url_list").getJSONObject(0).getString("url");
                    int video_width = group.getInt("video_width");
                    list.add(new VideoBean(p360Video, coverUrl, video_width, video_height, title));
                } catch (JSONException e) {
//                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
