package com.zzu.ehome.ehomefordoctor.mvp.presenter;

import com.zzu.ehome.ehomefordoctor.mvp.listener.ISignInquiry;

import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;
import com.zzu.ehome.ehomefordoctor.mvp.model.ISignDataImpl;

import com.zzu.ehome.ehomefordoctor.mvp.view.ISignDataView;


/**
 * Created by Administrator on 2016/10/27.
 */

public class ISignDataPresenter {
    private ISignInquiry mISignInquiry;
    private ISignDataView mISignDataView;

    public ISignDataPresenter(ISignDataView iSignView) {
        this.mISignDataView = iSignView;
        mISignInquiry = new ISignDataImpl();
    }

    public void getSignData() {
        mISignInquiry.getSignData(mISignDataView.getDoctorId(),mISignDataView.getUserNo(),new OnCommonResultListener() {
            @Override
            public <T> void onSuccess(T t) {
                mISignDataView.onResultSuccess(t);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onError(Exception e) {
                mISignDataView.onError(e.toString());
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
