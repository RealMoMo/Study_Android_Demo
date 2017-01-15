package com.momo.day76_scrollview_with_listview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * 本Demo解决ScrollView嵌套ListView显示ListView item内容不全的问题。
 * 为什么只显示一行：ScrollView中嵌套ListView空间，无法正确的计算ListView的大小。
 * 故可以通过代码，根据当前的ListView的列表项计算列表的尺寸。
 */
public class MainActivity extends AppCompatActivity {


    private ScrollView scrollView;
    private ListView listview;
    private ArrayAdapter<String> adapter;

    private List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        listview = (ListView) findViewById(R.id.listview);

        for (int i = 0; i < 100; i++) {
            data.add(""+i);
        }

        adapter = new ArrayAdapter<String>(this,R.layout.listview_layout,R.id.listview_tv,data);
        listview.setAdapter(adapter);

        setListViewHeightBasedOnChildren(listview);


    }

    //代码重新计算ListView的高度
    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高   注意：LinearLayout才有measure方法 所以，此setListViewHeightBasedOnChildren方法只适用ListView的父布局为LinearLayout
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }
}
