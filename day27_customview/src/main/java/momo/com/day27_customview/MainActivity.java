package momo.com.day27_customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import momo.com.day27_customview.widget.MyProgressBar;

public class MainActivity extends AppCompatActivity {


    MyProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zoom_imageview);
//        progressBar  = (MyProgressBar) findViewById(R.id.pb);
    }

//    int progress;
//    @Override
//    protected void onResume() {
//        super.onResume();
//        new Thread(){
//
//            @Override
//            public void run() {
//                super.run();
//                for(int i = 0;i<=100;i++){
//                    progress = i;
//                    SystemClock.sleep(500);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            progressBar.setProgress(progress);
//                        }
//                    });
//                }
//            }
//        }.start();
//    }
}
