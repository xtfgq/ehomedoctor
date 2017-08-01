package com.zzu.ehome.ehomefordoctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.app.CommonApi;
import com.zzu.ehome.ehomefordoctor.db.EHomeDao;
import com.zzu.ehome.ehomefordoctor.db.EHomeDaoImpl;

import com.zzu.ehome.ehomefordoctor.mvp.presenter.CheckInfoPresenter;
import com.zzu.ehome.ehomefordoctor.mvp.presenter.IGetCodePresenter;
import com.zzu.ehome.ehomefordoctor.mvp.presenter.IMSDoctorFindPsdPresenter;
import com.zzu.ehome.ehomefordoctor.mvp.view.ICheckInfoView;
import com.zzu.ehome.ehomefordoctor.mvp.view.IGetCodeView;
import com.zzu.ehome.ehomefordoctor.mvp.view.IMsDoctorFindPassView;
import com.zzu.ehome.ehomefordoctor.service.RegisterCodeTimerService;
import com.zzu.ehome.ehomefordoctor.utils.CheckUtils;
import com.zzu.ehome.ehomefordoctor.utils.RegisterCodeTimer;
import com.zzu.ehome.ehomefordoctor.utils.ToastUtils;
import com.zzu.ehome.ehomefordoctor.view.HeadView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.igexin.push.core.a.n;

/**
 * Created by Administrator on 2016/10/28.
 */

public class FindPsdActivity extends BaseActivity implements ICheckInfoView,IGetCodeView,
        IMsDoctorFindPassView{


    @BindView(R.id.tv_getCode)
    TextView tvGetCode;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.editPhone)
    EditText editPhone;
    @BindView(R.id.editPass_again)
    EditText editPassAgain;
    @BindView(R.id.edCode)
    EditText edCode;
    private Intent mIntent;
    private EHomeDao dao;
    private String check="",realphone="";
    private CheckInfoPresenter mCheckInfoPresenter;
    private IGetCodePresenter mIGetCodePresenter;
    private IMSDoctorFindPsdPresenter mIMSDoctorFindPsdPresenter;

    Handler mCodeHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == RegisterCodeTimer.IN_RUNNING) {// 正在倒计时
                tvGetCode.setText(msg.obj.toString());
                tvGetCode.setEnabled(false);
            } else if (msg.what == RegisterCodeTimer.END_RUNNING) {// 完成倒计时
                tvGetCode.setEnabled(true);
                tvGetCode.setText(msg.obj.toString());
            }
        }

        ;
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_findpsd);
        dao = new EHomeDaoImpl(FindPsdActivity.this);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public void init() {
        setLeftWithTitleViewMethod(R.mipmap.icon_arrow_left, "找回密码", new HeadView.OnLeftClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        RegisterCodeTimerService.setHandler(mCodeHandler);
        mIntent = new Intent(FindPsdActivity.this,
                RegisterCodeTimerService.class);
        mCheckInfoPresenter=new CheckInfoPresenter(this);
        mIGetCodePresenter=new IGetCodePresenter(this);
        mIMSDoctorFindPsdPresenter=new IMSDoctorFindPsdPresenter(this);
    }

    @OnClick({R.id.tv_getCode, R.id.btn_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_getCode:
                if(TextUtils.isEmpty(editPhone.getText().toString())){
                    show("请输入手机号码！");
                    return;
                }
                if(!CheckUtils.isMobileNO(editPhone.getText().toString())){
                    show("请输入正确的手机号码!");
                    return;
                }
                mIGetCodePresenter.getGetCoke();
                break;
            case R.id.btn_save:
                mCheckInfoPresenter.getCheckInfoMessage();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(mIntent);
    }

    @Override
    public String getMoblie() {
        return editPhone.getText().toString();
    }

    @Override
    public String getPassword() {
        return editPassAgain.getText().toString();
    }

    @Override
    public String getCode() {
        return edCode.getText().toString();
    }

    @Override
    public String getEditPhone() {
        return editPhone.getText().toString();
    }

    @Override
    public String getCheck() {
        return check;
    }

    @Override
    public <T> void onMessageSuccess(T t) {
        String message = (String) t;
        if (message.equals("ok")) {
            mIMSDoctorFindPsdPresenter.getFindPsd();
        } else {
            show(message);
        }

    }

    @Override
    public String getUserMobile() {
        return editPhone.getText().toString();
    }

    @Override
    public String getFlag() {
        return "002";
    }

    @Override
    public <T> void onGetCodeSuccess(T t) {
        try {
            String msg=(String)t;
            JSONObject mySO = new JSONObject(msg);
            JSONArray array = mySO
                    .getJSONArray("SendAuthCodeForDoctor");
            realphone=editPhone.getText().toString();
            if(Integer.valueOf(array.getJSONObject(0)
                    .getString("MessageCode"))==0) {
                check = array.getJSONObject(0)
                        .getString("MessageContent");
                startService(mIntent);

//                ToastUtils.showMessage(FindPsdActivity.this, array.getJSONObject(0)
//                        .getString("MessageContent"));
            }else {
                ToastUtils.showMessage(FindPsdActivity.this, array.getJSONObject(0)
                        .getString("MessageContent"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public <T> void onFindSuccess(T t) {
        try {
            String msg=(String)t;
            JSONObject mySO = new JSONObject(msg);
            JSONArray array = mySO
                    .getJSONArray("MSDoctorFindPass");
            if(Integer.valueOf(array.getJSONObject(0)
                    .getString("MessageCode"))==0) {
                ToastUtils.showMessage(FindPsdActivity.this, array.getJSONObject(0)
                        .getString("MessageContent"));
              finish();
            }else {
                ToastUtils.showMessage(FindPsdActivity.this, array.getJSONObject(0)
                        .getString("MessageContent"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getRealPhone() {
        return realphone;
    }
}
