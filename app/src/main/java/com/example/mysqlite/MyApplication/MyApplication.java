package com.example.mysqlite.MyApplication;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: MySqlite
 * @Package com.example.mysqlite.MyApplication
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/9/26 11:45
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration build = new RealmConfiguration.Builder()
                .name("userInfom")
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(build);

    }
}
