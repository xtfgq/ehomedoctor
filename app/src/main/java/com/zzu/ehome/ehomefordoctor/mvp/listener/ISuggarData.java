package com.zzu.ehome.ehomefordoctor.mvp.listener;

/**
 * Created by Administrator on 2016/10/26.
 */

public interface ISuggarData {
    void getSuggarData(String CardNO, String UserID, String StartTime, String EndTime, String type,OnCommonResultListener listener);
}
