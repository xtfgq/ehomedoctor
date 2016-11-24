package com.zzu.ehome.ehomefordoctor.mvp.listener;

/**
 * Created by Administrator on 2016/10/28.
 */

public interface IFindPsdData {
    void changePsd(String UserMobile, String Password, String NewPassword, final  OnCommonResultListener listener);
}
