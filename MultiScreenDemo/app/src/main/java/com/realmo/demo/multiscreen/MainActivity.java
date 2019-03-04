package com.realmo.demo.multiscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PictureInPictureParams;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.realmo.demo.multiscreen.utils.DefaultLogger;


/**
 *  分屏 + 画中画 功能Demo
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvPic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPic = findViewById(R.id.tv_picinpic);
        tvPic.setOnClickListener(this);


    }


    @Override
    protected void onStart() {
        super.onStart();
        DefaultLogger.debug("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        DefaultLogger.debug("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        DefaultLogger.debug("onPause");
    }


    @Override
    protected void onStop() {
        super.onStop();
        DefaultLogger.debug("onStop");
    }


    @Override
    public boolean isInMultiWindowMode() {
        return super.isInMultiWindowMode();
    }

    @Override
    public boolean isInPictureInPictureMode() {
        return super.isInPictureInPictureMode();
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode, Configuration newConfig) {
        super.onMultiWindowModeChanged(isInMultiWindowMode, newConfig);
        DefaultLogger.debug("onMultiWindowModeChanged:"+isInMultiWindowMode);
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
        DefaultLogger.debug("onPictureInPictureModeChanged:"+isInPictureInPictureMode);
    }

    @Override
    public boolean enterPictureInPictureMode(@NonNull PictureInPictureParams params) {
        return super.enterPictureInPictureMode(params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_picinpic:{
                openPicInPicMode();
            }break;
            default:{

            }break;
        }
    }

    private void openPicInPicMode() {
        this.enterPictureInPictureMode(new PictureInPictureParams.Builder().build());
    }
}
