package com.zzu.ehome.ehomefordoctor.mvp.presenter;

import com.zzu.ehome.ehomefordoctor.mvp.listener.ICheckInfoData;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnMessageResultListener;
import com.zzu.ehome.ehomefordoctor.mvp.model.ICheckInfoImpl;
import com.zzu.ehome.ehomefordoctor.mvp.view.ICheckInfoView;

/**
 * Created by Administrator on 2016/10/28.
 */

public class CheckInfoPresenter {
    private ICheckInfoData mICheckInfoData;
    private ICheckInfoView mICheckInfoView;

    public CheckInfoPresenter(ICheckInfoView checkView) {
        this.mICheckInfoView = checkView;
        mICheckInfoData = new ICheckInfoImpl();
    }

    public void getCheckInfoMessage() {
        mICheckInfoData.getCheckInfo(mICheckInfoView.getMoblie(), mICheckInfoView.getEditPhone(), mICheckInfoView.getPassword(),
                mICheckInfoView.getCode(), mICheckInfoView.getCheck(), mICheckInfoView.getRealPhone(),new OnMessageResultListener() {

                    @Override
                    public <T> void onMessageSuccess(T t) {
                        mICheckInfoView.onMessageSuccess(t);
                    }
                });
    }
}
