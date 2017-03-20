package com.zzu.ehome.ehomefordoctor.mvp.presenter;

import com.zzu.ehome.ehomefordoctor.mvp.listener.IDynamicData;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;
import com.zzu.ehome.ehomefordoctor.mvp.model.IDynamicDataImpl;
import com.zzu.ehome.ehomefordoctor.mvp.view.IDynamicView;

/**
 * Created by Mersens on 2016/10/25.
 */

public class DynamicPresenter {
    private IDynamicData mIDynamicData;
    private IDynamicView mIDynamicView;
    public DynamicPresenter(IDynamicView iDynamicView){
        this.mIDynamicView=iDynamicView;
        mIDynamicData=new IDynamicDataImpl();
    }
    public  void getDynamicData(){
        mIDynamicData.getDynamicData(mIDynamicView.getUserid(), new OnCommonResultListener() {
            @Override
            public <T> void onSuccess(T t) {
                mIDynamicView.onSuccess(t);

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onError(Exception e) {
                mIDynamicView.onError(e+"");
            }
            @Override
            public void onFinish() {

            }
        });
    }
}
