package com.zzu.ehome.ehomefordoctor.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public class StaticDate {
    @SerializedName("GetElectrocardio")
    List<StaticBean> data;

    public List<StaticBean> getData() {
        return data;
    }
}
