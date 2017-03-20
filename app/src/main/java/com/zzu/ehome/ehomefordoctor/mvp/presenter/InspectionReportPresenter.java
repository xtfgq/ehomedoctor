package com.zzu.ehome.ehomefordoctor.mvp.presenter;

import com.zzu.ehome.ehomefordoctor.mvp.listener.InspectionReportData;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;
import com.zzu.ehome.ehomefordoctor.mvp.model.InspectionReportDataImpl;
import com.zzu.ehome.ehomefordoctor.mvp.view.InspectionReportView;

/**
 * Created by Mersens on 2016/10/29.
 */

public class InspectionReportPresenter {
    private InspectionReportView mInspectionReportView;
    private InspectionReportData mInspectionReportData;

    public InspectionReportPresenter(InspectionReportView mInspectionReportView){
        this.mInspectionReportView=mInspectionReportView;
        mInspectionReportData=new InspectionReportDataImpl();

    }


    public void getInspectionReport(){
        mInspectionReportData.getInspectionReport(mInspectionReportView.getUserNum(),mInspectionReportView.getType(), mInspectionReportView.getPageSize(), mInspectionReportView.getIndex(), new OnCommonResultListener() {
            @Override
            public <T> void onSuccess(T t) {
                mInspectionReportView.onSuccess(t);

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onError(Exception e) {
                mInspectionReportView.onError();
            }

            @Override
            public void onFinish() {

            }
        });
    }





}
