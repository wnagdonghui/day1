package com.example.wang.day1.db;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.wang.day1.dao.DaoMaster;
import com.example.wang.day1.dao.DaoSession;


public class MyApplication extends Application {
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "my.db");
        SQLiteDatabase writableDatabase = devOpenHelper.getWritableDatabase();
        daoSession = new DaoMaster(writableDatabase).newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
