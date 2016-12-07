package momo.com.day43_camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Administrator on 2016/12/7 0007.
 */
public class BigImgLoad {

    public static Bitmap getSimplingBitmap(String filePath,int viewWidth,int  viewHeight){

        BitmapFactory.Options ops = new BitmapFactory.Options();

        ops.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(filePath,ops);

        int width = ops.outWidth;
        int height = ops.outHeight;

        int wScale = (int) Math.ceil(width/viewWidth);
        int hScale = (int) Math.ceil(height/viewHeight);

        int scale  = wScale>hScale?wScale:hScale;

        ops.inJustDecodeBounds=false;
        ops.inSampleSize = scale;
        ops.inPreferredConfig = Bitmap.Config.ALPHA_8;


        return BitmapFactory.decodeFile(filePath,ops);
    }
}
