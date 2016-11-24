package com.zzu.ehome.ehomefordoctor.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/10/25.
 */

public class CholRes {
    public ResultBean getmResultBean() {
        return mResultBean;
    }

    public void setmResultBean(ResultBean mResultBean) {
        this.mResultBean = mResultBean;
    }

    private ResultBean mResultBean;
    @SerializedName("MonitorTime")
  String MonitorTime;
    @SerializedName("CHOL")
    String CHOL;

    public String getMonitorTime() {
        return MonitorTime;
    }

    public void setMonitorTime(String monitorTime) {
        MonitorTime = monitorTime;
    }

    public String getCHOL() {
        return CHOL;
    }

    public void setCHOL(String CHOL) {
        this.CHOL = CHOL;
    }
}
