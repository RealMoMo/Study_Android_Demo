package momo.com.day27_customview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/11/15 0015.
 */
public class MyTextView extends View {

    //画笔
    Paint mPaint;
    /*圆的半径*/
    int cr = 100;
    /*x的中心点 */
    int pX = 0;
    /*y的中心点*/
    int pY = 0;

    public MyTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        //创建画笔
        mPaint = new Paint();
        //设置抗锯齿
        mPaint.setAntiAlias(true);
        //设置画笔的颜色
        mPaint.setColor(Color.parseColor("#FF0926CE"));
        //设置描边宽度
        mPaint.setStrokeWidth(5);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /*
  * 测量控件，得到控件的测量大小
  *
  * */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //测量控件的大小
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //设置控件的测量大小
        //根据设置值，获取模式，设置大小
        setMeasuredDimension(getMeasureWH(widthMeasureSpec, 1), getMeasureWH(heightMeasureSpec, 2));

        //经过上面计算后，得到测量大小
        //计算圆的中心及半径
        pX = getMeasuredWidth() / 2;
        pY = getMeasuredHeight() / 2;

        //半径为小
        cr = pX > pY ? pY : pX - 5;
    }

    /*
    * 测量宽高
    *
    * 参数一：即widthMeasureSpec或heightMeasureSpec
    *
    * 参数二：表示测量是的宽还是高        1:宽，2：高
    *
    * */
    private int getMeasureWH(int wh, int type) {

        //1.获取设置模式，推荐大小
        int mode = MeasureSpec.getMode(wh);
        //推荐大小
        int size = MeasureSpec.getSize(wh);
        //2.根据模式，计算大小
        switch (mode) {
            case MeasureSpec.EXACTLY: {
                //精确值,在xml中，设置控件大小为固定值，或match_parent
                //直接返回推荐大小
                return size;
            }
            case MeasureSpec.AT_MOST: {
                //在范围内,在xml中，设置控件大小为wrap_content
                //根据内容，计算大小，再返回
                if (type == 1) {
                    //计算宽
                } else {
                    //计算高
                }
                return 200;
            }
            case MeasureSpec.UNSPECIFIED: {
                //想要多大，就有多大，一般为ListView或ScrollView的子控件的大小， 不常用
                return size;
            }
        }
        return size;

    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置画笔的颜色
        mPaint.setColor(Color.parseColor("#FF0926CE"));
        //先画圆，作背景
        canvas.drawCircle(pX, pY, cr, mPaint);

        //写文字
        //设置文字大小、画笔颜色
        mPaint.setTextSize(50);
        mPaint.setColor(Color.YELLOW);
        //计算文字的宽度
        int textW = (int) mPaint.measureText("M");
        //计算文字的高度
        int textH = (int) (mPaint.descent()-mPaint.ascent());
        //文字画在圆的中心，坐标自己好好算下
        canvas.drawText("M",pX-textW/2,pY-textH/2-mPaint.ascent(),mPaint);
    }
}
