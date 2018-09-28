package com.example.mysqlite.Vo;

import io.realm.RealmObject;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: MySqlite
 * @Package com.example.mysqlite.Vo
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/9/26 11:43
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class Person extends RealmObject {

    private String name;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
