package top.vimerzhao.plugin;

import android.os.Bundle;

public interface Pluginable {

    public void onCreate(Bundle bundle);

    public void onStart();

    public void onResume();

    public void onStop();

    public void onPause();

    public void onDestroy();
}
