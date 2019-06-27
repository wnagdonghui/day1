package com.example.wang.day1.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.wang.day1.R;
import com.example.wang.day1.adapter.RecyclerViewAdapter;
import com.example.wang.day1.bean.BannerBean;
import com.example.wang.day1.bean.Bean;
import com.example.wang.day1.bean.ResultsBean;
import com.example.wang.day1.net.ApiService;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment {


    private View view;
    private RecyclerView f1_rec;
    private RecyclerViewAdapter recyclerViewAdapter;
    private int page = 1;
    private SmartRefreshLayout f1_sma;

    public Fragment1() {
        // Required empty public constructor
    }

    private static final String TAG = "Fragment1";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);
        initView(view);
        initData();
        initDataBanner();
        return view;
    }

    private void initDataBanner() {
        new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiService.bannerUrl)
                .build()
                .create(ApiService.class)
                .getDataBanner()
                .enqueue(new Callback<BannerBean>() {
                    @Override
                    public void onResponse(Call<BannerBean> call, Response<BannerBean> response) {
                        List<BannerBean.DataBean> data = response.body().getData();
                        recyclerViewAdapter.initDataBanner(data);
                    }

                    @Override
                    public void onFailure(Call<BannerBean> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    private void initData() {
        new Retrofit.Builder()
                .baseUrl(ApiService.itemUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class)
                .getDataItem(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<Bean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Bean bean) {
                        List<ResultsBean> results = bean.getResults();
                        if (page == 1) {
                            recyclerViewAdapter.initDataItem(results);
                            f1_sma.finishRefresh();
                        } else {
                            recyclerViewAdapter.moreData(results);
                            f1_sma.finishLoadMore();
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initView(View view) {
        f1_rec = (RecyclerView) view.findViewById(R.id.f1_rec);
        f1_rec.setLayoutManager(new LinearLayoutManager(getContext()));
        f1_rec.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        recyclerViewAdapter = new RecyclerViewAdapter(this);
        f1_rec.setAdapter(recyclerViewAdapter);

        registerForContextMenu(f1_rec);

        f1_sma = (SmartRefreshLayout) view.findViewById(R.id.f1_sma);
        f1_sma.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                initData();
            }
        });
        f1_sma.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                initData();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(1, 1, 1, "删除");
        menu.add(1, 2, 1, "收藏");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                recyclerViewAdapter.deleteItem();
                break;
            case 2:
                recyclerViewAdapter.collectItem();
                break;
        }
        return super.onContextItemSelected(item);
    }
}
