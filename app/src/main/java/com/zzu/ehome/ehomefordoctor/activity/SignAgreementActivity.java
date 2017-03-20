package com.zzu.ehome.ehomefordoctor.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.app.App;

import com.zzu.ehome.ehomefordoctor.entity.SignBean;

import com.zzu.ehome.ehomefordoctor.mvp.presenter.ISignDataPresenter;

import com.zzu.ehome.ehomefordoctor.mvp.view.ISignDataView;

import com.zzu.ehome.ehomefordoctor.utils.DateUtils;
import com.zzu.ehome.ehomefordoctor.utils.SharePreferenceUtil;
import com.zzu.ehome.ehomefordoctor.view.DialogTips;
import com.zzu.ehome.ehomefordoctor.view.HeadView;


import java.util.List;


import static com.zzu.ehome.ehomefordoctor.activity.MedicalRecordActivity.TARGETID;
import static com.zzu.ehome.ehomefordoctor.activity.MedicalRecordActivity.UserNo;


/**
 * Created by Mersens on 2017/2/15 14:01
 * Email:626168564@qq.com
 */

public class SignAgreementActivity extends BaseActivity implements ISignDataView{
    private TextView tv_home_doctor_value;
    private TextView tv_hosptial_value;
    private TextView tv_home_fzr_value;
    private TextView tv_cardID_value;
    private TextView tv_tel;
    private TextView tv_time;

    private String doctorid;
    private String mTargetId;
    private String mUserNo;



    private ISignDataPresenter mISignDataPresenter;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.layout_sign_agreement);
        mTargetId=getIntent().getStringExtra(TARGETID);
        mUserNo=getIntent().getStringExtra(UserNo);
        mISignDataPresenter = new ISignDataPresenter(this);
        doctorid=SharePreferenceUtil.getInstance(App.getInstance()).getUserId();

        init();

    }

    public void init() {
        initViews();
        initEvent();
        mISignDataPresenter.getSignData();
    }
    private void initViews() {
        setLeftWithTitleViewMethod(R.mipmap.icon_arrow_left, "家庭医生签约服务协议", new HeadView.OnLeftClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        tv_home_doctor_value=(TextView) findViewById(R.id.tv_home_doctor_value);

        tv_hosptial_value=(TextView) findViewById(R.id.tv_hosptial_value);

        tv_home_fzr_value=(TextView) findViewById(R.id.tv_home_fzr_value);
        tv_cardID_value=(TextView) findViewById(R.id.tv_cardID_value);
        tv_tel=(TextView) findViewById(R.id.tv_tel);
        tv_time=(TextView)findViewById(R.id.tv_time);

    }
    private void initEvent() {
        tv_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拨打服务电话
               showTips();

            }
        });


    }
    private void initDatas() {
//        dao = new EHomeDaoImpl(this);
//      userid= SharePreferenceUtil.getInstance(this).getUserId();
//        if(!TextUtils.isEmpty(userid)){
//            User user=dao.findUserInfoById(userid);
//            if(user!=null){
//                tv_home_fzr_value.setText(user.getUsername());
//                tv_cardID_value.setText(user.getUserno());
//            }
//        }
    }




    private void showTips(){
        DialogTips dialog = new DialogTips(SignAgreementActivity.this,"0371-86505391","拨打",
                "取消", "", false,false);
        dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int userId) {
                callPhone();
            }
        });
        dialog.show();
        dialog = null;
    }
    private void callPhone(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (SignAgreementActivity.this.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                Intent intentTel = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "037186505391"));
                startActivity(intentTel);
            }
        }else{
            Intent intentTel = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "037186505391"));
            startActivity(intentTel);
        }


    }


    @Override
    public <T> void onResultSuccess(T t) {
        List<SignBean> list=(List<SignBean>) t;
        tv_home_doctor_value.setText(list.get(0).getDoctorName());
        tv_hosptial_value.setText(list.get(0).getHospitalName());
        tv_cardID_value.setText(list.get(0).getCardNO());
        tv_home_fzr_value.setText(list.get(0).getUserName());
        String starttime=DateUtils.StringPattern(list.get(0).getStartTime(),"yyyy/MM/dd HH:mm:ss","yyyy.MM.dd");
        String endtime=DateUtils.StringPattern(list.get(0).getEndTime(),"yyyy/MM/dd HH:mm:ss","yyyy.MM.dd");
        tv_time.setText(starttime+"-"+endtime);
    }

    @Override
    public String getUserNo() {
        return mUserNo;
    }

    @Override
    public String getDoctorId() {
        return doctorid;
    }

    @Override
    public void onError(String e) {

    }
}
