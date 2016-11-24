package com.zzu.ehome.ehomefordoctor.mvp.presenter;

import com.zzu.ehome.ehomefordoctor.mvp.listener.IStaticData;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;
import com.zzu.ehome.ehomefordoctor.mvp.model.IStaticDataImpl;
import com.zzu.ehome.ehomefordoctor.mvp.view.IDynamicView;

/**
 * Created by Mersens on 2016/10/26.
 */

public class StaticDtatPresenter {
    private IStaticData mIStaticData;
    private IDynamicView mIDynamicView;
    public StaticDtatPresenter(IDynamicView view){
        this.mIDynamicView=view;
        mIStaticData=new IStaticDataImpl();
    }

    public void getStaticData(){
        mIStaticData.getDynamicData(mIDynamicView.getUserid(), new OnCommonResultListener() {
            @Override
            public <T> void onSuccess(T t) {
                mIDynamicView.onSuccess(t);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onError(Exception e) {
                mIDynamicView.onError();
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
