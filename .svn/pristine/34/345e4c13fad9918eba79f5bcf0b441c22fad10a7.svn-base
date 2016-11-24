package com.zzu.ehome.ehomefordoctor.mvp.presenter;

import com.zzu.ehome.ehomefordoctor.mvp.listener.IBaseData;
import com.zzu.ehome.ehomefordoctor.mvp.listener.IUserDate;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;
import com.zzu.ehome.ehomefordoctor.mvp.model.IBaseDataImpl;
import com.zzu.ehome.ehomefordoctor.mvp.model.IUserDateImpl;
import com.zzu.ehome.ehomefordoctor.mvp.view.BaseDataView;
import com.zzu.ehome.ehomefordoctor.mvp.view.IUserDateView;

/**
 * Created by Administrator on 2016/10/26.
 */

public class UserDataPresenter {
    private IUserDate mIUserDate;
    private IUserDateView mIUserDateView;
    public UserDataPresenter(IUserDateView userDataView){
        this.mIUserDateView=userDataView;
        mIUserDate=new IUserDateImpl();
    }
    public void getUserData(){
        mIUserDate.getUserData(mIUserDateView.getUserId(), new OnCommonResultListener() {
            @Override
            public <T> void onSuccess(T t) {
                mIUserDateView.onSuccess(t);
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
