package momo.com.day29_pulltorefreshtext;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2016/11/17 0017.
 */
public class WebViewActivity extends AppCompatActivity {


    String[] urls = {
            "http://www.baidu.com",
            "http://www.163.com",
            "http://www.sina.com",

    };

    int currentIndex;

    WebView webView;
    PtrFrameLayout refresh;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        setupView();
    }

    private void setupView() {
        webView = (WebView) findViewById(R.id.webView);

        //设置webView
        //支持js
        webView.getSettings().setJavaScriptEnabled(true);
        //监听
        webView.setWebChromeClient(new WebChromeClient() {

            //网页进度发生改变时回调
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });

        webView.setWebViewClient(new WebViewClient() {

            /*
                   *网页开始加载回调
                   *
                   * */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            /*
                       *网页加载结束回调
                       *
                       * */
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //停止刷新
                refresh.refreshComplete();
            }
        });

        //加载网页
        webView.loadUrl("http://www.baidu.com");

        //刷新控件
        refresh = (PtrFrameLayout) findViewById(R.id.fragment_rotate_header_with_text_view_frame);

        //设置刷新监听
        refresh.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //刷新动作
                // webView.reload();

                webView.loadUrl(urls[++currentIndex % urls.length]);
            }

            /*子控件是否能下拉
           *
           * WebView、ListView、ScrollView
           *
           * 参数二：被刷新控件包含的子控件
           * */
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                Log.d("Tag", (content == webView ? true : false) + "");//，上拉，返回true
                return super.checkCanDoRefresh(frame, webView, header);
            }
        });

    }
}
