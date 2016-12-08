package momo.com.day27_customview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 自定义View，实现：进度提示
 * <p/>
 * 设置进度值
 * 获取进度值
 * <p/>
 * 继承自View
 * 重写onDraw
 */
public class MyProgressBar extends View {

    /*当前进度值*/
    int progress = 0;

    //画笔
    Paint mPaint;
    //圆的半径(先给个默认值)
    int cr = 100;
    //圆心
    int pX = 0;
    int pY = 0;

    //扇形
    RectF rf;

    /*在java代码中，动态创建时，调用 */
    public MyProgressBar(Context context) {
        super(context);
        Log.d("Tag", "MyProgressBar 一个参数");
        init();
    }

    /*在xml布局中写控件时，调用 */
    public MyProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d("Tag", "MyProgressBar 二个参数");
        init();
    }

    /*初始化*/
    private void init() {
        //创建画笔
        mPaint = new Paint();
        //设置相关属性
        //设置抗锯齿
        mPaint.setAntiAlias(true);
        //设置画笔的颜色
        mPaint.setColor(Color.parseColor("#FF0926CE"));
        //设置描边宽度
        mPaint.setStrokeWidth(5);

        //界面没出来，控件宽高为0
        //pX=getWidth()/2;

    }

    /*
   * 测量控件，得到控件的测量大小
   *
   * */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //测量控件的大小
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);//布局若用wrap_content不能去使用
        //设置控件的测量大小
        //根据设置值，获取模式，设置大小
        setMeasuredDimension(getMeasureWH(widthMeasureSpec, 1), getMeasureWH(heightMeasureSpec, 2));

        //经过上面计算后，得到测量大小
        //计算圆的中心及半径
        pX = getMeasuredWidth() / 2;
        pY = getMeasuredHeight() / 2;

        //半径为小的一半(减5只是画出来的圆免得贴着控件边边)
        cr = ((pX > pY ? pY : pX) - 5);

        //设置圆与内部扇形的间距
        int gap = 10;
        //初始化扇形(自己好好算算坐标)
        rf = new RectF(pX - cr + gap, pY - cr + gap, pX + cr - gap, pY + cr - gap);


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
        int size = MeasureSpec.getSize(wh);
        //2.根据模式，计算大小
        switch (mode) {
            case MeasureSpec.EXACTLY: {
                //精确值，在xml中，设置控件大小为固定值，或match_parent
                //直接返回推荐大小
                return size;
            }
            case MeasureSpec.AT_MOST: {
                //在范围内，在xml中，设置控件大小为wrap_content
                //根据内容，计算大小，再返回
                if (type == 1) {
                    //计算宽
                } else {
                    //计算高
                }
                //因为本例是画圆，所以返回一样即可
                return 200;

            }
            case MeasureSpec.UNSPECIFIED: {
                //想要多大，就有多大，一般为ListView或ScrollView的子控件大小,不常用
                return size;
            }
        }
        return size;
    }

    /*设置进度*/
    public void setProgress(int progress) {
        this.progress = progress;
        //重绘界面
        invalidate();
        //如果是在子线程中，调用
//        postInvalidate();
    }

    /*绘制界面时，调用
         *
          * 该方法可能会被多次调用,不应该在该方法中创建对象
          *
          * */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("Tag", "onDraw");

        //设置画笔为空心风格
        mPaint.setStyle(Paint.Style.STROKE);

        //画空心圆
        canvas.drawCircle(pX, pY, cr, mPaint);
        //设置画笔为实心
        mPaint.setStyle(Paint.Style.FILL);
        //把当前进度值转换成角度
        int tmp = progress * 360 / 100;
        //绘制扇形
        canvas.drawArc(rf, 0, tmp, true, mPaint);
    }
}
