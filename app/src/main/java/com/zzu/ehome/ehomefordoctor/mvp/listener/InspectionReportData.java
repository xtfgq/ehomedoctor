package com.zzu.ehome.ehomefordoctor.mvp.listener;

/**
 * Created by Mersens on 2016/10/29.
 */

public interface InspectionReportData {
    void getInspectionReport(String userno,String type,String pagesize,String page,OnCommonResultListener listener);
}
