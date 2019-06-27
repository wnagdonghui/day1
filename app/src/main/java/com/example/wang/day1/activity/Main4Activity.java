package com.example.wang.day1.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.example.wang.day1.R;
import com.example.wang.day1.adapter.ViewPagerFragmentAdapter;
import com.example.wang.day1.fragment.Fragment1;
import com.example.wang.day1.fragment.Fragment2;
import com.example.wang.day1.fragment.Fragment3;
import com.example.wang.day1.fragment.Fragment4;

import java.util.ArrayList;

public class Main4Activity extends AppCompatActivity {

    private Toolbar mi4_tool;
    private ViewPager mi4_vp;
    private TabLayout mi4_tab;
    private NavigationView mi4_nav;
    private DrawerLayout mi4_dra;
    private Fragment4 fragment4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        initView();
    }

    private void initView() {
        mi4_tool = (Toolbar) findViewById(R.id.mi4_tool);
        mi4_vp = (ViewPager) findViewById(R.id.mi4_vp);
        mi4_tab = (TabLayout) findViewById(R.id.mi4_tab);
        mi4_nav = (NavigationView) findViewById(R.id.mi4_nav);
        mi4_dra = (DrawerLayout) findViewById(R.id.mi4_dra);

        mi4_tool.setTitle("Toolbar");
        setSupportActionBar(mi4_tool);

        mi4_tool.setNavigationIcon(R.mipmap.ic_launcher);
        mi4_tool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ArrayList<Fragment> fragments = new ArrayList<>();
        Fragment1 fragment1 = new Fragment1();
        Fragment2 fragment2 = new Fragment2();
        Fragment3 fragment3 = new Fragment3();
        fragment4 = new Fragment4();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);

        ViewPagerFragmentAdapter viewPagerFragmentAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(), fragments);
        mi4_vp.setAdapter(viewPagerFragmentAdapter);

        mi4_tab.setupWithViewPager(mi4_vp);

        mi4_tab.getTabAt(0).setCustomView(R.layout.tablayout_title);
        mi4_tab.getTabAt(1).setCustomView(R.layout.tablayout_title);
        mi4_tab.getTabAt(2).setCustomView(R.layout.tablayout_title);
        mi4_tab.getTabAt(3).setCustomView(R.layout.tablayout_title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1,1,1,"瀑布");
        menu.add(1,2,1,"列表");
        menu.add(1,3,1,"网格");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        RecyclerView recyclerView = fragment4.getRecyclerView();
        if (recyclerView!=null){
        switch (item.getItemId()){
            case 1:
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                break;
            case 2:
                recyclerView.setLayoutManager(new LinearLayoutManager(Main4Activity.this));
                break;
            case 3:
                recyclerView.setLayoutManager(new GridLayoutManager(Main4Activity.this,2));
                break;
        }
        }else {
            Toast.makeText(this, "请在fragment4使用此功能", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
