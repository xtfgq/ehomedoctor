package com.zzu.ehome.ehomefordoctor.mvp.listener;

/**
 * Created by Administrator on 2017/2/22.
 */

public interface ISignInquiry {
    void getSignData(String doctorid,String cardno,OnCommonResultListener listener);
}
