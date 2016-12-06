package momo.com.day42_volley;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2016/12/6 0006.
 */
public class MyApp extends Application {


    private RequestQueue queue;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化RequestQueue
        queue = Volley.newRequestQueue(this);

    }


    public RequestQueue getRequestQueue(){
        return queue;
    }
}
