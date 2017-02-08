package com.momo.reviewandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * 实现ListView样式的RecyclerView
 */
public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private List<String> mData;
    private com.momo.reviewandroid.SimpleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recylerView);

        initDatas();

        initViews();

        mAdapter = new SimpleAdapter(this, mData);
        recyclerView.setAdapter(mAdapter);
        //设置RecylcerView的布局管理
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        //设置Item动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        //设置RectclerView的Item间分割线
//        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));

        //
        mAdapter.setmOnItemClickListener(new SimpleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Toast.makeText(MainActivity.this,mData.get(postion)+":click,postion:"+postion,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int postion) {
                Toast.makeText(MainActivity.this,mData.get(postion)+":longclick,postion:"+postion,Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initViews() {

    }

    private void initDatas() {
        mData = new ArrayList<>();
        for (int i = 'A'; i <= 'z'; i++) {
            mData.add("" + (char) i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_add: {
                mAdapter.addData(1);
            }
            break;
            case R.id.action_del: {
                mAdapter.delData(1);
            }
            break;

            case R.id.action_listview: {
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            }
            break;
            case R.id.action_gridview: {
                recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
            }
            break;
            case R.id.action_hor_gridview: {
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.HORIZONTAL));
            }
            break;
            case R.id.action_staggered: {
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
            }
            break;
        }

        return super.onOptionsItemSelected(item);
    }
}
