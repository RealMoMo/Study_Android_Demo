package momo.com.day27_customview.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义瀑布流布局控件
 */
public class PuBuLayout extends ScrollView {


    public PuBuLayout(Context context) {
        super(context);
        init();
    }

    public PuBuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    //三个垂直方向的线性布局的集合，用来存在ImageView控件
    List<LinearLayout> childLayout;

    //初始化
    private void init() {
        //初始化集合
        childLayout = new ArrayList<>(3);
        //水平线性布局(放3个垂直线性布局)
        LinearLayout hLayout = new LinearLayout(getContext());
        //设置方向为水平
        hLayout.setOrientation(LinearLayout.HORIZONTAL);

        //三个垂直的线性布局
        //垂直线性布局的布局属性
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置布局的权重
        params.weight = 1;

        //此时，childLayout.size为0.因为还没有添加.所以用，i<3.
        for (int i = 0; i < 3; i++) {
            LinearLayout tmpLayout = new LinearLayout(getContext());
            //方向为垂直方向
            tmpLayout.setOrientation(LinearLayout.VERTICAL);
            //添加到水平线性布局中
            hLayout.addView(tmpLayout,params);
            //再把子控件添加到集合中
            childLayout.add(tmpLayout);
        }
        //把水平线性布局添加到ScrollView中
        this.addView(hLayout);
    }
    //统计当前总共有多少个ImageView
    int countImg;

    //向外提供一个方法，用来布局控件的
    public void addImage(Bitmap bitmap){
        //每添加一张图片，创建一个新的ImageView
        ImageView iv = new ImageView(getContext());
        //设置ImageView的拉伸模式
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //设置margin属性以及设置宽为match_parent,高:wrap_content
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(5,5,5,5);
        iv.setImageBitmap(bitmap);
        //设置Imageview保持宽高比
        iv.setAdjustViewBounds(true);

        //并添加到指定的垂直线性布局中
        //计算出当前要放的布局的索引
        int index = countImg++%childLayout.size();

        //取出对应索引的LinearLalyout
        //添加子控件
        childLayout.get(index).addView(iv,params);


    }



}
