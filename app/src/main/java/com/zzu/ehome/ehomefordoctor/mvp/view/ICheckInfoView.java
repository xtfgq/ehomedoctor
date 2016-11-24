package com.zzu.ehome.ehomefordoctor.mvp.view;

/**
 * Created by Administrator on 2016/10/28.
 */

public interface ICheckInfoView {
    String getMoblie();
    String getPassword();
    String getCode();
    String getEditPhone();
    String getCheck();
    String getRealPhone();
    <T>void onMessageSuccess(T t);
}
