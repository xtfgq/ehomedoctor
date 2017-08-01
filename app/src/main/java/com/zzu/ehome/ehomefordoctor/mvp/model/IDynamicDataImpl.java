package com.zzu.ehome.ehomefordoctor.mvp.model;

import android.util.Log;

import com.zzu.ehome.ehomefordoctor.entity.ECGDate;
import com.zzu.ehome.ehomefordoctor.entity.ECGDynamicBean;
import com.zzu.ehome.ehomefordoctor.entity.StaticBean;
import com.zzu.ehome.ehomefordoctor.entity.StaticDate;
import com.zzu.ehome.ehomefordoctor.mvp.listener.IDynamicData;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;
import com.zzu.ehome.ehomefordoctor.service.ServiceStore;
import com.zzu.ehome.ehomefordoctor.utils.ECGNode;
import com.zzu.ehome.ehomefordoctor.utils.JsonTools;
import com.zzu.ehome.ehomefordoctor.utils.Node;
import com.zzu.ehome.ehomefordoctor.utils.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by Mersens on 2016/10/25.
 */

public class IDynamicDataImpl implements IDynamicData {
    private RequestManager manager;

    public IDynamicDataImpl() {
        manager = RequestManager.getInstance();
    }


    @Override
    public void getDynamicData(String userid,String cardno, final OnCommonResultListener listener) {
        Map<String,String> map=new HashMap<>();
        map.put("UserID",userid);
        map.put("CardNO",cardno);
        map.put("StartDate","");
        map.put("EndDate","");
        String result= Node.getResult("HolterPDFInquiry",map);
        final ServiceStore service= manager.create(ServiceStore.class);
        Call<ResponseBody> call=service.getDynamicData(result);
        manager.execute(call, new RequestManager.RequestCallBack() {
            @Override
            public void onSueecss(String msg) {
                try {
                    Log.e("MSG",msg);
                    JSONObject mySO = new JSONObject(msg);
                    JSONArray array = mySO.getJSONArray("GetElectrocardio");

                    if (array.getJSONObject(0).has("MessageCode")) {
                        listener.onSuccess(null);
                    }else{
                        StaticDate date = JsonTools.getData(msg, StaticDate.class);
                        List<StaticBean> list = date.getData();
                        listener.onSuccess(list);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onError(e);
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
