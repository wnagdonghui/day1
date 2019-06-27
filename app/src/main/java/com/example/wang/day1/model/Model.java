package com.example.wang.day1.model;

import android.util.Log;

import com.example.wang.day1.bean.Bean;
import com.example.wang.day1.bean.ResultsBean;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Model implements IModel {
    private static final String TAG = "Model";
    @Override
    public void model(final CallBack callBack) {

        OkHttpClient okHttpClient = new OkHttpClient();
        Request build = new Request.Builder()
                .url("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/20/1")
                .build();
        okHttpClient.newCall(build).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.getError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                Bean bean = gson.fromJson(string, Bean.class);
                List<ResultsBean> results = bean.getResults();
                callBack.getData(results);
            }
        });
    }
}
