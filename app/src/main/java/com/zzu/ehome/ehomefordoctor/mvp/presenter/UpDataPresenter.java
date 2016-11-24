package com.zzu.ehome.ehomefordoctor.mvp.presenter;

import android.view.View;

import com.baidu.platform.comapi.map.I;
import com.zzu.ehome.ehomefordoctor.mvp.listener.IUAHistory;
import com.zzu.ehome.ehomefordoctor.mvp.listener.IUpVersionData;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;
import com.zzu.ehome.ehomefordoctor.mvp.model.IUAHistoryImpl;
import com.zzu.ehome.ehomefordoctor.mvp.model.IUpVersionDataImpl;
import com.zzu.ehome.ehomefordoctor.mvp.view.IUAHistoryView;
import com.zzu.ehome.ehomefordoctor.mvp.view.IUpView;

/**
 * Created by Administrator on 2016/10/31.
 */

public class UpDataPresenter {
    private IUpVersionData mIUpVersionData;
    private IUpView mIUpView;

    public UpDataPresenter(IUpView upView) {
        this.mIUpView = upView;
        mIUpVersionData = new IUpVersionDataImpl();
    }

    public void getUp() {
        mIUpVersionData.getUp(new OnCommonResultListener() {
                    @Override
                    public <T> void onSuccess(T t) {
                        mIUpView.onUpSuccess(t);
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
