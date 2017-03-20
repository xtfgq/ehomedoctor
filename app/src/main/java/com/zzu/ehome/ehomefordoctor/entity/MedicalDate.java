package com.zzu.ehome.ehomefordoctor.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2016/12/7.
 */

public class MedicalDate {
    @SerializedName("MeidicalReportInquiry")
    List<MedicalBean> data;

    public List<MedicalBean> getData() {
        return data;
    }
}
