package momo.com.day27_customview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/11/16 0016.
 */
public class MyBall extends View{

    //画笔
    Paint paint;

    //声明圆的参数
    int pX;
    int pY;
    int radius;


    public MyBall(Context context) {
        super(context);
        init();
    }

    public MyBall(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init(){

        pX=pY=0;
        radius=100;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(pX,pY,radius,paint);
    }

    /**
     * 是否是多点触摸
     */
    boolean isMore = false;

    /*
    *
    * 手指触摸屏幕时，回调
    *
    * 参数：MotionEvent        包装了触摸事件
    *
    * */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //如果是手指移动，计算圆心，重绘界面
        switch (event.getAction()&MotionEvent.ACTION_MASK){
            //多点按下时，触发
            case MotionEvent.ACTION_POINTER_DOWN:{
                isMore=true;
                //计算圆心，半径
                pX = (int) event.getX(0);
                pY = (int) event.getY(0);
                radius = getDistance(event);
                //重绘界面
                invalidate();

            }break;
            //多点手指抬起时触发
            case MotionEvent.ACTION_POINTER_UP:{
                isMore=false;
            }break;
            case  MotionEvent.ACTION_DOWN:{
//                isMore=false;
//                Log.d("Tag","按下 down");
            }break;
            case MotionEvent.ACTION_UP:{
//                isMore=false;
//                Log.d("Tag","抬起 up");
            }break;
            case MotionEvent.ACTION_MOVE:{
//                Log.d("Tag","移动 move");

                //获取手指的位置，计算圆心
                //如果是多点,移动并改变半径
                //如果是单点移动，只移动
                if(isMore){
                    pX = (int) event.getX();
                    pY = (int) event.getY();
                    radius= getDistance(event);
                    invalidate();

                }else{
                    pX = (int) event.getX();
                    pY = (int) event.getY();
                    invalidate();
                }

            }break;
        }


//        Log.d("Tag",""+super.onTouchEvent(event));
        return true;
    }

    private int getDistance(MotionEvent event){
        Log.d("Tag","PointerCount:"+event.getPointerCount());
        float x1 =event.getX(0);
        float y1 =event.getY(0);
        float x2 =event.getX(1);
        float y2 =event.getY(1);

        return (int) Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }


}
