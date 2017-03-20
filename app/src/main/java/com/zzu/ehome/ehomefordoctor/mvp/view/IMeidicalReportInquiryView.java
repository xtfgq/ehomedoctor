package com.zzu.ehome.ehomefordoctor.mvp.view;

/**
 * Created by Administrator on 2016/12/7.
 */

public interface IMeidicalReportInquiryView {
    <T>void onSuccess(T t);
    String getUserId();
    void onError(String error);


}
