package com.zzu.ehome.ehomefordoctor.mvp.listener;

import static android.R.attr.password;

/**
 * Created by Administrator on 2016/10/28.
 */

public interface ICheckInfoData {
    void getCheckInfo(String mobile,String editPhone,String password,String code,String check,String realphone,final OnMessageResultListener listener);
}
