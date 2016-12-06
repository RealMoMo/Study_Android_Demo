package momo.com.day42_volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Volley的ImageLoader.ImageCache 图片缓存工具类
 * 实现ImageLoader.ImageCache
 */
public class BitmapCache implements ImageLoader.ImageCache {

    private Context context;
    private LruCache<String,Bitmap> lruCache;

    public BitmapCache(Context context) {
        this.context = context;
        //设置大小LruCache缓存大小，通常就app运行内存的1/8
        lruCache = new LruCache<String,Bitmap>((int) (Runtime.getRuntime().maxMemory()/8)){

            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        //设置图片的文件名
        String bitmapUrl = url.substring(url.lastIndexOf("/")+1,url.length());
        Bitmap bitmap = lruCache.get(bitmapUrl);
        //内存没有
        if(bitmap==null){
            //到sdcard取
            bitmap = getBitmapFromSDCard(bitmapUrl);
            if(bitmap!=null){
                //sdcard取到后，把图片存到内存
                lruCache.put(bitmapUrl,bitmap);
            }
        }

        return bitmap;
    }

    private Bitmap getBitmapFromSDCard(String bitmapUrl) {
        return BitmapFactory.decodeFile(new File(context.getExternalCacheDir(),bitmapUrl).getAbsolutePath());
    }


    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        String bitmapUrl = url.substring(url.lastIndexOf("/")+1,url.length());
        //内存没有，存到内存
        if(lruCache.get(bitmapUrl)==null){
            lruCache.put(bitmapUrl,bitmap);
        }
        //sdcard没有，存到sdcard
        if(getBitmapFromSDCard(bitmapUrl) ==null){
            saveBitmapToSDCard(bitmapUrl,bitmap);
        }
    }

    private void saveBitmapToSDCard(String bitmapUrl, Bitmap bitmap) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(context.getExternalCacheDir(),bitmapUrl));

            if(bitmapUrl.endsWith(".png")||bitmapUrl.endsWith(".PNG")){
                //如果第一个参数为png(无损图片)，则第二个参数无效
                bitmap.compress(Bitmap.CompressFormat.PNG,0,fos);
            }else{
                bitmap.compress(Bitmap.CompressFormat.JPEG,10,fos);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
