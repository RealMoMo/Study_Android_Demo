package com.momo.day58_textview_loadhtml;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ImageSpan;
import android.widget.TextView;

import java.net.URL;

/**
 * 本Demo:用textview加载html
 *
 * 学习网址：
 * http://www.cnblogs.com/mxgsa/archive/2012/12/14/2816775.html
 *
 */
public class MainActivity extends AppCompatActivity {

    private int width;
    private String content = "<p><img src=\"https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1476000277&di=816a9e199d176952c1fdc075e27207c4&src=http://www.pp3.cn/uploads/201609/201609105.jpg\"/></p><p> 主席： 五月二十日，我找了周总理谈说是可靠的）实行不<span class='word-tag' onclick='newshotword(\\\"逮捕\\\")'>逮捕</span>、不关押、不杀、不撤职等“四不”，如果他们某个成员有错误，可经过党内思想批评来解决，他们有病，可找人代替工 作，如果病故，则提升其他人接替。对于久病要求退休者，则按退休干部处理。“一要”就是遇特殊情况，要执行主席面授的机动指示。</p><p>强龙历史网：</p><p> 林彪叛逃前写给毛泽东一封信曝光 内容惊人【图】林彪与<span class='word-tag' onclick='newshotword(\\\"毛主席\\\")'>毛主席</span>相谈甚欢</p><p> 第三，为保证首都安全，首都附近的三个人造山，建议由华东、华北、山东各派一个独立营来担任固守。</p><p> 第四，建议将三十八军调离华北。这个部队虽然是很好的部队，但放在首都附近不甚适宜，以调往别处，换一个原二野、三野或一野的军来，接替他们的任务为宜。</p><p> 我的以上想法，是看了这次批陈整风会议文件，有的同志在担心着安全问题。他们的心情是忧虑的，因而是值得重视和深思的。我想，为了防止万一发生事故起见，所以想到以上的做法，但这些方法必然是不完备或甚至是不正确的，特报告主席，请主席考虑交总理遵办。</p><p><img src=\"http://www.chnlung.cn/uploads/allimg/160422/1-1604220QGVI.jpg\"/></p>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        struct();
        setContentView(R.layout.activity_main);
        width = getResources().getDisplayMetrics().widthPixels;
        TextView tv = (TextView) findViewById(R.id.tv);

        tv.setMovementMethod(ScrollingMovementMethod.getInstance());// 设置可滚动
        tv.setMovementMethod(LinkMovementMethod.getInstance());// 设置超链接可以打开网页
        tv.setText(Html.fromHtml(content, imgGetter,null));

    }

    private void startImg(SpannableStringBuilder text, String src, Html.ImageGetter img) {
        Drawable d = null;

        if (img != null) {
            d = img.getDrawable(src);
        }
        if (d == null) {
            d = getResources().getDrawable(R.mipmap.ic_launcher);
            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        }
        int len = text.length();
        text.append("\uFFFC");

        text.setSpan(new ImageSpan(d, src), len, text.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    // 这里面的resource就是fromhtml函数的第一个参数里面的含有的url
    Html.ImageGetter imgGetter = new Html.ImageGetter() {
        public Drawable getDrawable(String source) {
            Drawable drawable = null;
            URL url;
            try {
                url = new URL(source);
                drawable = Drawable.createFromStream(url.openStream(), ""); // 获取网路图片
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            // 限制图片在TextView中的边界大小
            drawable.setBounds(
                    0,
                    0,
                    width,
                    width * drawable.getIntrinsicHeight()
                            / drawable.getIntrinsicWidth());
            return drawable;
        }
    };


    // 严苛模式
    public static void struct() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork() // or
                // .detectAll()
                // for
                // all
                // detectable
                // problems
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects() // 探测SQLite数据库操作
                .penaltyLog() // 打印logcat
                .penaltyDeath().build());
    }
}
