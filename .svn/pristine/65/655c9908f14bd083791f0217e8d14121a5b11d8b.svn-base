package com.zzu.ehome.ehomefordoctor.mvp.presenter;

import com.zzu.ehome.ehomefordoctor.mvp.listener.IPressData;

import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;
import com.zzu.ehome.ehomefordoctor.mvp.model.IPressDataImpl;

import com.zzu.ehome.ehomefordoctor.mvp.view.IPressDataView;


/**
 * Created by Administrator on 2016/10/27.
 */

public class PressDataPresenter {
    private IPressData mIPressData;
    private IPressDataView mIPressDateView;

    public PressDataPresenter(IPressDataView pressDataView) {
        this.mIPressDateView = pressDataView;
        mIPressData = new IPressDataImpl();
    }

    public void getPressData() {
        mIPressData.getPressData(mIPressDateView.getUserNo(), mIPressDateView.getUserId(), mIPressDateView.getStartTime(),
                mIPressDateView.getEndTime(), mIPressDateView.getType(), new OnCommonResultListener() {
                    @Override
                    public <T> void onSuccess(T t) {
                        mIPressDateView.onSuccess(t);
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
