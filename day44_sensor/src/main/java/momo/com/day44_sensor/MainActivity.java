package momo.com.day44_sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView iv_up,iv_down;

    SensorManager manager;

    SoundPool soundPool;

    Vibrator vibrator;

    long lastTime= 0;
    int playId;

    SensorEventListener listner=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            //获取手机在XYZ轴上加速度的变化
            float[] values = event.values;
            float valueX = values[0];
            float valueY = values[1];
            float valueZ = values[2];
            //测试出来，17是相对较好的数值 （z轴方向不需要实现摇一摇，可去掉）
            if(valueX>17||valueY>17||valueZ>17){
                long currentTimeMillis = System.currentTimeMillis();
                //摇一摇一秒内，只执行一次。
                if(currentTimeMillis-lastTime<1000){
                    return;
                }
                lastTime = currentTimeMillis;
                //开启动画
                startAnimation();
                //开启声音
                startSound();

                //开启振动(记得添加振动权限)
                //参数1：振动节奏（振动/停止/振动/停止。。。）
                //参数2：重复次数，-1表示不重复
                vibrator.vibrate(new long[]{200,100,200,100,200,100},-1);

            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private void startSound(){
        soundPool.play(playId,1,1,1,0,1);
    }

    private void startAnimation(){

        TranslateAnimation upUp = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF,0,TranslateAnimation.RELATIVE_TO_SELF,0,
                TranslateAnimation.RELATIVE_TO_SELF,0,TranslateAnimation.RELATIVE_TO_SELF,-1);
        upUp.setDuration(1000);
        TranslateAnimation upDown = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF,0,TranslateAnimation.RELATIVE_TO_SELF,0,
                TranslateAnimation.RELATIVE_TO_SELF,0,TranslateAnimation.RELATIVE_TO_SELF,1);
        upDown.setDuration(1000);
        upDown.setStartOffset(1000);

        //组合动画
        AnimationSet upSet = new AnimationSet(false);
        //将上面的动画添加到组合动画
        upSet.addAnimation(upUp);
        upSet.addAnimation(upDown);
        //控件开启动画
        iv_up.startAnimation(upSet);


        TranslateAnimation downDown = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF,0,TranslateAnimation.RELATIVE_TO_SELF,0,
                TranslateAnimation.RELATIVE_TO_SELF,0,TranslateAnimation.RELATIVE_TO_SELF,1);
        downDown.setDuration(1000);
        TranslateAnimation downUp = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF,0,TranslateAnimation.RELATIVE_TO_SELF,0,
                TranslateAnimation.RELATIVE_TO_SELF,0,TranslateAnimation.RELATIVE_TO_SELF,-1);
        downUp.setDuration(1000);
        downUp.setStartOffset(1000);

        AnimationSet downSet = new AnimationSet(false);
        downSet.addAnimation(downDown);
        downSet.addAnimation(downUp);

        iv_down.startAnimation(downSet);





    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //找到控件
        iv_up = (ImageView) findViewById(R.id.img_up);
        iv_down = (ImageView) findViewById(R.id.img_down);
        //获取一个振动服务
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //初始化传感器
        initSensor();
        //初始化声音池
        initSoundPool();
    }

    @Override
    protected void onStop() {
        super.onStop();
        manager.unregisterListener(listner);
        //释放声音池
        soundPool.release();
    }

    private  void initSoundPool(){

        if(Build.VERSION.SDK_INT>20) {
            AudioAttributes audioAttribues= new AudioAttributes.Builder()
                    .setLegacyStreamType(AudioManager.STREAM_MUSIC).build();
            //此SoundPool创建方式，sdk至少21
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(5)
                    .setAudioAttributes(audioAttribues)
                    .build();
        }else {
            //参数1：声音池最大并发流数目
            soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }

        playId = soundPool.load(this,R.raw.awe, 1);

    }

    private void initSensor() {
        //1.获取一个传感器管理器
        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //2.获取一个加速度传感器
        Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //3.注册加速度传感器
//        第三个参数表示传感器灵敏度，四种取值，从上往下灵敏度依次降低
//        SENSOR_DELAY_FASTEST = 0;---延时:0ms
//        SENSOR_DELAY_GAME = 1;---延时:20ms
//        SENSOR_DELAY_UI = 2;----延时:60ms
//        SENSOR_DELAY_NORMAL = 3;---延时:200ms
        manager.registerListener(listner,sensor,SensorManager.SENSOR_DELAY_GAME);
    }
}
