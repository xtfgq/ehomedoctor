package com.zzu.ehome.ehomefordoctor.mvp.view;

/**
 * Created by Mersens on 2016/10/25.
 */

public interface IDynamicView {
    String getUserid();
    String  getNo();
    <T>void onSuccess(T t);
    void onError(String error);
}
