package com.example.wang.day1.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.wang.day1.R;
import com.example.wang.day1.adapter.RecyclerViewAdapter;
import com.example.wang.day1.bean.ResultsBean;
import com.example.wang.day1.presenter.Presenter;
import com.example.wang.day1.view.IView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment2 extends Fragment implements IView {

    private static final String TAG = "Fragment2";
    private View view;
    private RecyclerView f2_rec;
    private RecyclerViewAdapter recyclerViewAdapter;
    private PopupWindow popupWindow;

    public Fragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment2, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        Presenter presenter = new Presenter(this);
        presenter.presenter();
    }

    private void initView(View view) {
        View view1 = View.inflate(getContext(), R.layout.popupwindow_view, null);
        popupWindow = new PopupWindow(view1,200,200);
        popupWindow.setAnimationStyle(R.style.pop);

        f2_rec = (RecyclerView) view.findViewById(R.id.f2_rec);
        f2_rec.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        f2_rec.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewAdapter = new RecyclerViewAdapter(this, popupWindow);
        f2_rec.setAdapter(recyclerViewAdapter);


        view1.findViewById(R.id.pop_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewAdapter.deleteItem();
                popupWindow.dismiss();
            }
        });
    }

    @Override
    public void getData(final List<ResultsBean> list) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerViewAdapter.initDataItem(list);

            }
        });

    }

    @Override
    public void getError(String error) {
        Log.d(TAG, "getError: "+error);
    }
}
