package com.zzu.ehome.ehomefordoctor.mvp.view;

/**
 * Created by Administrator on 2016/10/26.
 */

public interface ISuggarDateView {
    <T>void onSuccess(T t);
    String getUserId();
    String getUserNo();
    String getStartTime();
    String getEndTime();
    String getType();


}
