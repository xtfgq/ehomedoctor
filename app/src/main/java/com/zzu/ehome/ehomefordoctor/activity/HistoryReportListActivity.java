package com.zzu.ehome.ehomefordoctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.view.HeadView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zzu.ehome.ehomefordoctor.activity.MedicalRecordActivity.TARGETID;
import static com.zzu.ehome.ehomefordoctor.activity.MedicalRecordActivity.UserNo;

/**
 * Created by Administrator on 2016/12/7.
 */

public class HistoryReportListActivity extends BaseActivity {

    @BindView(R.id.layout_file)
    RelativeLayout layoutFile;
    @BindView(R.id.layout_xcg)
    RelativeLayout layoutXcg;
    @BindView(R.id.layout_bioch)
    RelativeLayout layoutBioch;
    @BindView(R.id.layout_xdbg)
    RelativeLayout layoutXdbg;
    @BindView(R.id.layout_jcbg)
    RelativeLayout layoutJcbg;
    private String mTargetId;
    private String mUserNo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_medical_history);
        ButterKnife.bind(this);
        mTargetId = getIntent().getStringExtra(TARGETID);
        mUserNo = getIntent().getStringExtra(UserNo);
        init();
    }

    @Override
    public void init() {
        setLeftWithTitleViewMethod(R.mipmap.icon_arrow_left, "历史报告", new HeadView.OnLeftClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }

    @OnClick({R.id.layout_file, R.id.layout_xcg, R.id.layout_bioch, R.id.layout_xdbg, R.id.layout_jcbg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_file:
                initAction(SmartWebView.class);
                break;
            case R.id.layout_xcg:
                initAction(InspectionReportActivity.class);
                break;
            case R.id.layout_bioch:
                initAction(BiochemicalReportActivity.class);
                break;
            case R.id.layout_xdbg:
                initAction(ECGActivity.class);
                break;
            case R.id.layout_jcbg:
                initAction(ExaminationReportActivity.class);
                break;
        }
    }
    private <T> void initAction(Class<T> cls) {
        Intent intent = new Intent(HistoryReportListActivity.this, cls);
        intent.putExtra(TARGETID, mTargetId);
        intent.putExtra(UserNo, mUserNo);
        startActivity(intent);

    }
}
