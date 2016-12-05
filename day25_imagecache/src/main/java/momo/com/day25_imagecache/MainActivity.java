package momo.com.day25_imagecache;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Bitmap> {


    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView) findViewById(R.id.iv);
    }


    //点击，启动异步任务，下载下一张图片
    public void loadNext(View view){
        //获取LoaderManager
        LoaderManager loaderManager = getSupportLoaderManager();
        //LoaderManager.restartLoader
        loaderManager.restartLoader(1,null,this);

    }


    @Override
    public Loader<Bitmap> onCreateLoader(int id, Bundle args) {
        MyLoader myLoader = new MyLoader(this);

        return myLoader;
    }

    @Override
    public void onLoadFinished(Loader<Bitmap> loader, Bitmap data) {
        //更新界面
        iv.setImageBitmap(data);
    }

    @Override
    public void onLoaderReset(Loader<Bitmap> loader) {

    }

    static int i=0;


    //自定义Loader,写成内部类，一定要写成静态，防止内存泄漏。
    static class MyLoader extends AsyncTaskLoader<Bitmap>{

        public MyLoader(Context context) {
            super(context);
        }

        @Override
        public Bitmap loadInBackground() {
            //联网下载图片
            String http ="http://www.ytmfdw.com/image/img" + (i++ % 9 + 1) + ".jpg";
            Bitmap bitmap = null;
            try {
                bitmap = HttpUtils.getBitmapByHttp(http);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            forceLoad();
        }
    }

}
