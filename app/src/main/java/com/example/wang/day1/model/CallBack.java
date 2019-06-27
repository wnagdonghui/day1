package com.example.wang.day1.model;


import com.example.wang.day1.bean.ResultsBean;

import java.util.List;

public interface CallBack {
    void getData(List<ResultsBean> list);
    void getError(String error);
}
