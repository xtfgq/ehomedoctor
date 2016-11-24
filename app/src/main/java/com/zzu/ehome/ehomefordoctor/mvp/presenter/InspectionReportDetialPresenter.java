package com.zzu.ehome.ehomefordoctor.mvp.presenter;

import com.zzu.ehome.ehomefordoctor.mvp.listener.InspectionReportDetial;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;
import com.zzu.ehome.ehomefordoctor.mvp.model.InspectionReportDetialImpl;
import com.zzu.ehome.ehomefordoctor.mvp.view.InspectionReportDetialView;

/**
 * Created by Mersens on 2016/10/29.
 */

public class InspectionReportDetialPresenter {
    private InspectionReportDetialView mInspectionReportDetialView;
    private InspectionReportDetial mInspectionReportDetial;

    public InspectionReportDetialPresenter(InspectionReportDetialView mInspectionReportDetialView){
        this.mInspectionReportDetialView=mInspectionReportDetialView;
        mInspectionReportDetial=new InspectionReportDetialImpl();
    }
    public void getInspectionReport(){
        mInspectionReportDetial.getInspectionReportDetial(mInspectionReportDetialView.getType(), mInspectionReportDetialView.getUserNum(), mInspectionReportDetialView.getId(), new OnCommonResultListener() {
            @Override
            public <T> void onSuccess(T t) {
                mInspectionReportDetialView.onSuccess(t);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onError(Exception e) {
                mInspectionReportDetialView.onError();

            }

            @Override
            public void onFinish() {

            }
        });

    }





}
