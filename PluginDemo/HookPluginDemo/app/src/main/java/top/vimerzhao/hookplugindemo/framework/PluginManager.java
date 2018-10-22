package top.vimerzhao.hookplugindemo.framework;


import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * Created by vimerzhao
 * Date: 2018/9/30
 * Description: 插件框架基础功能
 */
public class PluginManager {
    private final static String TAG = "PluginManager";
    private static PluginManager sInstance;
    private Context mContext;
    private PluginApp mPluginApp;


    public static PluginManager getInstance(Context context) {
        if (sInstance == null && context != null) {
            sInstance = new PluginManager(context);
        }
        return sInstance;
    }

    private PluginManager(Context context) {
        mContext = context;
    }


    public void hookInstrumentation() {
        try {
            Instrumentation baseInstrumentation = ReflectUtil.getInstrumentation();

            final HookedInstrumentation instrumentation = new HookedInstrumentation(baseInstrumentation, this);

            Object activityThread = ReflectUtil.getActivityThread();
            ReflectUtil.setInstrumentation(activityThread, instrumentation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hookCurrentActivityInstrumentation(Activity activity) {
        ReflectUtil.setActivityInstrumentation(activity, sInstance);
    }


    public void hookToStubActivity(Intent intent) {
        if (Constants.DEBUG) Log.e(TAG, "hookToStubActivity");

        if (intent == null || intent.getComponent() == null) {
            return;
        }
        String targetPackageName = intent.getComponent().getPackageName();
        String targetClassName = intent.getComponent().getClassName();

        if (mContext != null
                && !mContext.getPackageName().equals(targetPackageName)
                && isPluginLoaded(targetPackageName)) {
            if (Constants.DEBUG) Log.e(TAG, "hook " +  targetClassName + " to " + Constants.STUB_ACTIVITY);

            intent.setClassName(Constants.STUB_PACKAGE, Constants.STUB_ACTIVITY);
            intent.putExtra(Constants.KEY_IS_PLUGIN, true);
            intent.putExtra(Constants.KEY_PACKAGE, targetPackageName);
            intent.putExtra(Constants.KEY_ACTIVITY, targetClassName);
        }
    }

    public boolean hookToPluginActivity(Intent intent) {
        if (Constants.DEBUG) Log.e(TAG, "hookToPluginActivity");
        if (intent.getBooleanExtra(Constants.KEY_IS_PLUGIN, false)) {
            String pkg = intent.getStringExtra(Constants.KEY_PACKAGE);
            String activity = intent.getStringExtra(Constants.KEY_ACTIVITY);
            if (Constants.DEBUG) Log.e(TAG, "hook " + intent.getComponent().getClassName() + " to " + activity);
            intent.setClassName(pkg, activity);
            return true;
        }
        return false;
    }

    private boolean isPluginLoaded(String packageName) {
        // TODO 检查packageNmae是否匹配
        return mPluginApp != null;
    }



    public PluginApp loadPluginApk(String apkPath) {
        String addAssetPathMethod = "addAssetPath";
        PluginApp pluginApp = null;
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod(addAssetPathMethod, String.class);
            addAssetPath.invoke(assetManager, apkPath);
            Resources pluginRes = new Resources(assetManager,
                    mContext.getResources().getDisplayMetrics(),
                    mContext.getResources().getConfiguration());
            pluginApp = new PluginApp(pluginRes);
            pluginApp.mClassLoader = createDexClassLoader(apkPath);
        } catch (IllegalAccessException
                | InstantiationException
                | NoSuchMethodException
                | InvocationTargetException e) {
            e.printStackTrace();
        }
        return pluginApp;
    }

    private DexClassLoader createDexClassLoader(String apkPath) {
        File dexOutputDir = mContext.getDir("dex", Context.MODE_PRIVATE);
        return new DexClassLoader(apkPath, dexOutputDir.getAbsolutePath(),
                null, mContext.getClassLoader());

    }

    public boolean loadPlugin(String apkPath) {
        File apk = new File(apkPath);
        if (!apk.exists()) {
            return false;
        }
        mPluginApp = loadPluginApk(apkPath);
        return mPluginApp != null;
    }

    public PluginApp getLoadedPluginApk() {
        return mPluginApp;
    }
}
