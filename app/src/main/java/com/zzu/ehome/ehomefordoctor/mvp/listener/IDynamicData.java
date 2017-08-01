package com.zzu.ehome.ehomefordoctor.mvp.listener;

/**
 * Created by Mersens on 2016/10/25.
 */

public interface IDynamicData {
    void getDynamicData(String userid,String cardNo,OnCommonResultListener listener);
}
