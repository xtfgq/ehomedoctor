package com.zzu.ehome.ehomefordoctor.mvp.presenter;

import com.zzu.ehome.ehomefordoctor.mvp.listener.IJzjlData;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;
import com.zzu.ehome.ehomefordoctor.mvp.model.IJzjlDataImpl;
import com.zzu.ehome.ehomefordoctor.mvp.view.IJzjlView;

/**
 * Created by Mersens on 2016/10/26.
 */

public class JzjlPresenter {
    private IJzjlData mIJzjlData;
    private IJzjlView mIJzjlView;
    public JzjlPresenter(IJzjlView mIJzjlView){
        this.mIJzjlView=mIJzjlView;
        mIJzjlData=new IJzjlDataImpl();
    }

    public void getInfo(){
        mIJzjlData.getJzjlInfo(mIJzjlView.getUserid(), mIJzjlView.getPageSize(), mIJzjlView.getPage(), new OnCommonResultListener() {
            @Override
            public <T> void onSuccess(T t) {
                mIJzjlView.onSuccess(t);

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onError(Exception e) {
                mIJzjlView.onError();
            }

            @Override
            public void onFinish() {

            }
        });
    }


}
