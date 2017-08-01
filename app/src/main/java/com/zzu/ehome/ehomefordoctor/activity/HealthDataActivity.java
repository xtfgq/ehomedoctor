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

/**
 * Created by Mersens on 2016/10/25.
 */

public class HealthDataActivity extends BaseActivity {
    @BindView(R.id.layout_xt)
    RelativeLayout layoutXt;
    @BindView(R.id.layout_xy)
    RelativeLayout layoutXy;
    @BindView(R.id.layout_nx)
    RelativeLayout layoutNx;
    @BindView(R.id.layout_dgc)
    RelativeLayout layoutDgc;
 /*   @BindView(R.id.layout_xcg)
    RelativeLayout layoutXcg;*/
    private String mTargetId;
    private String mUserNo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_health_data);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public void init() {
        mTargetId = getIntent().getStringExtra(TARGETID);
        mUserNo = getIntent().getStringExtra(MedicalRecordActivity.UserNo);
        setLeftWithTitleViewMethod(R.mipmap.icon_arrow_left, "健康数据", new HeadView.OnLeftClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });

    }

    @OnClick({R.id.layout_xt, R.id.layout_xy, R.id.layout_nx, R.id.layout_dgc})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_xt:
                initAction(NewSuggarActvity.class);
                break;
            case R.id.layout_xy:
                initAction(NewPressActvity.class);
                break;
            case R.id.layout_nx:
                initAction(UricacidActivity.class);
                break;
            case R.id.layout_dgc:
                initAction(CholesterinActivity.class);
                break;
//            case R.id.layout_xcg:
//                initAction(InspectionReportActivity.class);
//                break;
        }
    }

    private <T> void initAction(Class<T> cls) {
        Intent intent = new Intent(HealthDataActivity.this, cls);
        intent.putExtra(TARGETID, mTargetId);
        intent.putExtra(MedicalRecordActivity.UserNo, mUserNo);
        startActivity(intent);

    }

}
