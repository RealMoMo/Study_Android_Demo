package top.vimerzhao.plugin;

public interface Attachable<T> {
    void attach(T proxy, PluginApk apk);
}

