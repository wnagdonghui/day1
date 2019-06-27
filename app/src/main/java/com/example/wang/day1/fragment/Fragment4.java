package com.example.wang.day1.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.wang.day1.R;
import com.example.wang.day1.adapter.RecyclerViewAdapter;
import com.example.wang.day1.bean.ResultsBean;
import com.example.wang.day1.util.Utile;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment4 extends Fragment {


    private View view;
    private RecyclerView f4_rec;
    private RecyclerViewAdapter recyclerViewAdapter;

    public Fragment4() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment4, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        List<ResultsBean> resultsBeans = Utile.queryAll();
        if (resultsBeans!=null){
            recyclerViewAdapter.initDataItem(resultsBeans);
        }

    }

    private void initView(View view) {
        f4_rec = (RecyclerView) view.findViewById(R.id.f4_rec);
        f4_rec.setLayoutManager(new LinearLayoutManager(getContext()));
        f4_rec.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));

        recyclerViewAdapter = new RecyclerViewAdapter(this);
        f4_rec.setAdapter(recyclerViewAdapter);

        registerForContextMenu(f4_rec);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(1,1,1,"删除");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        recyclerViewAdapter.deleteDao();
       recyclerViewAdapter.deleteItem();
        return super.onContextItemSelected(item);
    }

    public RecyclerView getRecyclerView(){
        return f4_rec;
    }
}
