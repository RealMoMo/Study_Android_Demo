package top.vimerzhao.plugin;

import android.util.Log;

public class VLog {
    private final static String TAG = "vimerzhao_plugin";
    private final static boolean IS_DEBUG = true;

    public static void log(String info) {
        if (IS_DEBUG) Log.e(TAG, info);
    }

}
