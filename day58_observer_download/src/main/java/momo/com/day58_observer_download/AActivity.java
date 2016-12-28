package momo.com.day58_observer_download;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 观察者模式
 *
 * 本Demo：通过观察者模式更新各界面的A/B/C Activity的下载进度
 */
public class AActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btn,btn_start,btn_pause,btn_resume;
    private ProgressBar progressBar;
    private MyObserver observer;

    private boolean isPause;
    private int progress;
    private FileInfo fileInfo;

    private Timer timer;
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            //若下载停止，不更新进度
            if(isPause){
                return;
            }

            progress++;
            if(fileInfo!=null) {
                fileInfo.setProgress(progress);
                //在Ui线程更新progressbar
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (progressBar != null) {
                            progressBar.setProgress(progress);
                        }
                        MyObserver.getInstance().notifyUpdate(fileInfo);
                    }
                });

            }


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();

    }


    private void setupViews() {

        progressBar = (ProgressBar) findViewById(R.id.pb);
        btn = (Button) findViewById(R.id.btn);
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_pause = (Button) findViewById(R.id.btn_pause);
        btn_resume = (Button) findViewById(R.id.btn_resume);

        btn.setOnClickListener(this);
        btn_start.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        btn_resume.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn:{
                //把当前进度值传过去，避免按了暂停下载，定时器没有执行到MyObserver.getInstance().notifyUpdate(fileInfo);方法。进度更新不到。
                Intent intent = new Intent(AActivity.this, BActivity.class);
                intent.putExtra("progress",progress);
                startActivity(intent);
            }break;
            case R.id.btn_start:{
                fileInfo = new FileInfo();
                fileInfo.setName("正在下载文件");
                timer = new Timer();
                timer.schedule(task,0,1000);
                view.setClickable(false);
            }break;
            case R.id.btn_pause:{
                isPause =true;
            }break;
            case R.id.btn_resume:{
                isPause = false;
            }break;
        }
    }
}
