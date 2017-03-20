package com.zzu.ehome.ehomefordoctor.mvp.view;

/**
 * Created by Mersens on 2016/10/29.
 */

public interface InspectionReportView {
    String getPageSize();
    String getIndex();
    String getUserNum();
    String getType();
    <T>void onSuccess(T t);
    void onError();
}
