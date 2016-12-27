package momo.com.day57_observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/27 0027.
 */

public class MyObserver {

    private static MyObserver observer;
    private List<Watcher> list;

    //单例模式
    private MyObserver() {
        list = new ArrayList<>();
    }

    public static MyObserver getInstance() {
        if (observer == null) {
            observer = new MyObserver();
        }
        return observer;
    }


    public void addWatcher(Watcher watcher){
        list.add(watcher);
    }

    public void removeWatcher(Watcher watcher){
        list.remove(watcher);
    }

    public void notifyUpdate(MessageImage messageImage) {
        for (int i = 0; i < list.size(); i++) {
            Watcher w = list.get(i);
            if (messageImage.getFromType() == w.fromType) {
                list.get(i).setImageId(messageImage);
                break;
            }
        }
    }


    public static abstract class Watcher {

        public int fromType;

        public Watcher(int fromType) {
            this.fromType = fromType;
        }

        public abstract void setImageId(MessageImage messageImage);

    }

}
