package top.vimerzhao.imageloader;

import android.os.Bundle;
import android.widget.ImageView;

import top.vimerzhao.plugin.PluginActivity;

public class MainActivity extends PluginActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Plugin App");
        ((ImageView) findViewById(R.id.iv_logo)).setImageResource(R.drawable.android);
    }
}
