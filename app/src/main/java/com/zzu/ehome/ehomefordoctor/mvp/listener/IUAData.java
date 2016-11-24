package com.zzu.ehome.ehomefordoctor.mvp.listener;

/**
 * Created by Administrator on 2016/10/27.
 */

public interface IUAData {
    void getUaData(String CardNO,String StartTime,String EndTime,String Type,String Top,final OnCommonResultListener listener);
}
