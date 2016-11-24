package com.zzu.ehome.ehomefordoctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.entity.User;
import com.zzu.ehome.ehomefordoctor.mvp.presenter.UserDataPresenter;
import com.zzu.ehome.ehomefordoctor.mvp.view.IUserDateView;
import com.zzu.ehome.ehomefordoctor.view.HeadView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mersens on 2016/10/25.
 */

public class MedicalRecordActivity extends BaseActivity implements IUserDateView {

    @BindView(R.id.layout_base_file)
    RelativeLayout layoutBaseFile;
    @BindView(R.id.layout_health_data)
    RelativeLayout layoutHealthData;
    @BindView(R.id.layout_jzjl)
    RelativeLayout layoutJzjl;
    @BindView(R.id.layout_xdbg)
    RelativeLayout layoutXdbg;
    @BindView(R.id.layout_jcbg)
    RelativeLayout layoutJcbg;
    private String mTargetId, userNo;
    public static final String TARGETID = "TargetId";
    public static final String UserNo = "UserNo";
    private UserDataPresenter presenter;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_medical_record);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public void init() {
        mTargetId = getIntent().getStringExtra("TargetId");
        setLeftWithTitleViewMethod(R.mipmap.icon_arrow_left, "病历", new HeadView.OnLeftClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        presenter = new UserDataPresenter(this);
        presenter.getUserData();
    }

    @OnClick({R.id.layout_base_file, R.id.layout_health_data, R.id.layout_jzjl, R.id.layout_xdbg,R.id.layout_jcbg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_base_file:
                initAction(BaseFileActivity.class);
                break;
            case R.id.layout_health_data:
                initAction(HealthDataActivity.class);
                break;
            case R.id.layout_jzjl:
                initAction(JzjlActivity.class);
                break;
            case R.id.layout_xdbg:
                initAction(ECGActivity.class);
                break;
            case R.id.layout_jcbg:
                initAction(InspectionReportActivity.class);
                break;
        }
    }

    private <T> void initAction(Class<T> cls) {
        Intent intent = new Intent(MedicalRecordActivity.this, cls);
        intent.putExtra(TARGETID, mTargetId);
        intent.putExtra(UserNo, userNo);
        startActivity(intent);

    }

    @Override
    public <T> void onSuccess(T t) {
        user = (User) t;
        userNo = user.getUserno();
    }

    @Override
    public String getUserId() {
        return mTargetId;
    }

    @OnClick(R.id.layout_jcbg)
    public void onClick() {
    }
}
