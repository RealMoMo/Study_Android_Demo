package momo.com.day25_imagecache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/11/11 0011.
 */
public class HttpUtils {

    /**
     * 从网上下载图片
     *
     * @param http 图片网址
     * @return
     */
    public static Bitmap getBitmapByHttp(String http) throws Exception {

        Bitmap bitmap;
        //在下载之前，先从缓存中取，如有缓存中有，直接返回bitmap.没有，则看SDcard有没有（在Cache工具类里判断）。
        // 若缓存，SDcard都没有则下载
        bitmap = ImageCache.getInstance().get(http);
        if(bitmap != null){
            return bitmap;
        }

        HttpURLConnection conn = null;
        URL url = new URL(http);
        conn = (HttpURLConnection) url.openConnection();
        InputStream in = conn.getInputStream();
        bitmap = BitmapFactory.decodeStream(in);

        //下载完成后，保存缓存与SDcard
        ImageCache.getInstance().put(http,bitmap);
        //关流
        in.close();
        conn.disconnect();

        Log.d("Tag","下载："+http);

        return bitmap;
    }
}
