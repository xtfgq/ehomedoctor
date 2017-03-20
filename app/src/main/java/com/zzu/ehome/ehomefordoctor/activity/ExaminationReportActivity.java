package com.zzu.ehome.ehomefordoctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.adapter.MedicalExaminationAdapter;
import com.zzu.ehome.ehomefordoctor.entity.MedicalBean;
import com.zzu.ehome.ehomefordoctor.mvp.presenter.DynamicPresenter;
import com.zzu.ehome.ehomefordoctor.mvp.presenter.MeidicalReportInquiryPresenter;
import com.zzu.ehome.ehomefordoctor.mvp.view.IMeidicalReportInquiryView;
import com.zzu.ehome.ehomefordoctor.view.HeadView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zzu.ehome.ehomefordoctor.activity.MedicalRecordActivity.TARGETID;
import static com.zzu.ehome.ehomefordoctor.activity.MedicalRecordActivity.UserNo;

/**
 * Created by Administrator on 2016/12/7.
 */

public class ExaminationReportActivity extends BaseActivity implements IMeidicalReportInquiryView {
    @BindView(R.id.tvnodate)
    TextView tvnodate;
    private String mTargetId;
    private String mUserNo;
    @BindView(R.id.layout_none)
    LinearLayout layoutNone;
    @BindView(R.id.listView)
    ListView listView;
    private MedicalExaminationAdapter adapter;
    private MeidicalReportInquiryPresenter presenter;
    List<MedicalBean> list;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.layout_examination_report);
        mTargetId = getIntent().getStringExtra(TARGETID);
        mUserNo = getIntent().getStringExtra(UserNo);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public void init() {
        setLeftWithTitleViewMethod(R.mipmap.icon_arrow_left, "历史体检报告", new HeadView.OnLeftClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        presenter=new MeidicalReportInquiryPresenter(this);
        presenter.doMeidicalReportInquiry();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ExaminationReportActivity.this, MedicalExaminationDesActivity.class);
                i.putExtra("ID", list.get(position).getID());
                i.putExtra(TARGETID, mTargetId);
                startActivity(i);

            }
        });
    }

    @Override
    public <T> void onSuccess(T t) {
        list = (List<MedicalBean>) t;
        if (list.get(0).getmResultBean() != null) {
            layoutNone.setVisibility(View.VISIBLE);
            return;
        }
        layoutNone.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        if (adapter == null) {
            adapter = new MedicalExaminationAdapter(ExaminationReportActivity.this, list);
            listView.setAdapter(adapter);
        } else {
            adapter.setmList(list);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public String getUserId() {
        return mTargetId;
    }

    @Override
    public void onError(String error) {
        layoutNone.setVisibility(View.VISIBLE);
        tvnodate.setText("数据加载失败");
    }
}
