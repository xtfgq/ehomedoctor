package com.zzu.ehome.ehomefordoctor.mvp.model;

import android.util.Log;

import com.zzu.ehome.ehomefordoctor.mvp.listener.IBaseData;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;
import com.zzu.ehome.ehomefordoctor.service.ServiceStore;
import com.zzu.ehome.ehomefordoctor.utils.Node;
import com.zzu.ehome.ehomefordoctor.utils.RequestManager;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by Mersens on 2016/10/25.
 */

public class IBaseDataImpl implements IBaseData {
    public RequestManager manager;
    public IBaseDataImpl(){
        manager=RequestManager.getInstance();
    }

    @Override
    public void getBaseData(String userid,final OnCommonResultListener listener) {
        Map<String,String> map=new HashMap<>();
        map.put("UserID",userid);
        String result= Node.getResult("BaseDataInquiry",map);
        final ServiceStore service= manager.create(ServiceStore.class);
        Call<ResponseBody> call=service.getBaseData(result);
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
}
