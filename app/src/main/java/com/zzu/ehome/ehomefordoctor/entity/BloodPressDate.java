package com.zzu.ehome.ehomefordoctor.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2016/10/20.
 */

public class BloodPressDate {
    @SerializedName("HealthDataInquiryWithPage")
    List<BloodPressBean> data;

    public List<BloodPressBean> getData() {
        return data;
    }
}
