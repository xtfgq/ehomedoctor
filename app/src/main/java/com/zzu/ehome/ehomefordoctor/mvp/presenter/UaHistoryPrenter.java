package com.zzu.ehome.ehomefordoctor.mvp.presenter;


import com.zzu.ehome.ehomefordoctor.mvp.listener.IUAHistory;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;

import com.zzu.ehome.ehomefordoctor.mvp.model.IUAHistoryImpl;

import com.zzu.ehome.ehomefordoctor.mvp.view.IUAHistoryView;

/**
 * Created by Administrator on 2016/10/27.
 */

public class UaHistoryPrenter {
    private IUAHistory mIUAHistory;
    private IUAHistoryView mIUAHistoryView;

    public UaHistoryPrenter(IUAHistoryView uaHistoryView) {
        this.mIUAHistoryView = uaHistoryView;
        mIUAHistory = new IUAHistoryImpl();
    }

    public void getHistoryData() {
        mIUAHistory.getHistory(mIUAHistoryView.getUserId(), mIUAHistoryView.getUserNo(), mIUAHistoryView.getPageSize(),
                mIUAHistoryView.getPageIndex(), mIUAHistoryView.getTypeHistory(), new OnCommonResultListener() {
                    @Override
                    public <T> void onSuccess(T t) {
                        mIUAHistoryView.onResultSuccess(t);
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
