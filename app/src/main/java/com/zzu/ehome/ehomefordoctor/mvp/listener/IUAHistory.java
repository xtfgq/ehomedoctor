package com.zzu.ehome.ehomefordoctor.mvp.listener;

/**
 * Created by Administrator on 2016/10/27.
 */

public interface IUAHistory {
    void getHistory(String usrid, String CardNo,String pagesize, String pageindex, String type,final OnCommonResultListener listener);
}
