package momo.com.day58_webview_js;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * 本Demo:实现webview加载本地的html。并实现html的js与android的交互。
 */
public class MainActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        //通过反射机制，让html的js调用java代码,参数二是js定义的关键字
        webView.addJavascriptInterface(new AndroidJs(),"momo");

        webView.setWebChromeClient(new WebChromeClient(){
            //webview 处理alart
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Log.d("momo","url:"+url);
                Log.d("momo","message:"+message);
                Log.d("momo","result:"+result.toString());
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).setTitle("JS标题").setMessage(message).create();
                dialog.show();
                //return true 自己处理(之后会有问题，再点击按钮就不会触发了)
                return true;
                //return false webview自己处理 不会有问题
//                return  false;
            }
        });

        //加载本地assets的html  注意：下面是asset不是assets
        webView.loadUrl("file:///android_asset/my.html");
    }


    //点击按钮，调用js的代码
    public void doClickJs(View view) {

        webView.loadUrl("javascript:print()");

    }


    public class AndroidJs{

        //需要添加JavascriptInterface的注解
        @JavascriptInterface
        public void openCamare(){
            //打开相机
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivity(intent);
        }
    }
}
