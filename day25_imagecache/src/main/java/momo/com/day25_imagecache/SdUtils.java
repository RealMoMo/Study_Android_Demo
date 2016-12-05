package momo.com.day25_imagecache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2016/11/11 0011.
 */
public class SdUtils {

    static final String PATH ="/imgCache/";


    /**
     * 保存图片到SDcard
     *
     * @param url    图片网址           http://www.xxx/abc.jpg
     * @param bitmap 图片内容
     */
    public static void saveBitmap(String url, Bitmap bitmap) throws Exception {
        if(!isMount()){
            Log.d("Tag","SD卡没有挂在好---");
            return;
        }

        //获取保存的目录
        String path = getSDCardPath()+PATH;
        //如果目录不存在，创建
        File parent = new File(path);
        if(!parent.exists()){
            //目录不存在，创建
            parent.mkdirs();
        }
        //创建保存的文件
        //截取文件名
        String name = url.substring(url.lastIndexOf("/")+1);
        //创建文件
        File imgFile = new File(parent,name);
        //把文件转成FileOutputStream
        FileOutputStream out = new FileOutputStream(imgFile);

        bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);

        out.close();



    }


    /**
     * 从指定的目录　中，取出Bitmap
     *
     * @param url 图片的网址：http://xxx.xxx/abc.jpg
     * @return
     */
    public static Bitmap getBitmapFromSD(String url){

        if(!isMount()){
            Log.d("Tag","SD卡没有挂在好---");
            return null;
        }
        //获取保存的目录
        String path = getSDCardPath()+PATH;
        //根据url，获取文件名
        //截取文件名
        String name = url.substring(url.lastIndexOf("/")+1);

        File file = new File(path,name);
        //如果文件不存在，返回null
        if(!file.exists()){
            return null;
        }

        //构建文件地址
        //通过BitmapFactory.decodeFile
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }

    /**
     * 获取SDcard的根目录
     *
     * @return
     */
    public static String getSDCardPath(){
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * SD卡是否挂载好
     *
     * @return  true:挂载好
     * false:没准备好
     * */
    public static boolean isMount(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}
