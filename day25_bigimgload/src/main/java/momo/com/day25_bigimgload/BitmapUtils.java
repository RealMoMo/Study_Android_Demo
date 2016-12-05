package momo.com.day25_bigimgload;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Administrator on 2016/11/11 0011.
 */
public class BitmapUtils {


    /**
     * 对res中的图片进行二次采样
     *
     * @param res   Resources对象
     * @param id    被加载的图片的id
     * @param viewW 控件的宽度
     * @param viewH 控件的高度
     * @return
     */
    public static Bitmap getBitmap(Resources res, int id, int viewW, int viewH) {
        //1.声明Options
        BitmapFactory.Options ops = new BitmapFactory.Options();
        //2.设置只解析边缘
        ops.inJustDecodeBounds = true;   //解析的bitmap 为null,在ops中，得到图片的宽高
        //3.解析图片，得到图片的宽高
        BitmapFactory.decodeResource(res, id, ops);

        int imgW = ops.outWidth;
        int imgH = ops.outHeight;
        //4.计算采样比：图片的宽高/控件的宽高
        int scalx = imgW / viewW;
        int scaly = imgH / viewH;
        //采样比越高，图片加载的像素点越少
        //取采样比大的
        int scal = (scalx > scaly )? scalx : scaly;
        //5.设置真正解析图片
        ops.inJustDecodeBounds =false;
        //设置采样比
        ops.inSampleSize = scal;
        //设置图片的质量
        ops.inPreferredConfig = Bitmap.Config.ALPHA_8;
        //第二次采样,返回解析的图片
        return BitmapFactory.decodeResource(res,id,ops);
    }
}
