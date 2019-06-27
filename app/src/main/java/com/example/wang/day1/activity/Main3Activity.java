package com.example.wang.day1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.example.wang.day1.R;
import com.example.wang.day1.adapter.ViewPagerAdapter;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {

    private ViewPager mi3_vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        initView();
    }

    private void initView() {
        mi3_vp = (ViewPager) findViewById(R.id.mi3_vp);

        View view = View.inflate(this, R.layout.viewpager_view1, null);
        View view1 = View.inflate(this, R.layout.viewpager_view1, null);
        View view2 = View.inflate(this, R.layout.viewpager_view2, null);
        ArrayList<View> views = new ArrayList<>();
        views.add(view);
        views.add(view1);
        views.add(view2);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(views);
        mi3_vp.setAdapter(viewPagerAdapter);

        view2.findViewById(R.id.vp_bu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main3Activity.this,Main4Activity.class);
                startActivity(intent);
            }
        });
    }
}
