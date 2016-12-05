package momo.com.day25_bigimgload;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //找到控件
        iv = (ImageView) findViewById(R.id.iv);
        //因为控件的宽高尚未初始化，所以用屏幕宽高演示
        //得到屏幕的宽高
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        //获取控件的宽度
        int tmpW = iv.getWidth();
        Log.d("Tag","控件宽："+tmpW);

        //获取控件在布局中的宽度
        ViewGroup.LayoutParams params = iv.getLayoutParams();
        int paramsW = params.width;
        Log.d("Tag","布局宽："+paramsW);



        //得到二次采样后的Bitmap
//        Bitmap bitmap = BitmapUtils.getBitmap(getResources(),R.drawable.bigimg,width,height);
        //设置
//        iv.setImageBitmap(bitmap);

        //对控件设置布局监听
        iv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.d("Tag","onGlobalLayout");

                //获取控件的宽度
                int tmpW = iv.getWidth();
                int tmpH = iv.getHeight();
                Log.d("Tag","onGlobalLayout 控件宽："+tmpW);

                //得到二次采样后的Bitmap
                Bitmap bitmap = BitmapUtils.getBitmap(getResources(),R.drawable.bigimg,tmpW,tmpH);
                //设置
                iv.setImageBitmap(bitmap);

                //获取控件在布局中的宽度
//                ViewGroup.LayoutParams params = iv.getLayoutParams();
//                int paramsW = params.width;
//                Log.d("Tag","onGlobalLayout 布局宽："+paramsW);


                //移除监听
                iv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

    }
}
