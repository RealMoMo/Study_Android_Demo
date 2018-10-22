package top.vimerzhao.plugin;

import android.content.pm.PackageInfo;
import android.content.res.Resources;

import dalvik.system.DexClassLoader;

public class PluginApk {
    public PackageInfo packageInfo;
    public DexClassLoader classLoader;
    public Resources pluginRes;

    public PluginApk(Resources pluginRes) {
        this.pluginRes = pluginRes;
    }

}
