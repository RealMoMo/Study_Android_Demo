package momo.com.day40_myjoke_fromhttp;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by Administrator on 2016/12/2 0002.
 */
public class DetailBean {

    public String content;

    public String thumb;


    public DetailBean( Document document) {
        this.content = document.getElementsByClass("content").first().text();
        Elements tmp = document.getElementsByClass("thumb");
        if(tmp!=null&&tmp.size()>0){
            this.thumb = tmp.first().select("img").attr("src");

        }
    }
}
