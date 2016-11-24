package com.zzu.ehome.ehomefordoctor.mvp.model;

import com.zzu.ehome.ehomefordoctor.app.CommonApi;
import com.zzu.ehome.ehomefordoctor.entity.CholDate;
import com.zzu.ehome.ehomefordoctor.entity.CholRes;
import com.zzu.ehome.ehomefordoctor.entity.ResultBean;
import com.zzu.ehome.ehomefordoctor.mvp.listener.IGetCodeData;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;
import com.zzu.ehome.ehomefordoctor.service.ServiceStore;
import com.zzu.ehome.ehomefordoctor.utils.JsonTools;
import com.zzu.ehome.ehomefordoctor.utils.Node;
import com.zzu.ehome.ehomefordoctor.utils.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by Administrator on 2016/10/28.
 */

public class IGetCodeDataImpl implements IGetCodeData{
    private RequestManager manager;

    public IGetCodeDataImpl() {
        manager = RequestManager.getInstance();
    }

    @Override
    public void getCode(String UserMobile,String Flag,final OnCommonResultListener listener) {

        Map<String, String> map = new HashMap<>();
        map.put("UserMobile", UserMobile);
        map.put("Flag", Flag);
        String result = Node.getResult("SendAuthCodeForDoctor", map);
        final ServiceStore service = manager.create(ServiceStore.class);
        Call<ResponseBody> callGetCoke = service.getCoke(result);
        manager.execute(callGetCoke, new RequestManager.RequestCallBack()  {
            @Override
            public void onSueecss(String msg) {
                try {
                    listener.onSuccess(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(String msg) {

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }
        });
    }
}
