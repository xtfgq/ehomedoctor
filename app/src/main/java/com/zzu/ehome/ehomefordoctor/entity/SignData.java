package com.zzu.ehome.ehomefordoctor.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/2/22.
 */

public class SignData {
    @SerializedName("SignInquiry")
    List<SignBean> data;

    public List<SignBean> getData() {
        return data;
    }
}
