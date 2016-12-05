package com.example.day16_animation_demo;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class MainActivity extends Activity {

	ImageView iv_number, iv_rocket;
	/**
	 * 动画持续时间
	 */
	int time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 1.找到设置动画的控件
		iv_number = (ImageView) findViewById(R.id.iv_number);
		iv_rocket = (ImageView) findViewById(R.id.iv_rocket);
		
		
	}

	// 点击，实现倒计时的动画
	public void send(View view) {

		// 2.找到对应属性（哪个设置了动画的）
		// 3.强转成AnimationDrawable
		AnimationDrawable number_anim = (AnimationDrawable) iv_number
				.getBackground();
		// 运行一次,放在start前后都可以
		// animation.setOneShot(true);

		// 计算动画持续时间:先获取总帧数，获取每一帧的持续时间
		int frameCount = number_anim.getNumberOfFrames();
		// 循环遍历，获取每一帧的时间
		for (int i = 0; i < frameCount; i++) {
			time += number_anim.getDuration(i);
		}
		// 动画执行完成后，iv_number消失，启动一个线程，让该线程sleep
		// time时间，sleep后，就执行iv.setVisiable(View.GONE)
		new Thread() {

			public void run() {

				try {
					Thread.sleep(time);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// 设置控件不可见，在主线程中运行
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// 设置iv_number不可见
						iv_number.setVisibility(View.GONE);
						// 启动火箭点火的动画
						AnimationDrawable rocket_anim = (AnimationDrawable) iv_rocket
								.getBackground();
						// 设置控件不可见，在主线程中运行
						rocket_anim.start();
					}
				});

			};

		}.start();
		// 4.启动倒计时动画
		number_anim.start();

	}
	// 点击，iv_rocket执行透明动画
	public void alphaAnim(View view) {
		// 2.将rocket_alpha动画，装载进来
		Animation rocket_alpha = AnimationUtils.loadAnimation(this,
				R.anim.rocket_alpha);
		// 3.通过View.startAnimation
		iv_rocket.startAnimation(rocket_alpha);

	}

	// 点击，代码实现iv_rocket透明动画
	public void alphaAnim_java(View view) {
		// 1.定义AlphaAnimation
		AlphaAnimation animation = new AlphaAnimation(1f, 0f);
		// 设置持续时间
		animation.setDuration(1000);
		// 设置循环次数
		animation.setRepeatCount(AlphaAnimation.INFINITE);
		// 设置循环模式
		animation.setRepeatMode(AlphaAnimation.REVERSE);
		// 2.通过View.startAnimation
		iv_rocket.startAnimation(animation);
	}
	
	// 点击， xml实现iv_rocket缩放动画
	public void scaleAnim(View view) {
		// 2.装载动画
		Animation rocket_scale = AnimationUtils.loadAnimation(this,
				R.anim.rocket_scale);
		// 3.播放动画
		iv_rocket.startAnimation(rocket_scale);

	}
	// 点击，java实现iv_rocket缩放动画
	public void scaleAnim_java(View view) {
		// 2.定义一个ScaleAnimation动画
		ScaleAnimation animation = new ScaleAnimation(1f, 3f, 1f, 3f,
				ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
				ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
		// 持续时间
		animation.setDuration(1000);
		// 设置停止在最后一帧
		animation.setFillAfter(true);
		// 3.播放动画
		iv_rocket.startAnimation(animation);

	}
	
	// 点击，xml实现iv_rocket移动动画
	public void translateAnim(View view) {
		// 2.通过AnimationUtils.loadAnim
		Animation rocket_translate = AnimationUtils.loadAnimation(this,
				R.anim.rocket_translate);
		// 3.播放动画
		iv_rocket.startAnimation(rocket_translate);

	}
	// 点击，java实现iv_rocket移动动画
	public void translateAnim_java(View view) {
		// 2.定义TranslateAnimation
		TranslateAnimation animation = new TranslateAnimation(
				TranslateAnimation.RELATIVE_TO_PARENT, 0,
				TranslateAnimation.RELATIVE_TO_PARENT, 0,
				TranslateAnimation.RELATIVE_TO_PARENT, 0,
				TranslateAnimation.RELATIVE_TO_PARENT, -1f);
		animation.setDuration(5000);
		// 停在最后
		animation.setFillAfter(true);
		// 设置插值器
		animation.setInterpolator(new LinearInterpolator());
		// 3.播放动画
		iv_rocket.startAnimation(animation);

	}

	// 点击，xml实现iv_rocket的旋转
	public void rotateAnim(View view) {
		// 2.AnimationUtils.loadAnim..
		Animation rocket_rotate = AnimationUtils.loadAnimation(this,
				R.anim.rocket_rotate);
		// 3.播放动画
		iv_rocket.startAnimation(rocket_rotate);

	}

	// 点击，java实现iv_rocket的旋转
	public void rotateAnim_java(View view) {
		// 2.定义RotateAnimation
		RotateAnimation animation = new RotateAnimation(-45f, 45f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0f);
		animation.setDuration(1000);
		animation.setRepeatCount(Animation.INFINITE);
		animation.setRepeatMode(Animation.REVERSE);
		animation.setInterpolator(new LinearInterpolator());
		// 3.播放动画
		iv_rocket.startAnimation(animation);

	}
	// 点击，iv_rocket播放：平移、缩放动画
	public void setAnim(View view) {
		// 2.AnimationUtils.loadAnimation
		Animation rocket_set = AnimationUtils.loadAnimation(this,
				R.anim.rocket_set);
		// 3.播放动画
		iv_rocket.startAnimation(rocket_set);

	}
	// 点击，iv_rocket实现set动画
	public void setAnim_java(View view) {
		// 2.定义一个AnimationSet
		AnimationSet set = new AnimationSet(true);
		// 3.定义其他动画:平移、旋转、缩放、透明
				// 2.定义TranslateAnimation
		TranslateAnimation trans = new TranslateAnimation(
				TranslateAnimation.RELATIVE_TO_PARENT, 0,
				TranslateAnimation.RELATIVE_TO_PARENT, 0,
				TranslateAnimation.RELATIVE_TO_PARENT, 0,
				TranslateAnimation.RELATIVE_TO_PARENT, -1f);
		// 2.定义ScaleAnimation
		ScaleAnimation scale = new ScaleAnimation(1f, 0.6f, 1f, 0.6f,
				ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
				ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
		// 4.通过set.addAnimtion()
		set.addAnimation(trans);
		set.addAnimation(scale);
		// 插值器
		set.setInterpolator(new AccelerateInterpolator());
		// 持续时间
		set.setDuration(2000);
		// 设置停止在最后
		set.setFillAfter(true);
		// 5.播放动画
		iv_rocket.startAnimation(set);

	}
}
