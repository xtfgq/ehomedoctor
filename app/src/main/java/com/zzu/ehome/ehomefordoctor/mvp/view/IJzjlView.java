package com.zzu.ehome.ehomefordoctor.mvp.view;

/**
 * Created by Mersens on 2016/10/26.
 */

public interface IJzjlView {
    String getUserid();
    String getPageSize();
    String getPage();
    <T>void onSuccess(T t);
    void onError();
}
