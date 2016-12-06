package momo.com.day41_exer_material_design.demo;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import momo.com.day41_exer_material_design.R;

/**
 * NavigationView使用见布局文件
 *
 * NavigationView菜单宽度默认是屏幕宽度的80%
 */
public class NavigationViewActivity extends AppCompatActivity {

    NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_view);

        nv = (NavigationView) findViewById(R.id.nv);

        //设置icon图标 null显示实际图片
        nv.setItemIconTintList(null);

        //设置NavigationView菜单的点击事件(不包含头部)
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.menu1:{
                        Toast.makeText(NavigationViewActivity.this, "realmo test", Toast.LENGTH_SHORT).show();
                    }break;
                    case R.id.menu2:{}break;
                    case R.id.menu3:{}break;
                    case R.id.menu4:{}break;
                }
                return false;
            }
        });

        //头部点击事件
        //获取头布局文件
        View headerView = nv.getHeaderView(0);
        //headerView中的findViewById方法来查找到头部的控件，设置点击事件即可。

    }



}
