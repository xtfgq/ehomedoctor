package com.zzu.ehome.ehomefordoctor.mvp.view;

import com.zzu.ehome.ehomefordoctor.entity.DoctorBean;

/**
 * Created by Mersens on 2016/9/18.
 */
public interface ILoginView {
    void hidePro();
    void showPro();
    String getUserName();
    String getPsd();
    void onSuccess(DoctorBean userBean);
    void onErroe(Exception e);
}
