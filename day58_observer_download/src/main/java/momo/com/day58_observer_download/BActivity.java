package momo.com.day58_observer_download;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

/**
 * 方式1：通过Activity实现Watcher接口
 */
public class BActivity extends AppCompatActivity implements MyObserver.Watcher{


    private Button btn;
    private ProgressBar progressBar;

    private MyObserver observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        setupViews();

        initMsg();
    }

    private void initMsg() {
        observer = MyObserver.getInstance();
        observer.addWatcher(this);

    }

    private void setupViews() {
        btn = (Button) findViewById(R.id.btn);
        progressBar = (ProgressBar) findViewById(R.id.pb);
        //设置初始进度
        progressBar.setProgress(getIntent().getIntExtra("progress",0));

        btn.setText("跳转到CActivity");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BActivity.this,CActivity.class);
                intent.putExtra("progress",progressBar.getProgress());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        observer.removeWatcher(this);
    }

    @Override
    public void onProgress(FileInfo fileInfo) {
        if(progressBar!=null&&fileInfo!=null){
            progressBar.setProgress(fileInfo.getProgress());
        }
    }
}
