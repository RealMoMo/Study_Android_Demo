package momo.com.day43_activeandroid;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

/**
 * Created by Administrator on 2016/12/7 0007.
 */
public class MyApp extends Application{



    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
