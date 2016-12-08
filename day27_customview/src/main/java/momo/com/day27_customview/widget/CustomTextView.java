package momo.com.day27_customview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Random;

import momo.com.day27_customview.R;

/**
 * Created by Administrator on 2016/11/16 0016.
 */
public class CustomTextView extends View implements View.OnClickListener{

    //定义的字符串
    String text;
    //定义的文本颜色
    int color;
    //定义的文本大小
    int size;


    Paint paint;

    public CustomTextView(Context context) {
        super(context);
        color = Color.BLACK;
        size = 50;
        init();
    }

    /*xml布局中写了属性，可以在这个构造方法中获取
   *
   * 参数二：AttributeSet,xml的属性值
   * */
    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d("Tag","2个参数");

        //1.获取属性数组
        /*
        * 参数一：attrs，xml中的属性值
        *
        * 参数二：自定义的属性名
        *
        * 返回：属性数组值
        * */
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.myTextView);
        /*
        * 获取属性名在数组中的位置：R.styleable.styleableName_attrName
        * */
        //获取文本
        text = arr.getString(R.styleable.myTextView_myText);
        //获取文本颜色
        color = arr.getColor(R.styleable.myTextView_myTextColor,Color.BLACK);
        //获取文本大小
        size =(int)arr.getDimensionPixelSize(R.styleable.myTextView_myTextSize,50);

        //释放属性数组
        arr.recycle();

        init();
    }


    private void init(){
        //初始化画笔
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(size);
        paint.setColor(color);

        //设置点击监听
        this.setOnClickListener(this);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasureWH(widthMeasureSpec,1),getMeasureWH(heightMeasureSpec,2));


    }


    private int getMeasureWH(int wh,int type){
        //获取模式
        int mode = MeasureSpec.getMode(wh);
        //获取推荐大小
        int size = MeasureSpec.getSize(wh);
        //根据模式，返回计算的大小
        switch (mode){
            case MeasureSpec.EXACTLY:{

                return size;
            }
            //设置是wrap_content,根据文本内容，计算控件的大小
            case MeasureSpec.AT_MOST:{
                if(type ==1){
                    //计算宽
                    //文本的长度
                    return (int)paint.measureText(text)+getPaddingLeft()+getPaddingRight();
                }else if(type ==2){
                    //计算高
                    return (int) (paint.descent()-paint.ascent()+getPaddingTop()+getPaddingBottom());
                }

            }
            case MeasureSpec.UNSPECIFIED:{
                return size;
            }



        }

        return size;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText(text,getPaddingLeft(),getPaddingTop()-paint.ascent(),paint);

    }





    //点击控件，生成随机的四位数
    @Override
    public void onClick(View v) {

        Log.d("Tag","onClick");
            //生成四位随机数
        Random ran = new Random();
        //生成四位随机数1000~9999
        int value = ran.nextInt(9000) + 1000;
        //设置text
        text =value+"";

        //重绘界面
        //会调整大小,并重绘界面
        requestLayout();
        //不会调整大小，只重绘界面
        invalidate();

    }
}
