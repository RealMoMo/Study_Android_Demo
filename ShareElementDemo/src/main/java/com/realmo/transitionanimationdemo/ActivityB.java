package com.realmo.transitionanimationdemo;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityB extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

        imageView = findViewById(R.id.imageview_b);
        textView = findViewById(R.id.tv_b);

        //initAnimation();
    }

//    private void initAnimation() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//        getWindow().setEnterTransition(new Slide());//从场景的边缘移入或移出
////            getWindow().setEnterTransition(new Fade());//调整透明度产生渐变效果
////        getWindow().setEnterTransition(new Explode());//从场景的中心移入或移出
//
//            ChangeBounds changeBounds = new ChangeBounds();//改变目标视图的布局边界
//        ChangeTransform changeTransform = new ChangeTransform();//改变目标视图的缩放比例和旋转角度
//        ChangeImageTransform changeImageTransform = new ChangeImageTransform();//改变目标图片的大小和缩放比例
//        ChangeClipBounds changeClipBounds = new ChangeClipBounds();//裁剪目标视图边界
//        changeBounds.setDuration(1000);
//            getWindow().setSharedElementEnterTransition(changeBounds);
//        }else{
//
//        }
//
//    }
}
