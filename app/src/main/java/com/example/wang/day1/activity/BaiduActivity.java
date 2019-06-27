package com.example.wang.day1.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.wang.day1.R;


public class BaiduActivity extends AppCompatActivity {

    private WebView bai_wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu);
        initView();
    }

    private void initView() {
        bai_wv = (WebView) findViewById(R.id.bai_wv);
        bai_wv.loadUrl("http://www.baidu.com");
        bai_wv.setWebViewClient(new WebViewClient());

    }
}
