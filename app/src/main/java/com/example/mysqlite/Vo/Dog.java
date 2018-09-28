package com.example.mysqlite.Vo;

import io.realm.RealmObject;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: MySqlite
 * @Package com.example.mysqlite.DB
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/9/26 11:43
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class Dog extends RealmObject {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
