package com.zzu.ehome.ehomefordoctor.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/10/25.
 */

public class UaBean {
    public ResultBean getmResultBean() {
        return mResultBean;
    }

    public void setmResultBean(ResultBean mResultBean) {
        this.mResultBean = mResultBean;
    }

    private ResultBean mResultBean;

@SerializedName("MonitorTime")
private String MonitorTime;
    @SerializedName("UA")
    private String UA;

    public String getMonitorTime() {
        return MonitorTime;
    }

    public void setMonitorTime(String monitorTime) {
        MonitorTime = monitorTime;
    }

    public String getUA() {
        return UA;
    }

    public void setUA(String UA) {
        this.UA = UA;
    }
}
