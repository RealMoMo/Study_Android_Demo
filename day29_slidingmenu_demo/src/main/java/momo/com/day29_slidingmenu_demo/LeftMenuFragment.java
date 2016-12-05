package momo.com.day29_slidingmenu_demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/11/17 0017.
 */
public class LeftMenuFragment extends ListFragment{

    String[] title = {
            "百度","网易","新浪"
    };

    String[] urls={
            "http://www.baidu.com",
            "http://www.163.com",
            "http://www.sina.com"
    };

    //定义一个接口
    public interface ItemClick{
        public void itemClick(String url);
    }

    //接口对象
    ItemClick listener;

    //设置点击监听
    public void setItemClick(ItemClick itemClick){
        this.listener = itemClick;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,title);
        setListAdapter(adapter);


    }


    //Item点击事件
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Log.d("Tag","position:"+position);
        //ListView中的Item点击事件
        if(listener!=null){
            listener.itemClick(urls[position]);
        }

    }
}
