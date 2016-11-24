package com.zzu.ehome.ehomefordoctor.mvp.view;

/**
 * Created by Mersens on 2016/10/29.
 */

public interface InspectionReportDetialView {
    String getType();
    String getId();
    String getUserNum();
    <T>void onSuccess(T t);
    void onError();
}
