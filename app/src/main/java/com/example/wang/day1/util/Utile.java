package com.example.wang.day1.util;

import com.example.wang.day1.db.MyApplication;
import com.example.wang.day1.bean.ResultsBean;
import com.example.wang.day1.dao.DaoSession;
import com.example.wang.day1.dao.ResultsBeanDao;

import java.util.List;

public class Utile {
        public static ResultsBean queryItem(ResultsBean resultsBean){
            DaoSession daoSession = MyApplication.getDaoSession();
            ResultsBean unique = daoSession.queryBuilder(ResultsBean.class)
                    .where(ResultsBeanDao.Properties._id.eq(resultsBean.get_id()))
                    .build()
                    .unique();
            return unique;
        }


        public static void addItem(ResultsBean resultsBean){
            DaoSession daoSession = MyApplication.getDaoSession();
            ResultsBean resultsBean1 = queryItem(resultsBean);
            if (resultsBean1==null){
                daoSession.insert(resultsBean);
            }
        }

        public static List<ResultsBean> queryAll(){
            DaoSession daoSession = MyApplication.getDaoSession();
            List<ResultsBean> resultsBeans = daoSession.loadAll(ResultsBean.class);
            return resultsBeans;
        }

        public static void deleteItem(ResultsBean resultsBean){
            DaoSession daoSession = MyApplication.getDaoSession();
            ResultsBean resultsBean1 = queryItem(resultsBean);
            if (resultsBean1!=null){
                daoSession.delete(resultsBean1);
            }
        }
}
