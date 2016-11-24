package com.zzu.ehome.ehomefordoctor.mvp.presenter;

import com.zzu.ehome.ehomefordoctor.entity.UsersBySignDoctor;
import com.zzu.ehome.ehomefordoctor.mvp.listener.IHzInfo;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnHzInfoListener;
import com.zzu.ehome.ehomefordoctor.mvp.model.IHzInfoImpl;
import com.zzu.ehome.ehomefordoctor.mvp.view.IHzInfoView;

import java.util.List;

/**
 * Created by Mersens on 2016/9/29.
 */

public class HzInfoPresenter {
    private IHzInfo mIHzInfo;
    private IHzInfoView mIHzInfoView;
    public HzInfoPresenter(IHzInfoView mIHzInfoView){
        this.mIHzInfoView=mIHzInfoView;
        mIHzInfo=new IHzInfoImpl();
    }

    public void doGetInfo(){
        mIHzInfo.getInfo(new OnHzInfoListener() {
            @Override
            public void OnSuccess(List<UsersBySignDoctor> list) {
                mIHzInfoView.onSuccess(list);
            }

            @Override
            public void onStart() {
                mIHzInfoView.showPro();
            }

            @Override
            public void onError(Exception e) {
                mIHzInfoView.onErroe(e);
            }

            @Override
            public void onFinish() {
                mIHzInfoView.hidePro();

            }
        });

    }
}
