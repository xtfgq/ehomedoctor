package com.zzu.ehome.ehomefordoctor.mvp.presenter;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;
import com.zzu.ehome.ehomefordoctor.mvp.model.IIMeidicalReportInquiryImpl;
import com.zzu.ehome.ehomefordoctor.mvp.view.IMeidicalReportInquiryView;

/**
 * Created by Administrator on 2016/12/7.
 */

public class MeidicalReportInquiryPresenter {
    private IMeidicalReportInquiryView mIMeidicalReportInquiryView;
    private IIMeidicalReportInquiryImpl mIIMeidicalReportInquiryImpl;


    public MeidicalReportInquiryPresenter(IMeidicalReportInquiryView mIMeidicalReportInquiryView){
        this.mIMeidicalReportInquiryView=mIMeidicalReportInquiryView;
        this.mIIMeidicalReportInquiryImpl=new IIMeidicalReportInquiryImpl();
    }


    public void doMeidicalReportInquiry(){
        mIIMeidicalReportInquiryImpl.MeidicalReportInquiry(mIMeidicalReportInquiryView.getUserId(),  new OnCommonResultListener() {

            @Override
            public <T> void onSuccess(T t) {
                mIMeidicalReportInquiryView.onSuccess(t);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onError(Exception e) {
                mIMeidicalReportInquiryView.onSuccess(new Exception(e));
            }

            @Override
            public void onFinish() {

            }
        });

    }
}
