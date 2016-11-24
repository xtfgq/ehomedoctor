package com.zzu.ehome.ehomefordoctor.mvp.view;

import com.zzu.ehome.ehomefordoctor.entity.UsersBySignDoctor;

import java.util.List;

/**
 * Created by Mersens on 2016/9/29.
 */

public interface IHzInfoView {
    void hidePro();
    void showPro();
    void onSuccess(List<UsersBySignDoctor> list);
    void onErroe(Exception e);
}
