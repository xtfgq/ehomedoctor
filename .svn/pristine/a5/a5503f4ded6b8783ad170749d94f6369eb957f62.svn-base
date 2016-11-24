package com.zzu.ehome.ehomefordoctor.mvp.presenter;

import android.content.Context;

import com.zzu.ehome.ehomefordoctor.mvp.listener.IOnLineInfo;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnOnLineLister;
import com.zzu.ehome.ehomefordoctor.mvp.model.IHzOnLineImpl;
import com.zzu.ehome.ehomefordoctor.mvp.view.IOnLineView;
/**
 * Created by Administrator on 2016/10/15.
 */

public class HzOnlinePresenter {
    private IOnLineInfo mIOnLineInfo;
    private IOnLineView mIOnLineView;
    public HzOnlinePresenter(IOnLineView mIOnLineView){
        this.mIOnLineView=mIOnLineView;
        mIOnLineInfo=new IHzOnLineImpl();
    }

    public void postOnline(){
        mIOnLineInfo.PostOnline(new OnOnLineLister(){
            @Override
            public void onStart() {

            }

            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void OnSuccess(String str) {
                mIOnLineView.onLineSuccess(str);
            }
        });

    }
}
