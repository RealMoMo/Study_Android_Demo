package momo.com.day40_myjoke_fromhttp;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * <div class="article block untagged mb15" id='qiushi_tag_118093462'>

 <div class="author clearfix">
 <a href="/users/30992791/" target="_blank" rel="nofollow">
 <img src="http://pic.qiushibaike.com/system/avtnew/3099/30992791/medium/2016091621502178.JPEG" alt="樱小暖"/>
 </a>
 <a href="/users/30992791/" target="_blank" title="樱小暖">
 <h2>樱小暖</h2>
 </a>
 <div class="articleGender womenIcon">98</div>
 </div>



 <a href="/article/118093462" target="_blank" class='contentHerf' >
 <div class="content">



 <span>阴冷的12月，一位同事倒下，我猛扑上去，拼命摇醒：“同志，你醒醒啊！”他虚弱地微睁双目，吃力地挤出：“这、这是我的周工作计划、月工作计划、季度和年度工作计划、工作记录本、专项检查总结、明年工作思路、计划、整改措施、年终总结……请、请一定代我转交组织！”说完又陷入了昏迷。我含泪晃着他的身子：“同志，你醒醒，醒醒，组织还有要求，还...还…还要交电子版的…”</span>


 </div>
 </a>




 <div class="thumb">

 <a href="/article/118093462" target="_blank">
 <img src="http://pic.qiushibaike.com/system/pictures/11809/118093462/medium/app118093462.jpg" alt="同志，你醒醒啊！" />
 </a>

 </div>
 */
public class JokeBean {

    //内容详情
    public String content;

    //图片地址，可能没有
    public String img;

    //点击查看数据的链接地址
    public String contentHerf;


    public JokeBean() {
    }

    public JokeBean(Element element) {
        //内容
        //得到内容，返回的是元素集合，然后再取第一个数据
        Element tmpContent = element.getElementsByClass("content").first();
        //取出文本
        this.content = tmpContent.text();

        //图片
        //图片地址,有两种可能，有或没有
        Elements tmpThumb = element.getElementsByClass("thumb");
        //如果imgs为null，或者内容长度为0说明没有图片，否则有图片，取第一个即可
        if(tmpThumb !=null && tmpThumb.size()>0){
            //有图片，解析出图片地址，取出第一个元素
            Element tmpImg = tmpThumb.first();
            //得到img标签的选择器,src的属性值即为图片地址
            this.img = tmpImg.select("img").attr("src");

        }
        //链接地址
        //得到class='contentHerf'，取出第一个元素，得到a的选择器，取出href属性
        Element tmpHerf = element.getElementsByClass("contentHerf").first();
        this.contentHerf = tmpHerf.select("a").attr("href");

    }

    @Override
    public String toString() {
        return content ;
    }
}
