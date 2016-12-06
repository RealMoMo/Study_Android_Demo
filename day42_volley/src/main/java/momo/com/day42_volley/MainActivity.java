package momo.com.day42_volley;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * 推荐博客：
 * http://blog.csdn.net/u012702547/article/details/49718955
 *
 * Volley频繁请求，但数据量不大(小于1M左右)。(不可以io流传递)
 * Volley的RequestQueue初始化建议在application进行。
 *
 * Volley核心功能：给开发者提供各种Request
 * 开发者也可以自己定义Request
 *
 * Volley自带缓存机制
 */
public class MainActivity extends AppCompatActivity {

    TextView tv;
    ImageView iv;
    //Volley自带的图片加载控件
    NetworkImageView niv;

    RequestQueue queue;

    String url="http://www.csdn.net";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化RequestQueue
        queue = ((MyApp)getApplication()).getRequestQueue();

        //找到控件
        setupView();

        //NetworkImageView控件加载网络图片
//        niv.setImageUrl("http://cdn8.staztic.com/app/a/5993/5993203/volley-17-l-280x280.png"
//                ,new ImageLoader(queue,new BitmapCache(this)));
    }

    private void setupView() {
        tv = (TextView) findViewById(R.id.tv);
        iv = (ImageView) findViewById(R.id.iv);
        niv = (NetworkImageView) findViewById(R.id.niv);
    }


    //string
    public void btnClick1(View view){

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tv.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //若联网失败等情况
                //缓存中找
                Cache.Entry entry = queue.getCache().get(url);

                if(entry==null){
                    return;
                }

                byte[] data = entry.data;
                if(data!=null && data.length>0){
                    tv.setText(new String(data,0,data.length));
                }
            }
        });
//        {
//            //若设置Request.Method.POST
//            //post请求传参方式
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                Map<String,String> map = new HashMap<>();
//                map.put("name","realmo");
//                return map;
//            }
//        };

        queue.add(request);
    }

    //jsonobject
    public void btnClick2(View view){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET
                , "http://www.tngou.net/api/lore/classify", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                tv.setText(response.optString("tngou"));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);

    }

    //jsonarray
    public void btnClick3(View view){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "", null
                , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);

    }

    //image
    public void btnClick4(View view){
        ImageRequest request = new ImageRequest("http://cdn8.staztic.com/app/a/5993/5993203/volley-17-l-280x280.png", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                iv.setImageBitmap(response);
            }
        }, 200, 200, ImageView.ScaleType.FIT_XY, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);

    }

    //imageloader
    public void btnClick5(View view){
        ImageLoader loader = new ImageLoader(queue,new BitmapCache(this));

        /**
         * 参数2：默认图片
         * 参数3：错误图片
         */
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(iv,
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher);

        loader.get("http://cdn8.staztic.com/app/a/5993/5993203/volley-17-l-280x280.png",imageListener);
    }

}
