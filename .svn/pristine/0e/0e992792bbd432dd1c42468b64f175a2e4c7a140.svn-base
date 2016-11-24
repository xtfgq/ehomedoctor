package com.zzu.ehome.ehomefordoctor.mvp.model;


import android.content.Context;

import com.zzu.ehome.ehomefordoctor.app.App;
import com.zzu.ehome.ehomefordoctor.mvp.listener.IMSDoctorFindPassData;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;

import com.zzu.ehome.ehomefordoctor.service.ServiceStore;

import com.zzu.ehome.ehomefordoctor.utils.Node;
import com.zzu.ehome.ehomefordoctor.utils.RequestManager;
import com.zzu.ehome.ehomefordoctor.utils.ToastUtils;
import com.zzu.ehome.ehomefordoctor.view.DialogTips;


import java.util.HashMap;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by Administrator on 2016/10/28.
 */

public class IMSDoctorFindPassDataImpl implements IMSDoctorFindPassData{
    private RequestManager manager;
    public IMSDoctorFindPassDataImpl(){
        manager=RequestManager.getInstance();
    }
    @Override
    public void findPsd(String DoctorMobile, String Password, final OnCommonResultListener listener) {
        Map<String,String> map=new HashMap<>();
        map.put("DoctorMobile",DoctorMobile);
        map.put("Password",Password);

        String result= Node.getResult("MSDoctorFindPass",map);

        final ServiceStore service= manager.create(ServiceStore.class);
        Call<ResponseBody> call=service.findPsd(result);
        manager.execute(call, new RequestManager.RequestCallBack() {
            @Override
            public void onSueecss(String msg) {
                   listener.onSuccess(msg);
            }

            @Override
            public void onError(String msg) {
                listener.onError(new Exception(msg));

            }

            @Override
            public void onStart() {
                listener.onStart();

            }

            @Override
            public void onFinish() {
                listener.onFinish();

            }
        });

    }
    private void showDialog(String message, Context mContext) {

        DialogTips dialog = new DialogTips(mContext, message, "确定");

        dialog.show();
        dialog = null;

    }
}
