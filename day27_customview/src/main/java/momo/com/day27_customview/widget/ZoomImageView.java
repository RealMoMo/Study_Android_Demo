package momo.com.day27_customview.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * 按矩阵移动缩放图片
 */
public class ZoomImageView extends ImageView{
    //Point -->int
    //PointF-->float
    //按下时，保存的点
    PointF startPF;
    //当前图片的矩阵
    Matrix currentMatrix;
    //移动后的矩阵
    Matrix matrix;





    public ZoomImageView(Context context) {
        super(context);
        init();

    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init(){
        //设置该ZoomImageView组件拉伸模式为矩阵
        setScaleType(ScaleType.MATRIX);

        startPF = new PointF();
        currentMatrix = new Matrix();
        matrix = new Matrix();
    }

    //是否多点在触摸
    boolean isMore;
    /*多点按下时的两点距离*/
    float startDistance;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()&MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_POINTER_DOWN:{
                isMore=true;
                //记录当前点的坐标
                startPF.set(event.getX(),event.getY());
                //记录当前的矩阵
                currentMatrix.set(getImageMatrix());
                //计算当前的距离
                startDistance = getDistence(event);

            }break;
            case MotionEvent.ACTION_POINTER_UP:{
                isMore=false;
            }break;
            case MotionEvent.ACTION_DOWN:{

                //单点按下时：
                //记录当前点的坐标
                startPF.set(event.getX(),event.getY());
                //记录当前的矩阵
                currentMatrix.set(getImageMatrix());

            }break;
            case MotionEvent.ACTION_UP:{

            }break;
            case MotionEvent.ACTION_MOVE:{

                //如果是多点，缩放操作
                //如果是单点,拖动操作
                if(isMore){
                    //计算 sx x方向的缩放比
                    //计算 sy y方向的缩放比
                    float scale = getDistence(event)/startDistance;
                    //设置移动矩阵，以按下时矩阵为基准
                    matrix.set(currentMatrix);
                    //设置矩阵缩放
                    matrix.postScale(scale,scale,startPF.x,startPF.y);
                    //把matrix设置为图片的矩阵
                    setImageMatrix(matrix);

                }else {
                    //移动时，计算x,y的移动距离
                    float dx = event.getX() - startPF.x;
                    float dy = event.getY() - startPF.y;
                    //设置移动矩阵，以按下时矩阵为基准
                    matrix.set(currentMatrix);
                    //设置移偏移量
                    matrix.postTranslate(dx, dy);
                    //把matrix设置为图片的矩阵
                    setImageMatrix(matrix);
                }

            }break;


        }

        return true;
    }

    /**
     * 计算两点的距离
     *
     * @param event
     * @return
     */
    private float getDistence(MotionEvent event){
        float x1 =event.getX(0);
        float y1 =event.getY(0);
        float x2 =event.getX(1);
        float y2 =event.getY(1);

        return (float)Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));

    }
}
