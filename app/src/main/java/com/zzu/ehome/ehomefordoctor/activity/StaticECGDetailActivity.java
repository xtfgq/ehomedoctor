package com.zzu.ehome.ehomefordoctor.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.utils.DateUtils;
import com.zzu.ehome.ehomefordoctor.view.HeadView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mersens on 2016/10/26.
 */

public class StaticECGDetailActivity extends BaseActivity {
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tvtime)
    TextView tvtime;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tvname)
    TextView tvname;
    @BindView(R.id.tvres)
    TextView tvres;
    @BindView(R.id.icon_pdf)
    ImageView iconPdf;
    private String imurl, Diagnosis, PatientName, CollectTime;
    private Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_static_detail);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public void init() {
        setLeftWithTitleViewMethod(R.mipmap.icon_arrow_left, "静态心电报告", new HeadView.OnLeftClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        mIntent = this.getIntent();
        imurl = mIntent.getStringExtra("imurl");
        Diagnosis = mIntent.getStringExtra("Diagnosis");
        PatientName = mIntent.getStringExtra("PatientName");
        CollectTime = mIntent.getStringExtra("CollectTime");
        tvtime.setText(DateUtils.StringPattern(CollectTime, "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd"));
        tvname.setText(PatientName);
        tvres.setText(Diagnosis);
        Glide.with(StaticECGDetailActivity.this).load(imurl).into(iconPdf);
        iconPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StaticECGDetailActivity.this, ImageECGDetail.class);
                i.putExtra("imurl", imurl);
                startActivity(i);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getPermission();
        }
    }
}
