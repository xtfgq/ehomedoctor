package com.zzu.ehome.ehomefordoctor.mvp.model;

import android.text.TextUtils;
import android.util.Log;

import com.zzu.ehome.ehomefordoctor.app.App;
import com.zzu.ehome.ehomefordoctor.app.Constans;
import com.zzu.ehome.ehomefordoctor.entity.ResultBean;
import com.zzu.ehome.ehomefordoctor.entity.UsersBySignDoctor;
import com.zzu.ehome.ehomefordoctor.mvp.listener.IHzInfo;
import com.zzu.ehome.ehomefordoctor.mvp.listener.IOnLineInfo;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnHzInfoListener;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnOnLineLister;
import com.zzu.ehome.ehomefordoctor.service.ServiceStore;
import com.zzu.ehome.ehomefordoctor.utils.CharacterParser;
import com.zzu.ehome.ehomefordoctor.utils.Node;
import com.zzu.ehome.ehomefordoctor.utils.PinyinComparator;
import com.zzu.ehome.ehomefordoctor.utils.RequestManager;
import com.zzu.ehome.ehomefordoctor.utils.SharePreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by Administrator on 2016/10/15.
 */

public class IHzOnLineImpl implements IOnLineInfo {
    public RequestManager manager;

    private String DoctorID;
    public IHzOnLineImpl() {
        manager = RequestManager.getInstance();
        DoctorID= SharePreferenceUtil.getInstance(App.getInstance()).getUserId();
    }


    @Override
    public void PostOnline(final OnOnLineLister listener) {
        Map<String, String> map = new HashMap<>();
        map.put("DoctorID",DoctorID);
        String result = Node.getResult("MSDoctorStartOLTime", map);
        final ServiceStore service = manager.create(ServiceStore.class);
        Call<ResponseBody> call = service.postOnline(result);
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
