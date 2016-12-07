package momo.com.day43_databinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import momo.com.day43_databinding.databinding.ActivityMainBinding;

/**
 * DataBinding
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        User user = new User("momo","123456");
        user.setUserface("https://raw.githubusercontent.com/android-cn/android-open-project-analysis/master/tool-lib/network/volley/image/Volley-run-flow-chart.png");
        binding.setUser(user);
    }
}
