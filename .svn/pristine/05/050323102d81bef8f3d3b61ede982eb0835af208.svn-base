package com.zzu.ehome.ehomefordoctor.mvp.presenter;

import com.zzu.ehome.ehomefordoctor.mvp.listener.IOffLineInfo;
import com.zzu.ehome.ehomefordoctor.mvp.listener.IOnLineInfo;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnOffLineLister;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnOnLineLister;
import com.zzu.ehome.ehomefordoctor.mvp.model.IHzOffLineImpl;
import com.zzu.ehome.ehomefordoctor.mvp.model.IHzOnLineImpl;
import com.zzu.ehome.ehomefordoctor.mvp.view.IOffLineView;
import com.zzu.ehome.ehomefordoctor.mvp.view.IOnLineView;

/**
 * Created by Administrator on 2016/10/15.
 */

public class HzOfflinePresenter {
    private IOffLineInfo mIOffLineInfo;
    private IOffLineView mIOffLineView;
    public HzOfflinePresenter(IOffLineView mIOffLineView){
        this.mIOffLineView=mIOffLineView;
        mIOffLineInfo=new IHzOffLineImpl();
    }

    public void postOffline() {
        mIOffLineInfo.PostOffline(new OnOffLineLister() {
            @Override
            public void OnSuccess(String str) {
                mIOffLineView.onOffLineSuccess(str);
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
