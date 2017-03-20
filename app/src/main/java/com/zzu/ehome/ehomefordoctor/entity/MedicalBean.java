package com.zzu.ehome.ehomefordoctor.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/12/7.
 */

public class MedicalBean {
    public ResultBean getmResultBean() {
        return mResultBean;
    }

    public void setmResultBean(ResultBean mResultBean) {
        this.mResultBean = mResultBean;
    }

    private ResultBean mResultBean;
    @SerializedName("CheckTime")
    private String CheckTime;
    @SerializedName("ID")
    private String ID;
    @SerializedName("ReportImage")
    private String ReportImage;
    @SerializedName("CreatedDate")
    private String CreatedDate;
    @SerializedName("UserName")
    private String UserName;
    @SerializedName("InstituteName")
    private String InstituteName;

    public String getCheckTime() {
        return CheckTime;
    }

    public void setCheckTime(String checkTime) {
        CheckTime = checkTime;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getReportImage() {
        return ReportImage;
    }

    public void setReportImage(String reportImage) {
        ReportImage = reportImage;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getInstituteName() {
        return InstituteName;
    }

    public void setInstituteName(String instituteName) {
        InstituteName = instituteName;
    }
}
