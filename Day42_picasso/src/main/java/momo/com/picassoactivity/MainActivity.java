package momo.com.picassoactivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class MainActivity extends AppCompatActivity {

    ImageView iv;
    final static String IMAGEURL="http://cdn8.staztic.com/app/a/5993/5993203/volley-17-l-280x280.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = (ImageView) findViewById(R.id.iv);
    }


    public void loadImg(View view){


        Transformation transformation = new Transformation() {

            //参数为下载到的原图
            @Override
            public Bitmap transform(Bitmap source) {
                int width = source.getWidth();
                int height = source.getHeight();
                //圆形图片的半径
                int radius = Math.min(width,height)/2;

                Bitmap blankBitmap = Bitmap.createBitmap(radius*2,radius*2, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(blankBitmap);
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                canvas.drawCircle(radius,radius,radius,paint);
                //Xfermode有16种（百度去，大概了解下）
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(source,0,0,paint);

                if(source!=null && !source.isRecycled()){
                    source.recycle();
                }


                return blankBitmap;
            }


            @Override
            public String key() {
                return "picasso";
            }
        };

        Picasso picasso = Picasso.with(this);
        picasso
                //设置图片资源路径
                .load(Uri.parse(IMAGEURL))
                //对下载到的图片进行二次处理
                .transform(transformation)
                //设置标签(通常配合listview的滚动状态进行设置);  pauseTag()和resumeTag()方法中用到
//                .tag();
                //对图片进行剪裁
//                .resize(200,200)
                //设置缩放模式,使用centerCrop()时必须对图片进行剪裁
//                .centerCrop()//需要调用resize方法,对图片进行剪裁
                //使用fit缩放模式时不可以对图片进行剪裁
//                .fit()
                //占位图，图片加载出来之前显示的默认图片
                .placeholder(R.mipmap.ic_launcher)
                //加载出错时显示的图片
                .error(R.mipmap.ic_launcher)
                //加载到那个控件，以及下载图片的监听
                .into(iv, new Callback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(MainActivity.this, "加载成功！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(MainActivity.this, "加载失败！", Toast.LENGTH_SHORT).show();
                    }
                });

        //暂停图片加载
//        picasso.pauseTag();
        //恢复图片加载
//        picasso.resumeTag();

    }


    public void loadImg2(View view){
        //此方式创建Picasso对象，拥有更好的扩展性（可指定缓存位置等）
        Picasso picasso = new Picasso.Builder(this)
                //OkHttp3.0改了包名(3.0之前是"com.squareup.okhttp.OkHttpClient"，3.0之后是okhttp3.OkHttpClient)(所以直接new 一个downloader用不了okhttp，用的就是httpurlconnection)
                //所以，要导包compile 'com.jakewharton.picasso:picasso2-okhttp3-downloader:1.1.0'
                .downloader(new OkHttp3Downloader(this.getExternalCacheDir()))
                .build();

//        Picasso.setSingletonInstance(picasso);
        picasso.load(IMAGEURL).into(iv);

    }
}
