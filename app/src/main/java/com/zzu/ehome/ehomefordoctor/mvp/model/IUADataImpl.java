package com.zzu.ehome.ehomefordoctor.mvp.model;

import com.zzu.ehome.ehomefordoctor.entity.BloodSuggarBean;
import com.zzu.ehome.ehomefordoctor.entity.BloodSuggarDate;
import com.zzu.ehome.ehomefordoctor.entity.LithicAcidDate;
import com.zzu.ehome.ehomefordoctor.entity.LithicAcidRes;
import com.zzu.ehome.ehomefordoctor.entity.ResultBean;
import com.zzu.ehome.ehomefordoctor.mvp.listener.IUAData;
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

import static android.R.id.list;

/**
 * Created by Administrator on 2016/10/27.
 */

public class IUADataImpl implements IUAData{
    private RequestManager manager;

    public IUADataImpl() {
        manager = RequestManager.getInstance();
    }

    @Override
    public void getUaData(String CardNO, String StartTime, String EndTime, String Type, String Top, final OnCommonResultListener listener) {
        Map<String,String> map=new HashMap<>();
        map.put("CardNO",CardNO);
        map.put("StartTime",StartTime);
        map.put("EndTime",EndTime);
        map.put("Type",Type);
        map.put("Top",Top);
        String result= Node.getResult("LithicAcidInquiry",map);
        final ServiceStore service= manager.create(ServiceStore.class);
        Call<ResponseBody> call=service.getUaData(result);
        manager.execute(call, new RequestManager.RequestCallBack() {
            @Override
            public void onSueecss(String msg) {
                try {
                    ResultBean resultBean=new ResultBean();
                    List<LithicAcidRes> list = new ArrayList<LithicAcidRes>();
                    LithicAcidRes bean=new LithicAcidRes();
                    JSONObject mySO = new JSONObject(msg);
                    JSONArray array = mySO
                            .getJSONArray("LithicAcidInquiry");
                    if(array.getJSONObject(0).has("MessageCode")){
                        resultBean.setMessageCode(array.getJSONObject(0).getString("MessageCode").toString());
                        resultBean.setMessageContent(array.getJSONObject(0).getString("MessageContent").toString());
                        bean.setmResultBean(resultBean);
                        list.add(bean);
                    }else{
                        LithicAcidDate date = JsonTools.getData(msg, LithicAcidDate.class);
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
