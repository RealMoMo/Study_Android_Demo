package momo.com.week6;

import android.app.Application;
import android.view.View;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;

/**
 * 自定义Application
 *
 * 一般用来保存一些全局的变量或初始化全局的组件
 */
public class MainApplication extends Application {

        @Override
        public void onCreate() {
            super.onCreate();
            //初始化百度地图
            //在使用SDK各组件之前初始化context信息，传入ApplicationContext
            //注意该方法要再setContentView方法之前实现
            SDKInitializer.initialize(getApplicationContext());


        }



}
