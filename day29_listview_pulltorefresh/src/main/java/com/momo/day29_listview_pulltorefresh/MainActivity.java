package com.momo.day29_listview_pulltorefresh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class MainActivity extends AppCompatActivity implements BuildTask.CallBack {

    ListView lv;
    PtrClassicFrameLayout refresh;

    BuildTask task;

    List<String> data;
    ArrayAdapter<String> adapter ;
    //页数
    int page = 1;
    //city
    int cityId=1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
        data = new ArrayList<>();
        adapter =new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,data);
        lv.setAdapter(adapter);
        task = new BuildTask(this);
        task.execute(page);

        setRefresh();
    }

    private void setRefresh() {
        refresh.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                task = new BuildTask(MainActivity.this);
                task.execute(++page);
            }


            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return super.checkCanDoRefresh(frame, lv, header);
            }
        });


    }

    private void setupView() {
        lv = (ListView) findViewById(R.id.lv);
        refresh = (PtrClassicFrameLayout) findViewById(R.id.refresh);
    }

    @Override
    public void getData(List<BuildBean> list) {
        data.clear();
        for(BuildBean bean:list){
           data.add(bean.fname);
        }
        adapter.notifyDataSetChanged();
        refresh.refreshComplete();

    }
}
