package com.realmo.thread;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.realmo.thread.callablefuture.CallableFutureActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnCallable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        btnCallable = findViewById(R.id.btn_callable);
        btnCallable.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_callable:{
                startActivity(CallableFutureActivity.class);
            }break;
            default:{

            }break;
        }
    }


    private void startActivity(Class clz){
        Intent intent = new Intent(this,clz);
        startActivity(intent);
    }
}
