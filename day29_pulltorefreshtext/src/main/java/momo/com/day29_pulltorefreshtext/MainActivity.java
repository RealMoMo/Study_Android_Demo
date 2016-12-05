package momo.com.day29_pulltorefreshtext;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class MainActivity extends AppCompatActivity {


    TextView tv;
    ImageView iv;

    JokeBean bean;

    PtrClassicFrameLayout ptrFrame;

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                //表示下载完成，更新TextView,ImageView
                case 100: {
                    //取出JokeBean
                    bean= (JokeBean) msg.obj;
                    //设置给文本框
                    tv.setText(bean.content);
                    //设置图片(用了Glide图片框架)
                    Glide.with(MainActivity.this).load(bean.img).into(iv);
                    //刷新结束
                    ptrFrame.refreshComplete();
                }
                break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv_content);
        iv = (ImageView) findViewById(R.id.iv);
        //初始化刷新控件
        setupRefreshView();
    }

    private void setupRefreshView() {
        //找到控件
        ptrFrame = (PtrClassicFrameLayout) findViewById(R.id.fragment_rotate_header_with_text_view_frame);
        //设置距离上次刷新时间
//        ptrFrame.setLastUpdateTimeRelateObject(this);
        //设置下拉监听
        ptrFrame.setPtrHandler(new PtrDefaultHandler() {
            /*下拉时，会触发该方法，
          *
          * 执行刷新或其他逻辑处理代码
          * */
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //模拟耗时操作
//                frame.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        //刷新结束
//                        ptrFrame.refreshComplete();
//                    }
//                }, 1500);

                //联网加载数据
                getData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return true;
            }
        });

    }

    //启动异步任务，下载数据
    private void getData() {

        MyTask task = new MyTask(handler);
        task.execute();
    }
}
