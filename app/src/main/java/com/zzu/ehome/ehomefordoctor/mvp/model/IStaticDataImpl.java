package com.zzu.ehome.ehomefordoctor.mvp.model;

import android.util.Log;

import com.zzu.ehome.ehomefordoctor.entity.StaticBean;
import com.zzu.ehome.ehomefordoctor.entity.StaticDate;
import com.zzu.ehome.ehomefordoctor.mvp.listener.IStaticData;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;
import com.zzu.ehome.ehomefordoctor.service.ServiceStore;
import com.zzu.ehome.ehomefordoctor.utils.ECGNode;
import com.zzu.ehome.ehomefordoctor.utils.JsonTools;
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
 * Created by Mersens on 2016/10/26.
 */

public class IStaticDataImpl implements IStaticData {
    private RequestManager manager;
    public IStaticDataImpl(){
        manager=RequestManager.getInstance();
    }
    @Override
    public void getStaticData(String userid, final OnCommonResultListener listener) {
        Map<String,String> map=new HashMap<>();
        map.put("UserID",userid);
        map.put("StartDate","");
        map.put("EndDate","");
        String result= ECGNode.getResult("BJResultInquiry",map);
        final ServiceStore service= manager.create(ServiceStore.class);
        Call<ResponseBody> call=service.getStaticData(result);
        manager.execute(call, new RequestManager.RequestCallBack() {
            @Override
            public void onSueecss(String msg) {
                Log.e("tag",msg);
                try {
                    JSONObject mySO = new JSONObject(msg);
                    JSONArray array = mySO.getJSONArray("Result");
                    JSONArray arrayContent = array.getJSONObject(0).getJSONArray("MessageContent");
                    int length = arrayContent.length();

                    if(length!=0){
                        JSONObject s = array.getJSONObject(0);
                        StaticDate date = JsonTools.getData(s.toString(), StaticDate.class);
                        List<StaticBean> list = date.getData();
                        listener.onSuccess(list);
                    }else{
                        listener.onSuccess(null);
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
