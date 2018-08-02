package com.realmo.demo_imageswitcher;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

/**
 * 通过ImageSwitcher简单实现图片切换以及相应动画
 */
public class MainActivity extends AppCompatActivity {
    /**
     * ImageSwitcher extends ViewSwitcher
     * ViewSwitcher extends ViewAnimator
     *  ViewAnimator extends FrameLayout
     */

    /**
     * ImageSwitcher
     * {@link ViewSwitcher} that switches between two ImageViews when a new
     * image is set on it. The views added to an ImageSwitcher must all be
     * {@link ImageView ImageViews}.
     */

    /**
     * ViewSwitcher : only two child views
     * {@link ViewAnimator} that switches between two views, and has a factory
     * from which these views are created.  You can either use the factory to
     * create the views, or add them yourself.  A ViewSwitcher can only have two
     * child views, of which only one is shown at a time.
     */

    /**
     * 主要简易实现切换视图+动画的View（为了简单方便）
     * 同级有TextSwitcher，同理使用和ImageSwitcher差不多
     * 想实现稍复杂的视图切换可用ViewSwitcher。当然，目前更多是ViewPager等其他
     */
    private ImageSwitcher imageSwitcher;

    private int[] resIds = new int[]{R.drawable.a, R.drawable.b,R.drawable.c,
                                    R.drawable.d,R.drawable.e};

    private int index;

    private static final int MSG_WHAT_CHANGE_IMG = 0x100;
    private static final int DELAY_TIME = 2000;
    private Handler handler  = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            imageSwitcher.setImageResource(resIds[(++index)%resIds.length]);
            sendEmptyMessageDelayed(MSG_WHAT_CHANGE_IMG,DELAY_TIME);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageSwitcher = findViewById(R.id.is);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView iv = new ImageView(MainActivity.this);
                iv.setScaleType(ImageView.ScaleType.CENTER);//居中显示
                //ViewSwitcher 默认给子控件设置match parent,所以此处不设置LayoutParams
                Log.d("realmo","make View"); //从源码得知，会触发2次（ViewSwitcher : only two child views）
                return iv;
            }
        });

        imageSwitcher.setImageResource(resIds[index]);//初始化时显示，必须放在工厂后面，否则会报NullPointerException
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));//设置动画
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));//设置动画

    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.sendEmptyMessageDelayed(MSG_WHAT_CHANGE_IMG,DELAY_TIME);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler = null;

    }
}
