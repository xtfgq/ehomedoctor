package com.zzu.ehome.ehomefordoctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.utils.NetUtils;
import com.zzu.ehome.ehomefordoctor.utils.SharePreferenceUtil;
import com.zzu.ehome.ehomefordoctor.utils.ToastUtils;


/**
 * Created by Administrator on 2016/9/29.
 */

public class WelcomeActivity extends BaseSimpleActivity{
    private static final long SPLASH_DELAY_MILLIS = 3000;
    private static final int GO_HOME = 0X00;
    private static final int GO_LOGIN = 0X01;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GO_HOME:
                    goHome();
                    break;
                case GO_LOGIN:
                    goLogin();
                    break;
            }

        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
            finish();
            return;
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
        init();
    }

    private void goHome(){
        startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
        finish();
    }
    private void goLogin(){
        startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
        finish();
    }


    @Override
    public void init() {
        if (!NetUtils.isNetworkConnected(WelcomeActivity.this)) {
            ToastUtils.showMessage(WelcomeActivity.this, R.string.msgUninternet);
            return;
        }
        if (TextUtils.isEmpty(SharePreferenceUtil.getInstance(WelcomeActivity.this).getUserId())) {
            mHandler.sendEmptyMessageDelayed(GO_LOGIN, SPLASH_DELAY_MILLIS);
        } else {
            mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
        }
    }
}
