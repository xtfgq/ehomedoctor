package com.zzu.ehome.ehomefordoctor.mvp.presenter;

import com.zzu.ehome.ehomefordoctor.mvp.listener.IBaseData;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;
import com.zzu.ehome.ehomefordoctor.mvp.model.IBaseDataImpl;
import com.zzu.ehome.ehomefordoctor.mvp.view.BaseDataView;

/**
 * Created by Mersens on 2016/10/25.
 */

public class BaseDataPresenter {
    private IBaseData mIBaseData;
    private BaseDataView mBaseDataView;
    public BaseDataPresenter(BaseDataView baseDataView){
        this.mBaseDataView=baseDataView;
        mIBaseData=new IBaseDataImpl();
    }
    public void getBaseData(){
        mIBaseData.getBaseData(mBaseDataView.getUserId(), new OnCommonResultListener() {
            @Override
            public <T> void onSuccess(T t) {
                mBaseDataView.onSuccess(t);
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
