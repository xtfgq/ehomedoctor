package com.zzu.ehome.ehomefordoctor.mvp.presenter;

import com.zzu.ehome.ehomefordoctor.mvp.listener.ISuggarData;

import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;
import com.zzu.ehome.ehomefordoctor.mvp.model.ISuggarDataImpl;

import com.zzu.ehome.ehomefordoctor.mvp.view.ISuggarDateView;


/**
 * Created by Administrator on 2016/10/27.
 */

public class SuggarPresenter {
    private ISuggarData mSuggarData;
    private ISuggarDateView mSuggarDateView;

    public SuggarPresenter(ISuggarDateView suggarDataView) {
        this.mSuggarDateView = suggarDataView;
        mSuggarData = new ISuggarDataImpl();
    }

    public void getSuggarData() {
        mSuggarData.getSuggarData(mSuggarDateView.getUserNo(), mSuggarDateView.getUserId(), mSuggarDateView.getStartTime(),
                mSuggarDateView.getEndTime(), mSuggarDateView.getType(), new OnCommonResultListener() {
                    @Override
                    public <T> void onSuccess(T t) {
                        mSuggarDateView.onSuccess(t);
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
