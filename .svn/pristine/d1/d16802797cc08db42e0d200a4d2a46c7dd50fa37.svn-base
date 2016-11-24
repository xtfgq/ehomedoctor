package com.zzu.ehome.ehomefordoctor.app;

import android.util.Log;

import com.zzu.ehome.ehomefordoctor.entity.ResultBean;
import com.zzu.ehome.ehomefordoctor.service.ServiceStore;
import com.zzu.ehome.ehomefordoctor.utils.Node;
import com.zzu.ehome.ehomefordoctor.utils.RequestManager;
import com.zzu.ehome.ehomefordoctor.utils.SharePreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static io.rong.imlib.statistics.UserData.name;

/**
 * Created by Administrator on 2016/9/30.
 */

public class CommonApi {
    /**
     * 融云连接服务器接口
     */
    public  interface RongIMListener {

        void OnSuccess(String userid);
    }
    public  interface RongTokenListener {

        void OnSuccess(String msg);
    }


    /**
     * 获取融云token的公共接口
     * @param userid
     * @param name
     * @param head

     * { "GetToken":[{ "MessageCode": "0","MessageContent":"++kcEWpl/YgwB6ukqOw6AaPE9Vt9SyDY/AzLjg4OVJReGLuA+tS6MOvG6t6BreV4l8WemQ8z/bVa68cXa6gxDQ=="}]}
     */
    public static  void getToken(String userid,String name,String head,final RongTokenListener listener){
        RequestManager manager=RequestManager.getInstance();
        Map<String,String> map=new HashMap<>();
        map.put("userId",userid);
        map.put("name",name);
        map.put("portraitUri",head);
        String result= Node.getResult("GetToken",map);
        final ServiceStore service= manager.create(ServiceStore.class);
        Call<ResponseBody> callGetToken=service.getToken(result);
        SharePreferenceUtil.getInstance(App.getInstance()).setNICK(name);
        SharePreferenceUtil.getInstance(App.getInstance()).setRongHead(head);
        manager.execute(callGetToken, new RequestManager.RequestCallBack() {
            @Override
            public void onSueecss(String msg) {
                try {
                    JSONObject mySO = new JSONObject(msg);
                    org.json.JSONArray array = mySO
                            .getJSONArray("GetToken");
                    SharePreferenceUtil.getInstance(App.getInstance()).setRongToken(array.getJSONObject(0).getString("MessageContent").toString());
                    listener.OnSuccess(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }

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

    /**
     * 连接融云服务器
     * @param token
     * @param listener
     */
    public static void connent(String token,final RongIMListener listener){

        if (App.getInstance().getApplicationInfo().packageName.equals(App.getCurProcessName(App.getInstance().getApplicationContext()))) {


            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {

                    Log.d("LoginActivity", "--onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {
                    listener.OnSuccess(userid);
                    Log.d("LoginActivity", "--onSuccess" + userid);

                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 *                  http://www.rongcloud.cn/docs/android.html#常见错误码
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                    Log.d("LoginActivity", "--onError" + errorCode);
                }
            });
        }
    }

}
