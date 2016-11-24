package com.zzu.ehome.ehomefordoctor.mvp.model;

import com.zzu.ehome.ehomefordoctor.entity.BloodSuggarBean;
import com.zzu.ehome.ehomefordoctor.entity.BloodSuggarDate;
import com.zzu.ehome.ehomefordoctor.entity.ResultBean;
import com.zzu.ehome.ehomefordoctor.mvp.listener.ISuggarData;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;
import com.zzu.ehome.ehomefordoctor.service.ServiceStore;
import com.zzu.ehome.ehomefordoctor.utils.JsonTools;
import com.zzu.ehome.ehomefordoctor.utils.Node;
import com.zzu.ehome.ehomefordoctor.utils.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by Administrator on 2016/10/26.
 */

public class ISuggarDataImpl implements ISuggarData{
    private RequestManager manager;

    public ISuggarDataImpl() {
        manager = RequestManager.getInstance();
    }


    @Override
    public void getSuggarData(String CardNO, String UserID, String StartTime, String EndTime, String type,final OnCommonResultListener listener) {
        Map<String,String> map=new HashMap<>();
        map.put("CardNO",CardNO);
        map.put("UserID",UserID);
        map.put("StartTime",StartTime);
        map.put("EndTime",EndTime);
        map.put("Type",type);
        String result= Node.getResult("BloodSugarInquiry",map);
        final ServiceStore service= manager.create(ServiceStore.class);
        Call<ResponseBody> call=service.getSuggarDate(result);
        manager.execute(call, new RequestManager.RequestCallBack() {
            @Override
            public void onSueecss(String msg) {
                try {
                    ResultBean resultBean=new ResultBean();
                    BloodSuggarBean bean=new BloodSuggarBean();
                    JSONObject mySO = new JSONObject(msg);
                    List<BloodSuggarBean> list=new ArrayList<BloodSuggarBean>();

                    org.json.JSONArray array = mySO
                            .getJSONArray("BloodSugarInquiry");
                    if(array.getJSONObject(0).has("MessageCode")){
                        resultBean.setMessageCode(array.getJSONObject(0).getString("MessageCode").toString());
                        resultBean.setMessageContent(array.getJSONObject(0).getString("MessageContent").toString());
                        bean.setmResultBean(resultBean);
                        list.add(bean);
                    }else{
                        BloodSuggarDate date = JsonTools.getData(msg, BloodSuggarDate.class);
                       list = date.getData();

                    }
                    listener.onSuccess(list);

                } catch (JSONException e) {
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
