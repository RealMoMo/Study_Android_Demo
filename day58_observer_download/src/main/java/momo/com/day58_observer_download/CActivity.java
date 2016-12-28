package momo.com.day58_observer_download;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

/**
 * 方式2：通过直接new watcher
 */
public class CActivity extends AppCompatActivity  {


    private ProgressBar progressBar;

    private MyObserver observer ;

    private MyObserver.Watcher watcher = new MyObserver.Watcher() {
        @Override
        public void onProgress(FileInfo fileInfo) {
            if(progressBar!=null&&fileInfo!=null){
                progressBar.setProgress(fileInfo.getProgress());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        setupViews();

        initMsg();
    }

    private void initMsg() {
        observer = MyObserver.getInstance();
        observer.addWatcher(watcher);

    }

    private void setupViews() {
        progressBar = (ProgressBar) findViewById(R.id.pb);
        progressBar.setProgress(getIntent().getIntExtra("progress",0));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        observer.removeWatcher(watcher);
    }
}
