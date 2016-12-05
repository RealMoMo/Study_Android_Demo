package momo.com.day29_slidingmenu_demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Administrator on 2016/11/17 0017.
 */
public class ContentFragment extends Fragment {

    WebView webView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.content_fragment,container,false);

        setupView(view);

        setUrl("http://www.baidu.com");
        return view;
    }

    private void setupView(View view) {
        webView = (WebView) view.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        //监听
        //没有该监听，app没有什么问题
        webView.setWebChromeClient(new WebChromeClient());
        //没有该监听，app空白。还直接跳到手机浏览器访问你要访问的地址
        webView.setWebViewClient(new WebViewClient());
    }

    /**
     * 供Activity调用，设置url，加载网页
     *
     * @param url
     */
    public void setUrl(String url){
        webView.loadUrl(url);

    }


    public WebView getWebView(){
        return webView;
    }


}
