package com.zzu.ehome.ehomefordoctor.mvp.model;

import com.zzu.ehome.ehomefordoctor.entity.TreatmentInquirywWithPage;
import com.zzu.ehome.ehomefordoctor.entity.TreatmentInquirywWithPageDate;
import com.zzu.ehome.ehomefordoctor.mvp.listener.IJzjlData;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;
import com.zzu.ehome.ehomefordoctor.service.ServiceStore;
import com.zzu.ehome.ehomefordoctor.utils.JsonTools;
import com.zzu.ehome.ehomefordoctor.utils.Node;
import com.zzu.ehome.ehomefordoctor.utils.RequestManager;

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

public class IJzjlDataImpl implements IJzjlData {
    private RequestManager manager;

    public IJzjlDataImpl() {
        manager = RequestManager.getInstance();
    }

    @Override
    public void getJzjlInfo(String userid, String pagesize, String page, final OnCommonResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("UserID", userid);
        map.put("PageSize", pagesize);
        map.put("PageIndex", page);
        String result = Node.getResult("TreatmentInquirywWithPage", map);
        final ServiceStore service = manager.create(ServiceStore.class);
        Call<ResponseBody> call = service.getJzjlData(result);
        manager.execute(call, new RequestManager.RequestCallBack() {
            @Override
            public void onSueecss(String msg) {
                try {
                    JSONObject mySO = new JSONObject(msg);
                    org.json.JSONArray array = mySO
                            .getJSONArray("TreatmentInquirywWithPage");
                    if (!array.getJSONObject(0).has("MessageCode")) {
                        TreatmentInquirywWithPageDate date = JsonTools.getData(mySO.toString(), TreatmentInquirywWithPageDate.class);
                        List<TreatmentInquirywWithPage> list = date.getData();
                        listener.onSuccess(list);
                    } else {
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
