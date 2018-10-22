package top.vimerzhao.plugin;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;

import java.lang.reflect.Constructor;

public class LifeCircleController implements Pluginable {
    Activity mProxy;
    PluginActivity mPlugin;
    Resources mResources;
    Resources.Theme mTheme;
    PluginApk mPluginApk;
    String mPluginClazz;

    public LifeCircleController(Activity activity) {
        mProxy = activity;
    }

    public void onCreate(Bundle bundle) {
        mPluginClazz = bundle.getString(Constants.PLUGIN_CLASS_NAME);
        String packageName = bundle.getString(Constants.PACKAGE_NAME);
        mPluginApk = PluginManager.getInstance().getPluginApk(packageName);
        try {
            mPlugin = (PluginActivity) loadPluginable(mPluginApk.classLoader, mPluginClazz);
            mPlugin.attach(mProxy, mPluginApk);
            mResources = mPluginApk.pluginRes;
            mPlugin.onCreate(bundle);
        } catch (Exception e) {
            VLog.log("Fail in LifeCircleController onCreate");
            VLog.log(e.getMessage());
            e.printStackTrace();
        }

    }
    private Object loadPluginable(ClassLoader classLoader, String pluginActivityClass)
            throws Exception {
        Class<?> pluginClz = classLoader.loadClass(pluginActivityClass);
        Constructor<?> constructor = pluginClz.getConstructor(new Class[] {});
        constructor.setAccessible(true);
        return constructor.newInstance(new Object[] {});
    }

    @Override
    public void onStart() {
        if (mPlugin != null) {
            mPlugin.onStart();
        }
    }

    @Override
    public void onResume() {
        if (mPlugin != null) {
            mPlugin.onResume();
        }
    }

    @Override
    public void onStop() {
        mPlugin.onStop();
    }

    @Override
    public void onPause() {
        mPlugin.onPause();
    }

    @Override
    public void onDestroy() {
        mPlugin.onDestroy();
    }

    public Resources getResources() {
        return mResources;
    }

    public Resources.Theme getTheme() {
        return mTheme;
    }

    public AssetManager getAssets() {
        return mResources.getAssets();
    }

}
