package momo.com.day44_qrwriter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.HashMap;
import java.util.Map;

/**
 * zxing架包下载地址：
 * http://mvnrepository.com/artifact/com.google.zxing/core
 *
 * 使用：直接导到module的libs即可
 *
 */
public class MainActivity extends AppCompatActivity {


    EditText et;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = (EditText) findViewById(R.id.et);
        iv = (ImageView) findViewById(R.id.iv);
    }

    //点击事件，根据输入的内容生成二维码
    public void genQRCode(View view){
        Bitmap qrCodeBitmap = getQRCodeBitmap(et.getText().toString(),400,400);
        Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        //給二维码添加logo
        Bitmap bitmap = addLogo(qrCodeBitmap,logoBitmap);
        iv.setImageBitmap(bitmap);
    }

    private Bitmap addLogo(Bitmap qrCodeBitmap, Bitmap logoBitmap) {
        int width = qrCodeBitmap.getWidth();
        int height = qrCodeBitmap.getHeight();
        Bitmap blankBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(blankBitmap);
        Paint paint = new Paint();
        //先画二维码
        canvas.drawBitmap(qrCodeBitmap, 0, 0, paint);
        //再画logo
        canvas.drawBitmap(logoBitmap, (width - logoBitmap.getWidth()) / 2, (height - logoBitmap.getHeight()) / 2, paint);
        return blankBitmap;
    }

    private Bitmap getQRCodeBitmap(String content, int width, int height) {

        QRCodeWriter writer= new QRCodeWriter();

        Map<EncodeHintType,Object> hints = new HashMap<>();
        //文本的编码格式
        hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");
        //设置二维码的容错级别
//        L   7%
//        M   15%
//        Q   25%
//        H   30%
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

        //对数据进行编码
        try {
            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
           //定义int[]数组，其长度为二维码的像素点
            int[] colors = new int[width*height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    //根据像素点位置，对应生成编码的该位置是否有内容。设置颜色
                    if (bitMatrix.get(j,i)) {
                        colors[i*width+j]= Color.BLACK;
                    } else {
                        colors[i*width+j]= Color.WHITE;
                    }
                }
            }

            //1.生成Bitmap的颜色数组
            //2.数组偏移量
            //3.生成的Bitmap水平方向上的像素点个数
            //4.5.Bitmap宽高
            //6.色彩模式
            return  Bitmap.createBitmap(colors,0,width,width,height, Bitmap.Config.ARGB_8888);
        } catch (WriterException e) {
            e.printStackTrace();
        }


        return null;
    }

}
