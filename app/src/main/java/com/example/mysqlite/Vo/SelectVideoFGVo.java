package com.example.mysqlite.Vo;

import java.io.Serializable;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: MySqlite
 * @Package com.example.mysqlite.Vo
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/9/28 11:01
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class SelectVideoFGVo implements Serializable{
    private String data;
    private String name;
    private String id;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
