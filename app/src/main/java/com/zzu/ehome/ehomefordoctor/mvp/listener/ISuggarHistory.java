package com.zzu.ehome.ehomefordoctor.mvp.listener;

/**
 * Created by Administrator on 2016/10/27.
 */

public interface ISuggarHistory {
    void getHistory(String usrid, String CardNo,String pagesize, String pageindex, String type,OnCommonResultListener listener);
}
