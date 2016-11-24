package com.zzu.ehome.ehomefordoctor.mvp.model;

import android.text.TextUtils;
import android.widget.Toast;

import com.zzu.ehome.ehomefordoctor.app.App;
import com.zzu.ehome.ehomefordoctor.app.Constans;
import com.zzu.ehome.ehomefordoctor.db.EHomeDao;
import com.zzu.ehome.ehomefordoctor.db.EHomeDaoImpl;
import com.zzu.ehome.ehomefordoctor.entity.DoctorBean;
import com.zzu.ehome.ehomefordoctor.entity.ResultBean;
import com.zzu.ehome.ehomefordoctor.mvp.listener.ILogin;
import com.zzu.ehome.ehomefordoctor.mvp.listener.OnLoginListener;
import com.zzu.ehome.ehomefordoctor.service.ServiceStore;
import com.zzu.ehome.ehomefordoctor.utils.Node;
import com.zzu.ehome.ehomefordoctor.utils.RequestManager;
import com.zzu.ehome.ehomefordoctor.utils.SharePreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by Mersens on 2016/9/18.
 */
public class ILoginImpl implements ILogin {
    public RequestManager manager;
    private EHomeDao dao;
    public ILoginImpl(){
        manager= RequestManager.getInstance();
    }

    @Override
    public void login(final String name, final String psd, final OnLoginListener listener) {
        if(TextUtils.isEmpty(name)){
            Toast.makeText(App.getInstance(), "用户名不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(psd)){
            Toast.makeText(App.getInstance(), "密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String,String> map=new HashMap<>();
        map.put("DoctorMobile",name);
        map.put("Password",psd);
        map.put("PhoneType","0");
        map.put("ClientID","");
        map.put("DeviceToken","");
        String result= Node.getResult("MSDoctorLogin",map);
        final ServiceStore service= manager.create(ServiceStore.class);
        Call<ResponseBody> call=service.login(result);
        dao = new EHomeDaoImpl(App.getInstance());

        manager.execute(call, new RequestManager.RequestCallBack() {
            @Override
            public void onSueecss(String msg) {
                try {
                    DoctorBean doctorBean=new DoctorBean();
                    ResultBean resultBean=new ResultBean();
                    JSONObject mySO = new JSONObject(msg);
                    org.json.JSONArray array = mySO
                            .getJSONArray("MSDoctorLogin");
                    if(array.getJSONObject(0).has("MessageCode")){
                        resultBean.setMessageCode(array.getJSONObject(0).getString("MessageCode").toString());
                        resultBean.setMessageContent(array.getJSONObject(0).getString("MessageContent").toString());
                        doctorBean.setResultBean(resultBean);
                    }else{
                        JSONObject jsonObject=(JSONObject) array.get(0);
                        doctorBean.setDoctorID(jsonObject.getString("DoctorID"));
                        doctorBean.setMobile(jsonObject.getString("Mobile"));
                        doctorBean.setClientID(jsonObject.getString("ClientID"));
                        doctorBean.setDoctorName(jsonObject.getString("DoctorName"));
                        doctorBean.setHospitalName(jsonObject.getString("HospitalName"));
                        doctorBean.setTitle(jsonObject.getString("Title"));
                        doctorBean.setDepartment(jsonObject.getString("Department"));
                        doctorBean.setGoodAt(jsonObject.getString("GoodAt"));
                        doctorBean.setApplyTo(jsonObject.getString("ApplyTo"));
                        doctorBean.setDescription(jsonObject.getString("Description"));
                        doctorBean.setSpeciaty(jsonObject.getString("Speciaty"));
                        String imgurl=jsonObject.getString("ImageURL");
                        if(!TextUtils.isEmpty(imgurl)){
                            doctorBean.setImageURL(Constans.JE_BASE_URLDoctor + imgurl.replace("~", "").replace("\\", "/"));
                        }
//                       dao.addUserInfo(doctorBean);
                       SharePreferenceUtil.getInstance(App.getInstance()).setUserId(doctorBean.getDoctorID());


                    }
                    listener.onSuccess(doctorBean);

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
