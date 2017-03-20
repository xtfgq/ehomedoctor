package com.zzu.ehome.ehomefordoctor.mvp.model;

import com.zzu.ehome.ehomefordoctor.entity.BloodSuggarBean;
import com.zzu.ehome.ehomefordoctor.entity.BloodSuggarDa;
import com.zzu.ehome.ehomefordoctor.entity.BloodSuggarDate;
import com.zzu.ehome.ehomefordoctor.entity.BloodSuggarRes;
import com.zzu.ehome.ehomefordoctor.entity.ResultBean;
import com.zzu.ehome.ehomefordoctor.mvp.listener.ISuggarHistory;
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
 * Created by Administrator on 2016/10/27.
 */

public class ISuggarHistoryImpl implements ISuggarHistory{
    private RequestManager manager;

    public ISuggarHistoryImpl() {
        manager = RequestManager.getInstance();
    }

    @Override
    public void getHistory(String usrid, String CardNo, String pagesize, String pageindex, String type, final OnCommonResultListener listener) {
        Map<String,String> map=new HashMap<>();
        map.put("CardNO",CardNo);
        map.put("UserID",usrid);
        map.put("PageSize",pagesize);
        map.put("PageIndex",pageindex);
        map.put("Type",type);
        map.put("Fromto", "01");
        String result= Node.getResult("HealthDataInquiryWithPage",map);
        final ServiceStore service= manager.create(ServiceStore.class);
        Call<ResponseBody> call=service.getSuggarHistory(result);
        manager.execute(call, new RequestManager.RequestCallBack() {
            @Override
            public void onSueecss(String msg) {
                try {
                    ResultBean resultBean=new ResultBean();
                    BloodSuggarRes bean=new BloodSuggarRes();
                    JSONObject mySO = new JSONObject(msg);

                    List<BloodSuggarRes> list=new ArrayList<BloodSuggarRes>();
                    org.json.JSONArray array = mySO
                            .getJSONArray("HealthDataInquiryWithPage");
                    if(array.getJSONObject(0).has("MessageCode")){
                        resultBean.setMessageCode(array.getJSONObject(0).getString("MessageCode").toString());
                        resultBean.setMessageContent(array.getJSONObject(0).getString("MessageContent").toString());
                        bean.setmResultBean(resultBean);
                        list.add(bean);
                    }else{
                        BloodSuggarDa date = JsonTools.getData(msg, BloodSuggarDa.class);
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
