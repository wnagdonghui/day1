package com.example.wang.day1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wang.day1.R;

public class MainActivity extends AppCompatActivity {

    private ImageView mi_img;
    /**
     * 倒计时5秒
     */
    private TextView mi_tv;
    private int a=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mi_img = (ImageView) findViewById(R.id.mi_img);
        mi_tv = (TextView) findViewById(R.id.mi_tv);

        RotateAnimation rotateAnimation = new RotateAnimation(0, 360);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1);
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 100, 0, 100);
        AnimationSet animationSet = new AnimationSet(this, null);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.setDuration(3000);
        mi_img.startAnimation(animationSet);

        new CountDownTimer(3000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                a--;
                mi_tv.setText("倒计时"+a+"秒");
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        }.start();
    }
}
