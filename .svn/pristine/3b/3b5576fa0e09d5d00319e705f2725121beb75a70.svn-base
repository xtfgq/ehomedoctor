package com.zzu.ehome.ehomefordoctor.mvp.presenter;


import com.zzu.ehome.ehomefordoctor.mvp.listener.IGetCodeData;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;

import com.zzu.ehome.ehomefordoctor.mvp.model.IGetCodeDataImpl;

import com.zzu.ehome.ehomefordoctor.mvp.view.IGetCodeView;

/**
 * Created by Administrator on 2016/10/28.
 */

public class IGetCodePresenter {
    private IGetCodeData mIGetCodeData;
    private IGetCodeView mIGetCodeView;

    public IGetCodePresenter(IGetCodeView getCodeView) {
        this.mIGetCodeView = getCodeView;
        mIGetCodeData = new IGetCodeDataImpl();
    }

    public void getGetCoke() {
        mIGetCodeData.getCode(mIGetCodeView.getUserMobile(), mIGetCodeView.getFlag(),  new OnCommonResultListener() {
                    @Override
                    public <T> void onSuccess(T t) {
                        mIGetCodeView.onGetCodeSuccess(t);
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onError(Exception e) {

                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }
}
