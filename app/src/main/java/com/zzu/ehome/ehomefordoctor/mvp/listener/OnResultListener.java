package com.zzu.ehome.ehomefordoctor.mvp.listener;

/**
 * Created by Mersens on 2016/9/14.
 */
public interface OnResultListener {
    void onStart();
    void onError(Exception e);
    void onFinish();
}
