package com.zzu.ehome.ehomefordoctor.mvp.model;

import android.util.Log;

import com.zzu.ehome.ehomefordoctor.entity.ECGDate;
import com.zzu.ehome.ehomefordoctor.entity.ECGDynamicBean;
import com.zzu.ehome.ehomefordoctor.entity.User;
import com.zzu.ehome.ehomefordoctor.entity.UserInfoDate;
import com.zzu.ehome.ehomefordoctor.mvp.listener.IDynamicData;
import com.zzu.ehome.ehomefordoctor.mvp.listener.IUserDate;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnCommonResultListener;
import com.zzu.ehome.ehomefordoctor.service.ServiceStore;
import com.zzu.ehome.ehomefordoctor.utils.ECGNode;
import com.zzu.ehome.ehomefordoctor.utils.JsonTools;
import com.zzu.ehome.ehomefordoctor.utils.Node;
import com.zzu.ehome.ehomefordoctor.utils.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by Administrator on 2016/10/26.
 */

public class IUserDateImpl implements IUserDate {
    private RequestManager manager;

    public IUserDateImpl() {
        manager = RequestManager.getInstance();
    }


    @Override
    public void getUserData(String userid, final OnCommonResultListener listener) {
        Map<String,String> map=new HashMap<>();
        map.put("UserID",userid);
        String result= Node.getResult("UserInquiry",map);
        final ServiceStore service= manager.create(ServiceStore.class);
        Call<ResponseBody> call=service.getUserDate(result);
        manager.execute(call, new RequestManager.RequestCallBack() {
            @Override
            public void onSueecss(String msg) {
                UserInfoDate date = JsonTools.getData(msg.toString(), UserInfoDate.class);
                List<User> list = date.getData();
                User user = list.get(0);
                listener.onSuccess(user);
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
