package momo.com.day40_myjoke_fromhttp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 导包：compile 'org.jsoup:jsoup:1.9.2'
 * 只能解析http，不能解析https
 */
public class MainActivity extends AppCompatActivity  {

    //页数
    int page=1;
    //listview是否加载更多
    boolean isAddMore;
    //recyclerview是否滚动到底的标识
    boolean toBottom;
    //数据集合
    List<JokeBean> data = new ArrayList<>();

    RecyclerView rc;
//    ListView lv;

    ArrayAdapter<JokeBean> adapter;
    JokeAdapter rcAdapter;

    //处理recyclerview滚动到底的handler
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //RecyclerView.canScrollVertically(1)的值表示是否能向上滚动，false表示已经滚动到底部
            //RecyclerView.canScrollVertically(-1)的值表示是否能向下滚动，false表示已经滚动到顶部
            //滚动到底，加载更多数据
            if(!rc.canScrollVertically(1)){

                page++;
                getData();
            }
            //延迟发送消息
            handler.sendEmptyMessageDelayed(0,1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rc = (RecyclerView) findViewById(R.id.rv);
//        lv = (ListView) findViewById(R.id.lv);

//        adapter = new ArrayAdapter<JokeBean>(this,android.R.layout.simple_list_item_1,data);
//        lv.setAdapter(adapter);
            //listview点击item的监听
//        lv.setOnItemClickListener(this);
        //listview滚动监听
//        lv.setOnScrollListener(this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        rc.setLayoutManager(manager);

        rcAdapter = new JokeAdapter(this,data);
        rc.setAdapter(rcAdapter);


        //联网获取数据
        getData();

        handler.sendEmptyMessage(0);



    }


    private void getData(){
        //联网得到数据
        Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://www.qiushibaike.com/")
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .build();
        //得到接口实例
        IJokeInterface joke = retrofit.create(IJokeInterface.class);
        //发请求
        Call<String> call = joke.getData(page);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                String value = response.body();
                //通过JSoup解析成Document
                Document document = Jsoup.parseBodyFragment(value);
                //  从document拿到class="article block untagged mb15"
                //class中，如果有空格如上所示，说明class里面各内容是有继承关系，不能直接全部拿来解析。只需拿其中关键的一个解析即可
                Elements es = document.getElementsByClass("article");
                //取出每个元素，封装成一个实体Bean
                for (Element e: es){
                    JokeBean bean = new JokeBean(e);
                    data.add(bean);
                }

                rcAdapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Intent intent = new Intent(this,DetailActivity.class);
//        intent.putExtra("url",data.get(position).contentHerf);
//        startActivity(intent);
//    }
//
//    @Override
//    public void onScrollStateChanged(AbsListView view, int scrollState) {
//        //SCROLL_STATE_IDLE =0;
//        if(isAddMore&&scrollState==SCROLL_STATE_IDLE){
//            page++;
//            getData();
//
//        }
//    }
//
//    @Override
//    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//            if(firstVisibleItem+visibleItemCount==totalItemCount){
//                isAddMore =true;
//            }else{
//                isAddMore=false;
//            }
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(handler!=null) {
            //移除消息
            handler.removeMessages(0);
            handler = null;
        }

    }
}
