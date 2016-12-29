package momo.com.day59_customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2016/12/29 0029.
 */

public class MediaView extends View {

    private Paint paint;
    private Path path;

    private int changY = 5;

    private Random random;

    private List<MediaInfo> list ;
    //线条数
    private  int count = 10;

    public MediaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        paint = new Paint();
        //抗锯齿
        paint.setAntiAlias(true);
        //抗抖动
        paint.setDither(true);
        paint.setColor(Color.BLUE);
        //给path描边
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);
        //设置paint画虚线
        DashPathEffect effect = new DashPathEffect(new float[]{16,2,16,2},0);
        paint.setPathEffect(effect);

        path = new Path();

        random = new Random();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int height = getHeight();

        if(list==null){
            list = new ArrayList<>();
            MediaInfo info;
            for (int i = 0; i < count; i++) {
                info = new MediaInfo();
                info.setStartX((int) (25+i*getWidth()/count+i*paint.getStrokeWidth()));
                info.setCurrentY(height);
                info.setTargetY(random.nextInt(height));
                list.add(info);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            updateView(canvas, height,list.get(i));
        }
        //递归调用
        invalidate();
    }

    private void updateView(Canvas canvas, int height,MediaInfo info) {

        //如果两者之差的绝对值小于每次改变的高度，就代表两点重合
        if(Math.abs(info.getCurrentY()-info.getTargetY())<changY){
            //重新设置目标值
            info.setTargetY(random.nextInt(height));
        }else{

            if(info.getCurrentY()<info.getTargetY()){
                info.setCurrentY(info.getCurrentY()+changY);
            }else{
                info.setCurrentY(info.getCurrentY()-changY);

            }
        }

        int x = info.getStartX();
        path.moveTo(x,height);
        path.lineTo(x,info.getCurrentY());

        //颜色线性渐变
        Shader shader = new LinearGradient(x,height,x,info.getCurrentY(), Color.BLUE,Color.YELLOW, Shader.TileMode.CLAMP);
        paint.setShader(shader);

        canvas.drawPath(path,paint);
        //path清空原来数据
        path.reset();
    }
}
