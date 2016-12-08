package momo.com.day27_customview;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

import momo.com.day27_customview.widget.PuBuLayout;

/**
 * Created by Administrator on 2016/11/16 0016.
 */
public class PuBuActivity extends AppCompatActivity {

    PuBuLayout puBuLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pubu_layout);
        puBuLayout = (PuBuLayout) findViewById(R.id.pubu_layout);
        //获取AssetManager
        AssetManager manager = getAssets();
        try {
            //获取Assets目录中的文件，得到文件名数组
            String[] images = manager.list("images");
            for(int i=0; i<images.length;i++){
                //向布局中添加Bitmap
                //把文件转换Bitmap
                InputStream in = manager.open("images/" + images[i]);
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                puBuLayout.addImage(bitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
