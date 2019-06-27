package com.example.wang.day1.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wang.day1.R;


public class Main2Activity extends AppCompatActivity {

    private ImageView mi2_img;
    /**
     * 倒计时3秒
     */
    private TextView mi2_tv;
    private int a=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
    }

    private void initView() {
        mi2_img = (ImageView) findViewById(R.id.mi2_img);
        mi2_tv = (TextView) findViewById(R.id.mi2_tv);

        ObjectAnimator alpha = ObjectAnimator.ofFloat(mi2_img, "alpha", 0, 1);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mi2_img, "scaleY", 0, 1);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(mi2_img, "translationY", 0, 100);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(mi2_img, "rotation", 0, 360);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(3000);
        animatorSet.play(alpha).with(scaleY).with(translationY).with(rotation);
        animatorSet.start();

        new CountDownTimer(3000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                a--;
                mi2_tv.setText("倒计时"+a+"秒");
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                startActivity(intent);
            }
        }.start();
    }
}
