package com.zzu.ehome.ehomefordoctor.mvp.presenter;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;
import com.zzu.ehome.ehomefordoctor.mvp.model.IIMeidicalReportInquiryImpl;
import com.zzu.ehome.ehomefordoctor.mvp.model.IMedicalExaminationDesImpl;
import com.zzu.ehome.ehomefordoctor.mvp.view.IMeidicalReportDesView;
import com.zzu.ehome.ehomefordoctor.mvp.view.IMeidicalReportInquiryView;

/**
 * Created by Administrator on 2016/12/7.
 */

public class MeidicalReportDesPresenter {
    private IMeidicalReportDesView mIMeidicalReportDesView;
    private IMedicalExaminationDesImpl mIMedicalExaminationDesImpl;


    public MeidicalReportDesPresenter(IMeidicalReportDesView mIMeidicalReportDesView){
        this.mIMeidicalReportDesView=mIMeidicalReportDesView;
        this.mIMedicalExaminationDesImpl=new IMedicalExaminationDesImpl();
    }


    public void doMeidicalDes(){
        mIMedicalExaminationDesImpl.MeidicalReportDes(mIMeidicalReportDesView.getUserId(), mIMeidicalReportDesView.getId(), new OnCommonResultListener() {

            @Override
            public <T> void onSuccess(T t) {
                mIMeidicalReportDesView.onSuccess(t);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onError(Exception e) {
                mIMeidicalReportDesView.onSuccess(new Exception(e));
            }

            @Override
            public void onFinish() {

            }
        });

    }
}
