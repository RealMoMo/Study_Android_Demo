package momo.com.day41_exer_material_design.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import momo.com.day41_exer_material_design.R;

/**
 * Created by Administrator on 2016/12/3 0003.
 */
public class SnackBar_Activity extends AppCompatActivity {


    CoordinatorLayout contain;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snackbar);

        contain = (CoordinatorLayout) findViewById(R.id.contain);
    }



    public void showSnackBar(View view){
        //snackbar最好配合CoordinatorLayout使用 （SnackBar可以直接右滑出去）
        //也可以避免控件的遮挡
        Snackbar snackbar = Snackbar.make(contain, "Hi! I'am SnackBar", Snackbar.LENGTH_SHORT)
                .setAction("Click me", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(SnackBar_Activity.this, "ByeBye", Toast.LENGTH_SHORT).show();
                    }
                })
                // 设定提示action的内容(上面Hi! I'am SnackBar不会显示)
//                .setText("hi realmo")
                //设置Action字体颜色
                .setActionTextColor(Color.parseColor("#8800ff00"));
        //snackbar文字的颜色
        View snackView = snackbar.getView();
        TextView tvSnackView  = (TextView) snackView.findViewById(android.support.design.R.id.snackbar_text);
        tvSnackView.setTextColor(Color.BLUE);
        //设置背景颜色
        snackView.setBackgroundColor(Color.parseColor("#66554433"));
        //显示snackbar
        snackbar.show();


    }



}
