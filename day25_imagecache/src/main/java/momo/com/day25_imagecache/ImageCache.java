package momo.com.day25_imagecache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

/**
 * Created by Administrator on 2016/11/11 0011.
 */
public class ImageCache {

    /**
     * 用来存储图片的集合
     *
     * key--->图片的网址String
     *
     * value-->图片Bitmap
     * */
    //static Map<String,Bitmap> cache = new HashMap<>(); //强引用

    static LruCache<String,Bitmap> lruCache;

    //单例模式
    private  ImageCache(){
        //初始化lruCache
        //计算最大内存
        long maxMemory = Runtime.getRuntime().maxMemory();
        Log.d("Tag","maxMemory:"+maxMemory);
        //设置缓存为最大内存的1/8
        lruCache = new LruCache<String,Bitmap>((int) (maxMemory/8)){

            @Override
            protected int sizeOf(String key, Bitmap value) {
                //计算bimap的大小
                int count = value.getByteCount();
                return count;
            }
        };
    }

    static ImageCache imageCache;

    public static ImageCache getInstance(){
        if(imageCache==null){
            imageCache = new ImageCache();
        }
        return imageCache;
    }

    /**
     * 存
     * */
    public  void put(String url,Bitmap bitmap){
        //先保存一份到SDcard
        try {
            SdUtils.saveBitmap(url,bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*    WeakReference<Bitmap> bitmapWeakReference=new WeakReference<Bitmap>(bitmap);            //弱引用
     //弱引用容易被垃圾回收机制回收(不推荐使用弱引用)
     //从弱引用中取出对象
       bitmap= bitmapWeakReference.get();
        if(bitmap!=null){}*/


        //再缓存到内存中
        lruCache.put(url,bitmap);

    }

    /**
     * 根据图片网址，取出图片
     * */
    public  Bitmap get(String url){
        //先从内存中取，如果没取到，再到Sdcard中取
        Bitmap bitmap = lruCache.get(url);
        if(bitmap==null){
            bitmap = SdUtils.getBitmapFromSD(url);
        }
        return bitmap;

    }
}
