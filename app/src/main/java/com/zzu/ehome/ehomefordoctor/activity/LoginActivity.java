package com.zzu.ehome.ehomefordoctor.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.app.App;
import com.zzu.ehome.ehomefordoctor.app.CommonApi;
import com.zzu.ehome.ehomefordoctor.entity.DoctorBean;
import com.zzu.ehome.ehomefordoctor.mvp.model.DataTimeType;
import com.zzu.ehome.ehomefordoctor.mvp.presenter.LoginPresenter;
import com.zzu.ehome.ehomefordoctor.mvp.view.ILoginView;
import com.zzu.ehome.ehomefordoctor.utils.IOUtils;
import com.zzu.ehome.ehomefordoctor.utils.SharePreferenceUtil;
import com.zzu.ehome.ehomefordoctor.utils.ToastUtils;
import com.zzu.ehome.ehomefordoctor.view.DialogTips;
import com.zzu.ehome.ehomefordoctor.view.HeadView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imageloader.utils.IoUtils;

/**
 * Created by Mersens on 2016/9/28.
 */

public class LoginActivity extends BaseActivity implements ILoginView {
    @BindView(R.id.editPhone)
    EditText editPhone;
    @BindView(R.id.editPass)
    EditText editPass;
    @BindView(R.id.btnlogin)
    Button btnlogin;
    @BindView(R.id.tvselect)
    TextView tvselect;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public void init() {
        setLeftWithTitleViewMethod(R.mipmap.icon_arrow_left, "登录", new HeadView.OnLeftClickListener() {
            @Override
            public void onClick() {
                App.getInstance().exit();
                finish();
            }
        });
        presenter = new LoginPresenter(this);
    }

    @OnClick({R.id.btnlogin, R.id.tvselect})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnlogin:
                if(TextUtils.isEmpty(editPhone.getText().toString())){
                    show("手机号不能为空！");
                    return;
                }
                if(TextUtils.isEmpty(editPass.getText().toString())){
                    show("密码不能为空！");
                    return;
                }
                if(!IOUtils.isMobileNO(editPhone.getText().toString())){
                    show("请输入正确的手机号!");
                    return;
                }
                presenter.doLogin();
                break;
            case R.id.tvselect:
                startActivity(new Intent(LoginActivity.this,FindPsdActivity.class));
                break;
        }
    }

    @Override
    public void hidePro() {
        stopProgressDialog();

    }

    @Override
    public void showPro() {
        startProgressDialog();
    }

    @Override
    public String getUserName() {
        return editPhone.getText().toString();
    }

    @Override
    public String getPsd() {
        return editPass.getText().toString();
    }

    @Override
    public void onSuccess(DoctorBean doctorBean) {
        if (doctorBean.getResultBean() == null) {

            CommonApi.getToken(doctorBean.getDoctorID(), doctorBean.getDoctorName(), doctorBean.getImageURL(), new CommonApi.RongTokenListener() {
                @Override
                public void OnSuccess(String msg) {
                    if (msg.contains("GetToken")) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        App.getInstance().isOnline=true;
                        finish();
                    }
                }
            });

        } else {
            ToastUtils.showMessage(LoginActivity.this, doctorBean.getResultBean().getMessageContent());
        }

    }

    @Override
    public void onErroe(Exception e) {

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (keyCode == KeyEvent.KEYCODE_BACK) {

                confirmExit();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void confirmExit() {

        DialogTips dialog = new DialogTips(LoginActivity.this, "", "是否退出软件？",
                "确定", true, true);
        dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int userId) {

                App.getInstance().exit();
                finish();
            }
        });

        dialog.show();
        dialog = null;


    }
}
