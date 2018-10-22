package top.vimerzhao.hookplugindemo.framework;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;

import java.io.File;

/**
 * Created by vimerzhao
 * Date: 2018/9/30
 * Description:
 */
public class PluginApp {
    public Resources mResources;
    public ClassLoader mClassLoader;


    public PluginApp(Resources mResources) {
        this.mResources = mResources;
    }
}
