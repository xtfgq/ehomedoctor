package com.zzu.ehome.ehomefordoctor.mvp.model;

import android.text.TextUtils;
import android.util.Log;

import com.zzu.ehome.ehomefordoctor.app.App;
import com.zzu.ehome.ehomefordoctor.app.Constans;
import com.zzu.ehome.ehomefordoctor.entity.ResultBean;
import com.zzu.ehome.ehomefordoctor.entity.UsersBySignDoctor;
import com.zzu.ehome.ehomefordoctor.mvp.listener.IHzInfo;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnHzInfoListener;
import com.zzu.ehome.ehomefordoctor.service.ServiceStore;
import com.zzu.ehome.ehomefordoctor.utils.CharacterParser;
import com.zzu.ehome.ehomefordoctor.utils.Node;
import com.zzu.ehome.ehomefordoctor.utils.PinyinComparator;
import com.zzu.ehome.ehomefordoctor.utils.RequestManager;
import com.zzu.ehome.ehomefordoctor.utils.SharePreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by Mersens on 2016/9/29.
 */

public class IHzInfoImpl implements IHzInfo {
    public RequestManager manager;
    private final CharacterParser characterParser = CharacterParser.getInstance();
    ;
    private final PinyinComparator pinyinComparator = new PinyinComparator();
    ;
    private String DoctorID;
    public IHzInfoImpl() {
        manager = RequestManager.getInstance();
        DoctorID= SharePreferenceUtil.getInstance(App.getInstance()).getUserId();
    }

    @Override
    public void getInfo(final OnHzInfoListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("DoctorID",DoctorID);
        //具体参数
        String result = Node.getResult("MSUsersBySignDoctorInquiry", map);
        final ServiceStore service = manager.create(ServiceStore.class);
        Call<ResponseBody> call = service.getUsersBySign(result);
        manager.execute(call, new RequestManager.RequestCallBack() {
            @Override
            public void onSueecss(String msg) {
                Log.e("TAG",msg);
                final List<UsersBySignDoctor> mList = new ArrayList<UsersBySignDoctor>();
                final List<UsersBySignDoctor> list = new ArrayList<UsersBySignDoctor>();
                try {
                    JSONObject mySO = new JSONObject(msg);
                   org.json.JSONArray array = mySO
                            .getJSONArray("MSUsersBySignDoctorInquiry");
                    if (array.getJSONObject(0).has("MessageCode")) {
                        UsersBySignDoctor ub = new UsersBySignDoctor();
                        ResultBean resultBean = new ResultBean();
                        resultBean.setMessageCode(array.getJSONObject(0).getString("MessageCode").toString());
                        resultBean.setMessageContent(array.getJSONObject(0).getString("MessageContent").toString());
                        ub.setResultBean(resultBean);
                        list.add(ub);
                    } else {
                        for (int i = 0; i < array.length(); i++) {
                            UsersBySignDoctor ub = new UsersBySignDoctor();
                            JSONObject jsonObject = (JSONObject) array.get(i);
                            ub.setUser_FullName(jsonObject.getString("RealName"));
                            ub.setUser_Name(jsonObject.getString("RealName"));
                            ub.setUser_RegisterId(jsonObject.getString("UserID"));
                            String imgurl=jsonObject.getString("PictureURL");
                            if(TextUtils.isEmpty(imgurl)||imgurl.contains("vine.gif")){
                                ub.setUser_Icon("");
                            }else{
                                ub.setUser_Icon(Constans.JE_BASE_URL3 + imgurl.replace("~", "").replace("\\", "/"));
                            }
                            String username = ub.getUser_FullName();
                            if (TextUtils.isEmpty(username)) {
                                ub.setSortLetters("#");
                            } else {
                                String pinyin = characterParser.getSelling(username).trim();

                                String sortString = pinyin.substring(0, 1).toUpperCase();
                                if (sortString.matches("[A-Z]")) {
                                    ub.setSortLetters(sortString.toUpperCase());
                                } else {
                                    ub.setSortLetters("#");
                                }
                            }
                            list.add(ub);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Collections.sort(list, pinyinComparator);
                mList.addAll(list);
                listener.OnSuccess(mList);
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
