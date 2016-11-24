package com.zzu.ehome.ehomefordoctor.mvp.presenter;

import com.zzu.ehome.ehomefordoctor.entity.DoctorBean;
import com.zzu.ehome.ehomefordoctor.mvp.listener.ILogin;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnLoginListener;
import com.zzu.ehome.ehomefordoctor.mvp.model.ILoginImpl;
import com.zzu.ehome.ehomefordoctor.mvp.view.ILoginView;


/**
 * Created by Mersens on 2016/9/18.
 */
public class LoginPresenter {
    private ILoginView mILoginView;
    private ILogin mILogin;


    public LoginPresenter(ILoginView mILoginView){
        this.mILoginView=mILoginView;
        this.mILogin=new ILoginImpl();
    }


    public void doLogin(){
        mILogin.login(mILoginView.getUserName(),mILoginView.getPsd(), new OnLoginListener() {
            @Override
            public void onStart() {
                mILoginView.showPro();
            }

            @Override
            public void onSuccess(DoctorBean DoctorBean) {
                mILoginView.onSuccess(DoctorBean);

            }

            @Override
            public void onError(Exception e) {
                mILoginView.onErroe(e);
                mILoginView.hidePro();

            }

            @Override
            public void onFinish() {
                mILoginView.hidePro();
            }
        });

    }

}
