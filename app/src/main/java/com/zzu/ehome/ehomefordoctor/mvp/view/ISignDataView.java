package com.zzu.ehome.ehomefordoctor.mvp.view;

/**
 * Created by Administrator on 2017/2/22.
 */

public interface ISignDataView {
    <T>void onResultSuccess(T t);
    String getUserNo();
    String getDoctorId();
    void onError(String e);
}
