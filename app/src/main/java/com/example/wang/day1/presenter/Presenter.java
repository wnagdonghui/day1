package com.example.wang.day1.presenter;


import com.example.wang.day1.bean.ResultsBean;
import com.example.wang.day1.model.CallBack;
import com.example.wang.day1.model.Model;
import com.example.wang.day1.view.IView;

import java.util.List;

//杨明
public class Presenter implements IPresenter{
    private IView view;
    private final Model model;

    public Presenter(IView view) {
        this.view = view;
        model = new Model();
    }


    @Override
    public void presenter() {
        model.model(new CallBack() {
            @Override
            public void getData(List<ResultsBean> list) {
                view.getData(list);
            }

            @Override
            public void getError(String error) {
                view.getError(error);
            }
        });
    }
}
