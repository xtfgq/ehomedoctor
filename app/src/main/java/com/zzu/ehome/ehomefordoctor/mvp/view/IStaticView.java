package com.zzu.ehome.ehomefordoctor.mvp.view;

/**
 * Created by Mersens on 2016/10/25.
 */

public interface IStaticView {
    String getUserid();
    String getCardNo();
    <T>void onSuccess(T t);
    void onError(String error);
}
