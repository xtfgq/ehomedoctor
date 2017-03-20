package com.zzu.ehome.ehomefordoctor.mvp.model;

import android.text.TextUtils;

import com.zzu.ehome.ehomefordoctor.entity.AdviceBean;
import com.zzu.ehome.ehomefordoctor.entity.BloodRoutine;
import com.zzu.ehome.ehomefordoctor.mvp.listener.InspectionReportDetial;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;
import com.zzu.ehome.ehomefordoctor.service.ServiceStore;
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
 * Created by Mersens on 2016/10/29.
 */

public class InspectionReportDetialImpl implements InspectionReportDetial {
    private RequestManager manager;
    public InspectionReportDetialImpl(){
        manager=RequestManager.getInstance();

    }
    @Override
    public void getInspectionReportDetial(String type, String userno, String id, final OnCommonResultListener listener) {
        Map<String,String> map=new HashMap<>();
        map.put("OCRType",type);
        map.put("CardNO",userno);
        map.put("RecordID",id);
        map.put("Fromto","02");
        String result= Node.getResult("BloodRoutineInquiry",map);
        final ServiceStore service= manager.create(ServiceStore.class);
        Call<ResponseBody> call=service.getInspectionReportDetial(result);
        final List<BloodRoutine> mList=new ArrayList<BloodRoutine>();
        final List<AdviceBean> mList2=new ArrayList<AdviceBean>();
        manager.execute(call, new RequestManager.RequestCallBack() {
            @Override
            public void onSueecss(String msg) {
                try {
                    JSONObject mySO =new JSONObject(msg);
                    org.json.JSONArray array = mySO
                            .getJSONArray("BloodRoutineInquiry");
                    int code = Integer.valueOf(array.getJSONObject(0).getString("MessageCode"));
                    if(code==0){
                        org.json.JSONArray arraySub =
                                array.getJSONObject(0).getJSONArray("ResultContent");
                        HashMap<String, Object> map=new  HashMap<String, Object>();
                        java.text.DecimalFormat df = new java.text.DecimalFormat("######0.00");
                        for (int i = 0; i < arraySub.length(); i++) {
                            BloodRoutine br = new BloodRoutine();
                            br.setCHK_ItemName_Z(arraySub.getJSONObject(i).getString("CHK_ItemName_Z"));
                            br.setItemRange(arraySub.getJSONObject(i).getString("ItemRange"));
                            br.setItemUnit(arraySub.getJSONObject(i).getString("ItemUnit"));
                            br.setMonitorTime(arraySub.getJSONObject(i).getString("MonitorTime"));
                            br.setItemValue(df.format(Double.valueOf(arraySub.getJSONObject(i).getString("ItemValue"))));

                            mList.add(br);
                            if (!TextUtils.isEmpty(arraySub.getJSONObject(i).getString("Advice"))) {
                                AdviceBean ab = new AdviceBean();
                                ab.setAdvice(arraySub.getJSONObject(i).getString("Advice"));
                                ab.setDescription(arraySub.getJSONObject(i).getString("Description"));
                                mList2.add(ab);
                            }
                        }
                        map.put("br",mList);
                        if(mList2.size()>0) {
                            map.put("advice", mList2);
                        }
                        listener.onSuccess(map);
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
