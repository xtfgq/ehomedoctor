package com.zzu.ehome.ehomefordoctor.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/2/22.
 */

public class SignBean {
//    "ID": "2285",
//            "UserID": "50857",
//            "CardNO": "410102198201251510",
//            "DoctorID": "18",
//            "DoctorName": "白蓉",
//            "HospitalID": "",
//            "HospitalName": "郑州大学第一附属医院",
//            "UserName": "鬼片",
//            "UserMobile": "15637147870",
//            "StartTime": "2017/2/22 0:00:00",
//            "EndTime": "2018/2/22 0:00:00",
//            "CreatedBy": "",
//            "CreatedDate": "2017/2/22 11:39:16",
//            "UpdatedBy": "",
//            "UpdatedDate": "2017/2/22 11:39:16",
//            "DeleteFlag": "0"
@SerializedName("DoctorName")
    String DoctorName;
    @SerializedName("HospitalName")
    String HospitalName;
    @SerializedName("UserName")
    String UserName;
    @SerializedName("CardNO")
    String CardNO;

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    @SerializedName("StartTime")
    String StartTime;
    @SerializedName("EndTime")
    String EndTime;
//    "StartTime": "2017/2/22 0:00:00",
//            "EndTime": "2018/2/22 0:00:00",

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String hospitalName) {
        HospitalName = hospitalName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getCardNO() {
        return CardNO;
    }

    public void setCardNO(String cardNO) {
        CardNO = cardNO;
    }
}
