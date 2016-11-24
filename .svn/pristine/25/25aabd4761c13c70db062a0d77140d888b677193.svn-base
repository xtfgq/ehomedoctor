package com.zzu.ehome.ehomefordoctor.mvp.presenter;

import com.zzu.ehome.ehomefordoctor.mvp.listener.IChHistory;
import com.zzu.ehome.ehomefordoctor.mvp.listener.IPressHistory;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;
import com.zzu.ehome.ehomefordoctor.mvp.model.CholesterinHistoryImpl;
import com.zzu.ehome.ehomefordoctor.mvp.model.IPressHistoryImpl;
import com.zzu.ehome.ehomefordoctor.mvp.view.ChHistoryView;
import com.zzu.ehome.ehomefordoctor.mvp.view.IPressHistoryView;

/**
 * Created by Administrator on 2016/10/27.
 */

public class ChHistoryPresenter {
    private IChHistory mIChHistory;
    private ChHistoryView mChHistoryView;

    public ChHistoryPresenter(ChHistoryView chHistoryView) {
        this.mChHistoryView = chHistoryView;
        mIChHistory = new CholesterinHistoryImpl();
    }

    public void getChHistoryData() {
        mIChHistory.getHistory(mChHistoryView.getUserId(), mChHistoryView.getUserNo(), mChHistoryView.getPageSize(),
                mChHistoryView.getPageIndex(), mChHistoryView.getTypeHistory(), new OnCommonResultListener() {
                    @Override
                    public <T> void onSuccess(T t) {
                        mChHistoryView.onResultSuccess(t);
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
