package com.zzu.ehome.ehomefordoctor.mvp.model;

import android.text.TextUtils;

import com.zzu.ehome.ehomefordoctor.mvp.listener.ICheckInfoData;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnMessageResultListener;
import com.zzu.ehome.ehomefordoctor.utils.CheckUtils;

/**
 * Created by Administrator on 2016/10/28.
 */

public class ICheckInfoImpl implements ICheckInfoData{
    @Override
    public void getCheckInfo(String mobile, String editPhone,String password, String code,String check,String realphone, final OnMessageResultListener listener) {
        if(TextUtils.isEmpty(mobile)){
            listener.onMessageSuccess("请输入手机号！");
            return;
        }

        if(TextUtils.isEmpty(code)){
            listener.onMessageSuccess("请输入验证码！");
            return;
        }
        if(TextUtils.isEmpty(password)){
            listener.onMessageSuccess("请输入密码！");
            return;
        }
        if(password.length()<6){
            listener.onMessageSuccess("密码长度过短！");
            return;
        }
        if(!CheckUtils.isMobileNO(mobile)){
            listener.onMessageSuccess("请输入正确的手机号码!");
            return;
        }
        if(!code.equals(check)){
            listener.onMessageSuccess("请输入正确的验证码！");
            return;
        }
        if(!TextUtils.isEmpty(realphone)) {
            if (!mobile.equals(realphone)) {
                listener.onMessageSuccess("手机号与验证码不一致！");
                return;
            }
        }


        listener.onMessageSuccess("ok");


    }
}
