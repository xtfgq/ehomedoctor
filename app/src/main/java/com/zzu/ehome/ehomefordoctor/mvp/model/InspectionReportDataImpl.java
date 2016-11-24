package com.zzu.ehome.ehomefordoctor.mvp.model;

import com.zzu.ehome.ehomefordoctor.entity.ResultContent;
import com.zzu.ehome.ehomefordoctor.mvp.listener.InspectionReportData;
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

public class InspectionReportDataImpl implements InspectionReportData {
    private RequestManager manager;

    public InspectionReportDataImpl() {
        manager = RequestManager.getInstance();
    }

    @Override
    public void getInspectionReport(String userno, String pagesize, String page, final OnCommonResultListener listener) {
        Map<String,String> map=new HashMap<>();
        map.put("CardNO",userno);
        map.put("PageIndex",page);
        map.put("PageSize",pagesize);
        String result= Node.getResult("OCRRecordInquiry",map);
        final ServiceStore service= manager.create(ServiceStore.class);
        Call<ResponseBody> call=service.getInspectionReport(result);
        final List<ResultContent> mList=new ArrayList<ResultContent>();
        manager.execute(call, new RequestManager.RequestCallBack() {
            @Override
            public void onSueecss(String msg) {
                try {
                    JSONObject mySO=new JSONObject(msg);
                    org.json.JSONArray array = mySO
                            .getJSONArray("OCRRecordInquiry");
                    int code = Integer.valueOf(array.getJSONObject(0).getString("MessageCode"));
                    if(code == 0){
                        org.json.JSONArray arraySub =
                                array.getJSONObject(0).getJSONArray("ResultContent");
                        for (int i = 0; i < arraySub.length(); i++) {
                            ResultContent rc = new ResultContent();
                            rc.setCreatedDate(arraySub.getJSONObject(i).getString("CreatedDate"));
                            rc.setID(arraySub.getJSONObject(i).getString("ID"));
                            rc.setOCRType(arraySub.getJSONObject(i).getString("OCRType"));
                            rc.setOCRTypeName(arraySub.getJSONObject(i).getString("OCRTypeName"));
                            rc.setRownumber(arraySub.getJSONObject(i).getString("rownumber"));
                            rc.setFromTo(arraySub.getJSONObject(i).getString("Fromto"));
                            mList.add(rc);
                        }
                        listener.onSuccess(mList);
                    }else{
                        listener. onSuccess(null);
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
