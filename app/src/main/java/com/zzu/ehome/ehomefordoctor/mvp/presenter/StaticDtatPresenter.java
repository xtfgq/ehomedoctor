package com.zzu.ehome.ehomefordoctor.mvp.presenter;

import com.zzu.ehome.ehomefordoctor.mvp.listener.IStaticData;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;
import com.zzu.ehome.ehomefordoctor.mvp.model.IStaticDataImpl;
import com.zzu.ehome.ehomefordoctor.mvp.view.IDynamicView;
import com.zzu.ehome.ehomefordoctor.mvp.view.IStaticView;

/**
 * Created by Mersens on 2016/10/26.
 */

public class StaticDtatPresenter {
    private IStaticData mIStaticData;
    private IStaticView mIStaticView;
    public StaticDtatPresenter(IStaticView view){
        this.mIStaticView=view;
        mIStaticData=new IStaticDataImpl();
    }

    public void getStaticData(){
        mIStaticData.getStaticData(mIStaticView.getUserid(), mIStaticView.getCardNo(),new OnCommonResultListener() {
            @Override
            public <T> void onSuccess(T t) {
                mIStaticView.onSuccess(t);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onError(Exception e) {
                mIStaticView.onError(e+"");
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
