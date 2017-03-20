package com.zzu.ehome.ehomefordoctor.mvp.model;

import com.zzu.ehome.ehomefordoctor.entity.MedicalBean;
import com.zzu.ehome.ehomefordoctor.entity.MedicalDate;
import com.zzu.ehome.ehomefordoctor.entity.ResultBean;
import com.zzu.ehome.ehomefordoctor.mvp.listener.IMedicalExaminationDes;
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
 * Created by Administrator on 2016/12/7.
 */

public class IMedicalExaminationDesImpl implements IMedicalExaminationDes {
    public RequestManager manager;

    public IMedicalExaminationDesImpl(){
        manager= RequestManager.getInstance();
    }

    @Override
    public void MeidicalReportDes(String userid, String id, final OnCommonResultListener listener) {
        Map<String,String> map=new HashMap<>();
        map.put("UserID",userid);
        map.put("ID",id);
        String result= Node.getResult("MeidicalReportInquiry",map);
        final ServiceStore service= manager.create(ServiceStore.class);
        Call<ResponseBody> call=service.getMeidicalReport(result);
        manager.execute(call, new RequestManager.RequestCallBack() {
            @Override
            public void onSueecss(String msg) {
                try {
                    ResultBean resultBean=new ResultBean();
                    MedicalBean bean=new MedicalBean();
                    JSONObject mySO = new JSONObject(msg);

                    List<MedicalBean> list = new ArrayList<MedicalBean>();

                    org.json.JSONArray array = mySO
                            .getJSONArray("MeidicalReportInquiry");
                    if(array.getJSONObject(0).has("MessageCode")){
                        resultBean.setMessageCode(array.getJSONObject(0).getString("MessageCode").toString());
                        resultBean.setMessageContent(array.getJSONObject(0).getString("MessageContent").toString());
                        bean.setmResultBean(resultBean);
                        list.add(bean);
                    }else{

                        MedicalDate date = JsonTools.getData(msg, MedicalDate.class);
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
