package com.zzu.ehome.ehomefordoctor.entity;

/**
 * Created by Administrator on 2016/10/31.
 */

public class UpInfoBean {
    private String VersionID;
    private String VersionFlag;
    private String VersionLog;
    ResultBean mResultBean;

    public String getVersionID() {
        return VersionID;
    }

    public void setVersionID(String versionID) {
        VersionID = versionID;
    }

    public String getVersionFlag() {
        return VersionFlag;
    }

    public void setVersionFlag(String versionFlag) {
        VersionFlag = versionFlag;
    }

    public String getVersionLog() {
        return VersionLog;
    }

    public void setVersionLog(String versionLog) {
        VersionLog = versionLog;
    }

    public ResultBean getmResultBean() {
        return mResultBean;
    }

    public void setmResultBean(ResultBean mResultBean) {
        this.mResultBean = mResultBean;
    }
}
