package momo.com.day43_camera;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ImageView iv;

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    File file;

    int ivW;
    int ivH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView) findViewById(R.id.iv);


        //对控件设置布局监听
        iv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //获取控件的宽高
                ivW = iv.getWidth();
                ivH = iv.getHeight();
                //控件宽高设置了wrap_content返回是0
//                Log.d("Tag","onGlobalLayout 控件宽："+ivW);
//                Log.d("Tag","onGlobalLayout 控件高："+ivH);

                //获取控件在布局中的宽度
//                ViewGroup.LayoutParams params = iv.getLayoutParams();
//                int paramsW = params.width;
//                Log.d("Tag","onGlobalLayout 布局宽："+paramsW);

                //移除监听(需要api最小为16)
                iv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }


    public void takePhoto1(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //拍照时如果没有指定照片的存储位置，则只返回所拍摄照片的缩略图
        startActivityForResult(intent,1);
    }



    public void takePhoto2(View view){

       file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),format.format(new Date())+".png");

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent,2);
    }

    public void takePhoto3(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.ImageColumns.DISPLAY_NAME,format.format(new Date())+".png");
        Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent,3);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            iv.setImageBitmap(bitmap);
//            Log.d("Tag","width:"+bitmap.getWidth()+",height:"+bitmap.getHeight());
        }else if(requestCode ==2){
            //momo手机拍照，照片宽高太大,直接不显示照片显示空白，app不会崩-->momo.com.day43_camera W/OpenGLRenderer: Bitmap too large to be uploaded into a texture (3120x4160, max=4096x4096)
            //所以，用了二次采样并对ImageView控件的宽高设置不为wrap_content。方便计算控件宽高
            //如果非要wrap_content,该ImageView嵌套ViewGroup,其宽高为match_parent即可。通过测量ImageView的ViewGroup.LayoutParams的宽高即可。
            iv.setImageBitmap(BigImgLoad.getSimplingBitmap(file.getAbsolutePath(),ivW,ivH));
        }else if(requestCode==3){
            Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Images.ImageColumns.DATA},null,null,
                    MediaStore.Images.ImageColumns.DATE_ADDED+ " DESC"); //注意DESC前面带有空格,DESC含义按降序排列
            if(cursor!=null){
                if(cursor.moveToFirst()){
                    iv.setImageBitmap(BitmapFactory.decodeFile(cursor.getString(0)));
                }
                cursor.close();
            }
        }

    }
}
