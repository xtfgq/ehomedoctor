package com.zzu.ehome.ehomefordoctor.mvp.presenter;

import com.zzu.ehome.ehomefordoctor.mvp.listener.IMSDoctorFindPassData;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;

import com.zzu.ehome.ehomefordoctor.mvp.model.IMSDoctorFindPassDataImpl;

import com.zzu.ehome.ehomefordoctor.mvp.view.IMsDoctorFindPassView;

/**
 * Created by Administrator on 2016/10/28.
 */

public class IMSDoctorFindPsdPresenter {
    private IMSDoctorFindPassData mIMSDoctorFindPassData;
    private IMsDoctorFindPassView mIMsDoctorFindPassView;

    public IMSDoctorFindPsdPresenter(IMsDoctorFindPassView iMsDoctorFindPassView) {
        this.mIMsDoctorFindPassView = iMsDoctorFindPassView;
        mIMSDoctorFindPassData = new IMSDoctorFindPassDataImpl();
    }

    public void getFindPsd() {
        mIMSDoctorFindPassData.findPsd(mIMsDoctorFindPassView.getMoblie(), mIMsDoctorFindPassView.getPassword(), new OnCommonResultListener() {
                    @Override
                    public <T> void onSuccess(T t) {

                        mIMsDoctorFindPassView.onFindSuccess(t);
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
