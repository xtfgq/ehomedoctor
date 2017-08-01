package com.zzu.ehome.ehomefordoctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.fragment.DynamicFragment;
import com.zzu.ehome.ehomefordoctor.fragment.StaticFragment;
import com.zzu.ehome.ehomefordoctor.view.HeadView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mersens on 2016/10/25.
 */

public class ECGActivity extends BaseActivity {

    @BindView(R.id.tv_d)
    TextView tvD;
    @BindView(R.id.layout_d)
    RelativeLayout layoutD;
    @BindView(R.id.tv_j)
    TextView tvJ;
    @BindView(R.id.layout_j)
    RelativeLayout layoutJ;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    private String mTargetId;
    private String mUserNo;
    private int selectColor, unSelectColor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ecg);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public void init() {
        unSelectColor = getResources().getColor(R.color.text_color1);
        selectColor = getResources().getColor(R.color.actionbar_color);
        mTargetId=getIntent().getStringExtra(MedicalRecordActivity.TARGETID);
        mUserNo = getIntent().getStringExtra(MedicalRecordActivity.UserNo);
        setLeftWithTitleViewMethod(R.mipmap.icon_arrow_left, "心电报告", new HeadView.OnLeftClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        tvD.setTextColor(unSelectColor);
        tvJ.setTextColor(selectColor);
        addFragment(Type.STATIC_STATE);
    }

    @OnClick({R.id.layout_d, R.id.layout_j})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_d:
                setColor(Type.DYNAMIC);
                addFragment(Type.DYNAMIC);
                break;
            case R.id.layout_j:
                setColor(Type.STATIC_STATE);
                addFragment(Type.STATIC_STATE);
                break;
        }
    }
    public Fragment creatFragment(Type type) {
        Fragment fragment = null;
        switch (type) {
            case DYNAMIC:
                fragment = DynamicFragment.getInstance(mTargetId,mUserNo);
                break;

            case STATIC_STATE:
                fragment = StaticFragment.getInstance(mTargetId,mUserNo);
                break;
        }

        return fragment;
    }


    public void addFragment(Type type) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, creatFragment(type)).commit();
    }
    public void setColor(Type type) {
        resetColor();
        switch (type) {
            case DYNAMIC:
                tvD.setTextColor(selectColor);
                break;
            case STATIC_STATE:
                tvJ.setTextColor(selectColor);
                break;
        }

    }


    public void resetColor() {
        tvJ.setTextColor(unSelectColor);
        tvD.setTextColor(unSelectColor);
    }
    public enum Type {
        DYNAMIC, STATIC_STATE;
    }
}
