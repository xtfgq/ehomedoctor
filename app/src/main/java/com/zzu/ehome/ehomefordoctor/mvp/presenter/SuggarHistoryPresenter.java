package com.zzu.ehome.ehomefordoctor.mvp.presenter;

import com.zzu.ehome.ehomefordoctor.mvp.listener.ISuggarData;
import com.zzu.ehome.ehomefordoctor.mvp.listener.ISuggarHistory;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;
import com.zzu.ehome.ehomefordoctor.mvp.model.ISuggarDataImpl;
import com.zzu.ehome.ehomefordoctor.mvp.model.ISuggarHistoryImpl;
import com.zzu.ehome.ehomefordoctor.mvp.view.ISuggarDateView;
import com.zzu.ehome.ehomefordoctor.mvp.view.ISuggarHistoryView;

/**
 * Created by Administrator on 2016/10/27.
 */

public class SuggarHistoryPresenter {
    private ISuggarHistory mISuggarHistory;
    private ISuggarHistoryView mISuggarHistoryView;

    public SuggarHistoryPresenter(ISuggarHistoryView suggarHistoryView) {
        this.mISuggarHistoryView = suggarHistoryView;
        mISuggarHistory = new ISuggarHistoryImpl();
    }

    public void getSuggarHistoryData() {
        mISuggarHistory.getHistory(mISuggarHistoryView.getUserId(), mISuggarHistoryView.getUserNo(), mISuggarHistoryView.getPageSize(),
                mISuggarHistoryView.getPageIndex(), mISuggarHistoryView.getTypeHistory(), new OnCommonResultListener() {
                    @Override
                    public <T> void onSuccess(T t) {
                        mISuggarHistoryView.onResultSuccess(t);
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
