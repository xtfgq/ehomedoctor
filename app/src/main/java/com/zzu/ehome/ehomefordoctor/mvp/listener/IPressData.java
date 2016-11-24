package com.zzu.ehome.ehomefordoctor.mvp.listener;

/**
 * Created by Administrator on 2016/10/27.
 */

public interface IPressData {
    void getPressData(String cardNo,String UserID, String StartTime, String EndTime, String Type,final OnCommonResultListener listener);
}
