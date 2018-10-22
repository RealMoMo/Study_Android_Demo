package top.vimerzhao.hookplugindemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import top.vimerzhao.hookplugindemo.framework.Constants;

/**
 * Created by vimerzhao
 * Date: 2018/9/30
 * Description:
 */
public class StubActivity extends Activity {
    public static final String TAG = "StubActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView) findViewById(R.id.tv_launch)).setText("StubActivity");
        if (Constants.DEBUG) Log.e(TAG, "onCreate");
    }
}
