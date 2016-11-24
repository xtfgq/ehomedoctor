package com.zzu.ehome.ehomefordoctor.mvp.presenter;

import com.zzu.ehome.ehomefordoctor.mvp.listener.IPressHistory;
import com.zzu.ehome.ehomefordoctor.mvp.listener.ISuggarHistory;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;
import com.zzu.ehome.ehomefordoctor.mvp.model.IPressHistoryImpl;
import com.zzu.ehome.ehomefordoctor.mvp.model.ISuggarHistoryImpl;
import com.zzu.ehome.ehomefordoctor.mvp.view.IPressHistoryView;
import com.zzu.ehome.ehomefordoctor.mvp.view.ISuggarHistoryView;

/**
 * Created by Administrator on 2016/10/27.
 */

public class PressHistoryPresenter {
    private IPressHistory mIPressHistory;
    private IPressHistoryView mIPressHistoryView;

    public PressHistoryPresenter(IPressHistoryView pressHistoryView) {
        this.mIPressHistoryView = pressHistoryView;
        mIPressHistory = new IPressHistoryImpl();
    }

    public void getPressHistoryData() {
        mIPressHistory.getHistory(mIPressHistoryView.getUserId(), mIPressHistoryView.getUserNo(), mIPressHistoryView.getPageSize(),
                mIPressHistoryView.getPageIndex(), mIPressHistoryView.getTypeHistory(), new OnCommonResultListener() {
                    @Override
                    public <T> void onSuccess(T t) {
                        mIPressHistoryView.onResultSuccess(t);
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
