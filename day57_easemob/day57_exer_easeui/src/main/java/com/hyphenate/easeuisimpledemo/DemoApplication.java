package com.hyphenate.easeuisimpledemo;

import com.hyphenate.easeui.controller.EaseUI;

import android.app.Application;


/**
 * 官网：http://www.easemob.com/
 *
 *
 * 作为库模块配置即时通讯easemob步骤：(快速集成官方的Demo)
 * 1.下载sdk
 * 2.解压选择，其中的examples文件夹easemob
 * 3.剪切其中的simpledemo出来
 * 4.把easemob作业库模块导入，并修改对应的build.gradle
 * 5.把simpledemo中的res和src里面文件拷贝到要实现即时通讯的模块中。
 * 6.相应修改AndroidManifest
 * 略坑的地方，easemob的libs文件夹导了v4包。与我们模块的v7冲突，相当于重复导了v4包。解决方法见7.
 * 7.删除easemob的libs文件夹导了v4包，把我们模块的build.gradle的引入v7包代码，剪切（是剪切！！！）到easemob的build.gradle
 * 8.运行即可
 */
public class DemoApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        EaseUI.getInstance().init(this, null);
    }
    
}
