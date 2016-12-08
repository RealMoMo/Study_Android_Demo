package momo.com.day27_customview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义布局
 */
public class MyLineFreeLayout extends ViewGroup {


    //每一行子控件的集合的集合
    List<List<View>> lines;
    //每一行的高度集合
    List<Integer> heights;


    public MyLineFreeLayout(Context context) {
        super(context);
        init();
    }

    public MyLineFreeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        //初始化数据
        lines = new ArrayList<>();
        heights = new ArrayList<>();

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        //测量所有子控件的大小
        //获取子控件数量
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            //测量每个子控件
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);

        }
    }

    /*
    * 返回子控件的layoutParams属性
    * xml中，layout_xxx
    * */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        //获取Margin属性
        return new MarginLayoutParams(getContext(),attrs);
    }



    /*布局子控件时，调用 */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //开始算每行的布局个数，每行布局高度。
        //=======================================================
        Log.d("Tag","onLayout");
        //清空控件集合(why?)
        lines.clear();
        heights.clear();

        //分析一共有多少行，每一行子控件的集合
        //每一行的总宽度
        int childWidth = 0;
        //每一行的高度
        int childHeight = 0;
        //计算当前layout控件的宽度  完整写法this.getMeasuredWidth();
        int width = getMeasuredWidth();
        //子控件的个数
        int childCount = getChildCount();
        //循环获取每个子控件，得到每行的宽度
        //当前行的子集合
        ArrayList<View> childLine = new ArrayList<>();
        //循环获取每个子控件，得到每行的宽度
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            //获取子控件的layoutParams属性
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();

            //当前子控件的宽度
            int tmpW = child.getMeasuredWidth()+params.leftMargin+params.rightMargin;
            int tmpH = child.getMeasuredHeight()+params.topMargin+params.bottomMargin;
            //判断当前宽度总长是否换行
            //要换行
            if (tmpW + childWidth > width) {
                //把当前高度保存到集合
                heights.add(childHeight);
                //先把子集合添加到总集合中
                lines.add(childLine);
                //换行,子集合重新赋值
                childLine = new ArrayList<>();
                //试试清空集合
                //childLine.clear();

                //清零宽度累加
                childWidth = 0;
                //清零高度
                childHeight = 0;


            }
            //不换行，把当前子控件，添加到子集合中
            childLine.add(child);
            //每行宽度累加
            childWidth += tmpW;
            //保存当前最高的值
            childHeight = Math.max(childHeight, tmpH);


        }
        //把最后一行的子控件集合及高度添加到集合中
        lines.add(childLine);
        heights.add(childHeight);
//========================================================
        //开始布局，总共的行数：
        int lineCount = lines.size();

        //布局高度累加
        int sumTop=0;

        //开始一行一行的布局
        for(int i =0;i<lineCount;i++){
            //取出当前行的所有子控件
            List<View> currentLine = lines.get(i);
            //当前行的子控件 的总个数
            int currentCount = currentLine.size();
            //左边距的累加值
            int sumLeft =0;

            //循环
            for(int j=0; j<currentCount;j++){

                //取出每个子控件
                View tmpView = currentLine.get(j);
                //获取LayoutParams属性
                MarginLayoutParams params = (MarginLayoutParams) tmpView.getLayoutParams();
                //计算每个控件的坐标值
                int left = sumLeft+params.leftMargin;
                int top = sumTop+params.topMargin;
                int right =left+tmpView.getMeasuredWidth();
                int bottom =top+tmpView.getMeasuredHeight();

                //布局
                tmpView.layout(left,top,right,bottom);
                //当前控件布局完，准备下一个控件的布局
                sumLeft+=tmpView.getMeasuredWidth()+params.leftMargin+params.rightMargin;

            }

            sumTop+=heights.get(i);


        }
    }


}
