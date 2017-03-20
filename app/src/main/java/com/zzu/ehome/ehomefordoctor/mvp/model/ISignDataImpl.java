package com.zzu.ehome.ehomefordoctor.mvp.model;


import com.zzu.ehome.ehomefordoctor.entity.BloodSuggarBean;
import com.zzu.ehome.ehomefordoctor.entity.BloodSuggarDate;
import com.zzu.ehome.ehomefordoctor.entity.SignBean;
import com.zzu.ehome.ehomefordoctor.entity.SignData;
import com.zzu.ehome.ehomefordoctor.mvp.listener.ISignInquiry;

import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;
import com.zzu.ehome.ehomefordoctor.service.ServiceStore;

import com.zzu.ehome.ehomefordoctor.utils.JsonTools;
import com.zzu.ehome.ehomefordoctor.utils.Node;
import com.zzu.ehome.ehomefordoctor.utils.RequestManager;


import org.json.JSONException;


import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;

import static android.R.id.list;


/**
 * Created by Administrator on 2016/10/27.
 */

public class ISignDataImpl implements ISignInquiry{
    private RequestManager manager;

    public ISignDataImpl() {
        manager = RequestManager.getInstance();
    }

    @Override
    public void getSignData(String doctorid, String cardno, final OnCommonResultListener listener) {
        Map<String,String> map=new HashMap<>();
        map.put("DoctorID",doctorid);
        map.put("CardNO",cardno);
        String result= Node.getResult("SignInquiry",map);
        final ServiceStore service= manager.create(ServiceStore.class);
        Call<ResponseBody> call=service.getSignData(result);
        manager.execute(call, new RequestManager.RequestCallBack() {
            @Override
            public void onSueecss(String msg) {
                try {
                    List<SignBean> list=new ArrayList<SignBean>();
                    SignData date = JsonTools.getData(msg, SignData.class);
                    list = date.getData();
                    listener.onSuccess(list);

                } catch (Exception e) {
                    e.printStackTrace();
                }
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
