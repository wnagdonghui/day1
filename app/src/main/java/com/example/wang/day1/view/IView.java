package com.example.wang.day1.view;


import com.example.wang.day1.bean.ResultsBean;

import java.util.List;

public interface IView {
    void getData(List<ResultsBean> list);
    void getError(String error);
}
