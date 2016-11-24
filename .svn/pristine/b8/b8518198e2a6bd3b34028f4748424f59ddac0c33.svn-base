package com.zzu.ehome.ehomefordoctor.mvp.model;

import com.zzu.ehome.ehomefordoctor.app.App;
import com.zzu.ehome.ehomefordoctor.mvp.listener.IOffLineInfo;
import com.zzu.ehome.ehomefordoctor.mvp.listener.IOnLineInfo;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnOffLineLister;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnOnLineLister;
import com.zzu.ehome.ehomefordoctor.service.ServiceStore;
import com.zzu.ehome.ehomefordoctor.utils.Node;
import com.zzu.ehome.ehomefordoctor.utils.RequestManager;
import com.zzu.ehome.ehomefordoctor.utils.SharePreferenceUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by Administrator on 2016/10/15.
 */

public class IHzOffLineImpl implements IOffLineInfo {
    public RequestManager manager;

    private String DoctorID;
    public IHzOffLineImpl() {
        manager = RequestManager.getInstance();
        DoctorID= SharePreferenceUtil.getInstance(App.getInstance()).getUserId();
    }


    @Override
    public void PostOffline(final OnOffLineLister listener) {
        Map<String, String> map = new HashMap<>();
        map.put("DoctorID",DoctorID);
        String result = Node.getResult("MSDoctorEndOLTime", map);
        final ServiceStore service = manager.create(ServiceStore.class);
        Call<ResponseBody> call = service.postOffline(result);
        manager.execute(call, new RequestManager.RequestCallBack() {
            @Override
            public void onSueecss(String msg) {
                listener.OnSuccess(msg);
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
