package com.zzu.ehome.ehomefordoctor.mvp.model;

import com.zzu.ehome.ehomefordoctor.entity.ECGDate;
import com.zzu.ehome.ehomefordoctor.entity.ECGDynamicBean;
import com.zzu.ehome.ehomefordoctor.entity.LithicAcidDate;
import com.zzu.ehome.ehomefordoctor.entity.LithicAcidRes;
import com.zzu.ehome.ehomefordoctor.entity.ResultBean;
import com.zzu.ehome.ehomefordoctor.entity.UpInfoBean;
import com.zzu.ehome.ehomefordoctor.mvp.listener.IUpVersionData;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;
import com.zzu.ehome.ehomefordoctor.service.ServiceStore;
import com.zzu.ehome.ehomefordoctor.utils.ECGNode;
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
 * Created by Administrator on 2016/10/31.
 */

public class IUpVersionDataImpl implements IUpVersionData{
    private RequestManager manager;

    public IUpVersionDataImpl() {
        manager = RequestManager.getInstance();
    }

    @Override
    public void getUp(final OnCommonResultListener listener) {
        Map<String,String> map=new HashMap<>();

        String result= Node.getResult("VersionInquiryForDoctor",map);
        final ServiceStore service= manager.create(ServiceStore.class);
        Call<ResponseBody> call=service.getUpdata(result);
        manager.execute(call, new RequestManager.RequestCallBack() {
            @Override
            public void onSueecss(String msg) {
                try {
                    ResultBean resultBean=new ResultBean();
                    UpInfoBean mUpInfoBean=new UpInfoBean();

                    JSONObject mySO = new JSONObject(msg);
                    JSONArray array = mySO
                            .getJSONArray("VersionInquiryForDoctor");
                    if(array.getJSONObject(0).has("MessageCode")){
                        resultBean.setMessageCode(array.getJSONObject(0).getString("MessageCode").toString());
                        resultBean.setMessageContent(array.getJSONObject(0).getString("MessageContent").toString());
                        mUpInfoBean.setmResultBean(resultBean);

                    }else{
                        mUpInfoBean.setVersionID(array
                                .getJSONObject(0).getString(
                                        "VersionID"));
                        mUpInfoBean.setVersionFlag(array
                                .getJSONObject(0).getString(
                                        "VersionFlag"));
                        mUpInfoBean.setVersionLog(array
                                .getJSONObject(0).getString(
                                        "VersionLog"));

                    }
                    listener.onSuccess(mUpInfoBean);

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
