package com.zzu.ehome.ehomefordoctor.mvp.presenter;

import com.zzu.ehome.ehomefordoctor.mvp.listener.ISuggarData;
import com.zzu.ehome.ehomefordoctor.mvp.listener.IUAData;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;
import com.zzu.ehome.ehomefordoctor.mvp.model.ISuggarDataImpl;
import com.zzu.ehome.ehomefordoctor.mvp.model.IUADataImpl;
import com.zzu.ehome.ehomefordoctor.mvp.view.ISuggarDateView;
import com.zzu.ehome.ehomefordoctor.mvp.view.IUaDataView;

/**
 * Created by Administrator on 2016/10/27.
 */

public class UaDataPresenter {
    private IUAData mIUAData;
    private IUaDataView mIUaDataView;

    public UaDataPresenter(IUaDataView uaDataView) {
        this.mIUaDataView = uaDataView;
        mIUAData = new IUADataImpl();
    }

    public void getUaData() {
        mIUAData.getUaData(mIUaDataView.getUserNo(), mIUaDataView.getStartTime(), mIUaDataView.getEndTime(),
                mIUaDataView.getType(), mIUaDataView.getTop(), new OnCommonResultListener() {
                    @Override
                    public <T> void onSuccess(T t) {
                        mIUaDataView.onSuccess(t);
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
