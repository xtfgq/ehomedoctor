package com.zzu.ehome.ehomefordoctor.mvp.presenter;

import com.zzu.ehome.ehomefordoctor.mvp.listener.ICholesterinData;

import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;
import com.zzu.ehome.ehomefordoctor.mvp.model.CholesterinDataImpl;

import com.zzu.ehome.ehomefordoctor.mvp.view.CholesterinDataView;


/**
 * Created by Administrator on 2016/10/27.
 */

public class CholesterinDataPresenter {
    private ICholesterinData mICholesterinData;
    private CholesterinDataView mCholesterinDataView;

    public CholesterinDataPresenter(CholesterinDataView chDataView) {
        this.mCholesterinDataView = chDataView;
        mICholesterinData = new CholesterinDataImpl();
    }

    public void getChData() {
        mICholesterinData.getCholesterinData(mCholesterinDataView.getUserNo(),  mCholesterinDataView.getStartTime(),
                mCholesterinDataView.getEndTime(), mCholesterinDataView.getType(),mCholesterinDataView.getTop(), new OnCommonResultListener() {
                    @Override
                    public <T> void onSuccess(T t) {
                        mCholesterinDataView.onSuccess(t);
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
