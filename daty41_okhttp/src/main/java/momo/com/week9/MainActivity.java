package momo.com.week9;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 * OkHttp详解学习：
 * http://blog.csdn.net/iispring/article/details/51661195
 *
 * 具体MIME：
 * http://www.w3school.com.cn/media/media_mimeref.asp
 * <p/>
 * http://www.iana.org/assignments/media-types/media-types.xhtml
 * <p/>
 * 几种常见数据的MIME类型值：
 * json ：application/json
 * xml：application/xml
 * png：image/png
 * jpg： image/jpeg
 * gif：image/gif
 */
public class MainActivity extends AppCompatActivity {


    TextView tv;

    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tv.setText(msg.obj.toString());
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv= (TextView) findViewById(R.id.tv);
    }


    /**
     * 同步请求
     * a.创建OkHttpClient对象
     * b.创建一个Requset请求
     * c.发起同步请求(注意要在子线程开启请求)
     *
     * @param view
     */
    public void btnClick1(View view) {
        //a.
        //OkHttp3.0版本之后的创建方式--构建者模式（3.0之前都是直接new okhttpclient）
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        //b.
        Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .build();

        final Call call = client.newCall(request);

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    //c.发起同步请求
                    Response response = call.execute();
                    //获取请求结果的内容
                    String result = response.body().string();

                    Message message = handler.obtainMessage();
                    message.obj =result;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    /**
     * 异步步请求
     * a.创建OkHttpClient对象
     * b.创建一个Requset请求
     * c.发起异步请求
     *
     * @param view
     */
    public void btnClick2(View view) {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .build();

        Call call = client.newCall(request);
        //c.发起异步请求
        call.enqueue(new Callback() {

            ////请求失败时回调（找不到服务器、网络不通），该方法在子线程执行
            @Override
            public void onFailure(Call call, IOException e) {

            }

            //请求成功时回调，该方法在子线程执行
            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                Log.d("Tag", Thread.currentThread().getName());
                if (response.isSuccessful()) {
                    //获取响应头信息
                    Headers headers = response.headers();
                    Set<String> names = headers.names();

                    StringBuilder sb = new StringBuilder();

                    //迭代器遍历
                    Iterator<String> iterator = names.iterator();
                    while (iterator.hasNext()) {
                        String name = iterator.next();
                        sb.append("key:"+name+",value:"+headers.get(name)).append("\n");
//                        Log.d("Tag", headers.get(name));
                    }

                    Message message = handler.obtainMessage();
                    message.obj =sb.toString();
                    handler.sendMessage(message);



//                    String result = response.body().string();
//                    Log.d("Tag", result);
                } else {

                }
            }


        });
    }

    //GET请求传递参数
    public void btnClick3(View view) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        Request request = new Request.Builder()
                .url("http://10.2.155.14:8080/upload?username=realmo&password=666666")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    /**
     * POST请求传递参数（IO流）
     * 通过IO流向服务端传递数据
     *
     * @param view
     */
    public void btnClick4(View view) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        //了解MediaType其他类型看最上面的内容
        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        JSONObject json = new JSONObject();
        try {
            json.put("username", "realmo");
            json.put("password", "666666");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(mediaType, json.toString());
        Request request = new Request.Builder()
                .url("http://10.2.155.14:8080/upload")
                //设置post请求的请求体
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }


    /**
     * POST请求传递参数(表单)
     * 通过表单上传数据
     * @param view
     */
    public void btnClick5(View view) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        FormBody body = new FormBody.Builder()
                .add("username", "realmo")
                .add("password", "888888")
                .build();
        Request request = new Request.Builder()
                .url("http://10.2.155.14:8080/upload")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }

    //POST请求上传文件
    public void btnClick6(View view) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        MediaType mediaType = MediaType.parse("appliction/octet-stream");
        //String s = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Download";
        //上面等同于下面的  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS
        RequestBody body = RequestBody.create(mediaType, new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "11.jpg"));
        Request request = new Request.Builder()
                .url("http://10.2.155.14:8080/upload")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }

    public void btnClick7(View view) {

        OkHttpClient client = new OkHttpClient.Builder()
                //设置缓存位置(开发者来判断，确定放到内部还是外部存储)(当然还得判断url是否允许缓存，如百度网页不予许缓存的)
                .cache(new Cache(this.getExternalCacheDir(), 10 * 1024 * 2024))
                .build();
        //可以通过设置CacheControl属性，给请求Request设置cacheControl
        CacheControl cc = new CacheControl.Builder().noCache().build();
        Request request = new Request.Builder()
                //设置cacheControl
//                .cacheControl(cc)
                .url("http://www.tngou.net/api/food/classify")
//                .url("http://www.baidu.com")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                    if(response.isSuccessful()){
                        Message msg = handler.obtainMessage();
                        msg.obj =response.body().string();
                        handler.sendMessage(msg);

                        Headers headers = response.headers();
                        Set<String> names = headers.names();

                        Iterator<String> iterator = names.iterator();
                        while (iterator.hasNext()) {
                            String name = iterator.next();
//                            Log.d("Tag", "name:"+name+",value:"+headers.get(name));
                        }
                    }
            }
        });

    }


}
