package com.zzu.ehome.ehomefordoctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.adapter.OcrDetailAdapter;
import com.zzu.ehome.ehomefordoctor.entity.BloodRoutine;
import com.zzu.ehome.ehomefordoctor.mvp.presenter.InspectionReportDetialPresenter;
import com.zzu.ehome.ehomefordoctor.mvp.view.InspectionReportDetialView;
import com.zzu.ehome.ehomefordoctor.view.HeadView;
import com.zzu.ehome.ehomefordoctor.view.MyScrollListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mersens on 2016/10/29.
 */

public class InspectionReportDetailActivity extends BaseActivity implements InspectionReportDetialView {
    @BindView(R.id.lilstView)
    MyScrollListView lilstView;
    private String title,type,id;
    private String mUserNo;
    private InspectionReportDetialPresenter presenter;
    private OcrDetailAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_inspection_report_detail);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public void init() {
        title=this.getIntent().getStringExtra("Title");
        type=this.getIntent().getStringExtra("Type");
        id=this.getIntent().getStringExtra("RecordID");
        mUserNo=getIntent().getStringExtra("UserNo");
        setLeftWithTitleViewMethod(R.mipmap.icon_arrow_left, title, new HeadView.OnLeftClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        presenter=new InspectionReportDetialPresenter(this);
        presenter.getInspectionReport();

    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getUserNum() {
        return mUserNo;
    }

    @Override
    public <T> void onSuccess(T t) {
        List<BloodRoutine> list=(List<BloodRoutine>)t;
        if(list!=null && list.size()>0){
            adapter=new OcrDetailAdapter(InspectionReportDetailActivity.this,list);
            lilstView.setAdapter(adapter);
        }
    }

    @Override
    public void onError() {
        Toast.makeText(this, "数据请求失败", Toast.LENGTH_SHORT).show();

    }
}
