package com.example.day16_objectanimator_demo;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends Activity {

	ImageButton ib;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ib = (ImageButton) findViewById(R.id.iv);
	}

	// 点击，实现ObjectAnimator动画
	public void start(View view) {

		// ib.setRotation(0.8f);
		// 2.定义ObjectAnimator动画
		/*
		 * 参数一：执行动画的对象
		 * 
		 * 参数二：动画的属性名,String,对象一定要提供set、get的方法
		 * 
		 * 参数三：动画的属性值：
		 */
		ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ib, "Rotation",
				0, 360);
		// 设置持续时间
		objectAnimator.setDuration(2000);
		// 设置持续时间
		objectAnimator.setRepeatCount(10);
		// 设置匀速
		objectAnimator.setInterpolator(new LinearInterpolator());
		// 3.启动动画
		objectAnimator.start();

	}

	// 点击，实现OjbectAnimator平移动画
	public void translateObj(View view) {		
		// ib.setTranslationY();
		
		// 2.定义ObjectAnimator
		ObjectAnimator objTrans = ObjectAnimator.ofFloat(ib, "TranslationY", 0,
				300);
		// 设置持续时间
		objTrans.setDuration(3000);
		// 3.播放动画
		objTrans.start();

	}
	
	// 点击，实现ObjectAnimator缩放
	public void scaleObj(View view) {

		// ib.setScaleX();
		// 定义动画
		ObjectAnimator objScale = ObjectAnimator.ofFloat(ib, "ScaleX", 1, 0.5f);
		// 持续时间
		objScale.setDuration(3000);
		objScale.start();
		
		// 监听ObjectAnimator值的变化
		objScale.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				Log.d("Tag", "onAnimationStart");

			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				Log.d("Tag", "onAnimationRepeat");

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				Log.d("Tag", "onAnimationEnd");

			}

			@Override
			public void onAnimationCancel(Animator animation) {
				Log.d("Tag", "onAnimationCancel");

			}
		});

		// ValueAnimator专用监听,用来监听属性值改变
		objScale.addUpdateListener(new AnimatorUpdateListener() {

			/*
			 * 当属性值发生改变时调用
			 */
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				// 获取当前动画执行的百分比
				float fraction = animation.getAnimatedFraction();

				Log.d("Tag", "onAnimationUpdate:" + fraction);
				// x方向的压缩总值为0.5f
				// 根据百分比，算出当前y方向的压缩比
				float value = 1 - fraction * 0.5f;
				// 设置y方向的压缩比
				ib.setScaleY(value);

			}
		});
	}

	// 点击，实现AnimatorSet动画
	public void setObj(View view) {
		// 获取屏幕的宽高
		DisplayMetrics metri = getResources().getDisplayMetrics();
		// 屏宽，以像素为单位
		int width = metri.widthPixels;
		// 屏高，以像素为单位
		int height = metri.heightPixels;
		// 2.定义x,y方向的属性动画
		ObjectAnimator objTranY = ObjectAnimator.ofFloat(ib, "TranslationY", 0,
				height - 2 * ib.getHeight());
		ObjectAnimator objTranX = ObjectAnimator.ofFloat(ib, "TranslationX", 0,
				width - ib.getWidth());
		// 3.定义AnimatorSet
		AnimatorSet set = new AnimatorSet();
		
		set.setDuration(3000);
		// 4.通过AnimatorSet.play添加并设置播放模式
		set.playTogether(objTranX, objTranY);
		// 5.播放
		set.start();

	}

	// 点击，启动第二个Activity
	public void startSecondActivity(View view) {
		// 启动Activity,设置过场动画
		Intent intent = new Intent(this, SecondActivity.class);
		startActivity(intent);
		// 设置过场动画
		overridePendingTransition(R.anim.enter_activity, R.anim.exit_activity);

	}
	


}
