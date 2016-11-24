package com.zzu.ehome.ehomefordoctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.adapter.InspectionReportAdapter;
import com.zzu.ehome.ehomefordoctor.entity.ResultContent;
import com.zzu.ehome.ehomefordoctor.mvp.presenter.InspectionReportPresenter;
import com.zzu.ehome.ehomefordoctor.mvp.view.InspectionReportView;
import com.zzu.ehome.ehomefordoctor.view.HeadView;
import com.zzu.ehome.ehomefordoctor.view.ProgressStateLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zzu.ehome.ehomefordoctor.activity.MedicalRecordActivity.UserNo;

/**
 * Created by Mersens on 2016/10/29.
 */

public class InspectionReportActivity extends BaseActivity implements InspectionReportView {

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progressStateLayout)
    ProgressStateLayout progressStateLayout;
    @BindView(R.id.common_actionbar)
    HeadView commonActionbar;

    private String mUserNo;
    private int pagesize = 10;
    private int page = 1;
    private InspectionReportPresenter presenter;
    private InspectionReportAdapter adapter;
    private List<ResultContent> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_inspection_report);
        ButterKnife.bind(this);
        init();

    }

    @Override
    public void init() {
        setLeftWithTitleViewMethod(R.mipmap.icon_arrow_left, "检查报告", new HeadView.OnLeftClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        mUserNo = getIntent().getStringExtra(UserNo);
        presenter = new InspectionReportPresenter(this);
        presenter.getInspectionReport();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getInspectionReport();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(InspectionReportActivity.this, InspectionReportDetailActivity.class);
                i.putExtra("Type", mList.get(position).getOCRType());
                i.putExtra("RecordID", mList.get(position).getID());
                i.putExtra("Title", mList.get(position).getOCRTypeName());
                i.putExtra("UserNo", mUserNo);
                startActivity(i);
            }
        });

    }

    @Override
    public String getPageSize() {
        return pagesize + "";
    }

    @Override
    public String getIndex() {
        return page + "";
    }

    @Override
    public String getUserNum() {
        return mUserNo;
    }

    @Override
    public <T> void onSuccess(T t) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }

        mList = (List<ResultContent>) t;
        if (mList != null && mList.size() > 0) {
            adapter = new InspectionReportAdapter(InspectionReportActivity.this, mList);
            listView.setAdapter(adapter);
            progressStateLayout.showContent();
        } else {
            progressStateLayout.showEmpty(R.mipmap.icon_none, "暂无数据");
        }

    }

    @Override
    public void onError() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        progressStateLayout.showError(new ProgressStateLayout.ReloadListener() {
            @Override
            public void onClick() {
                if (presenter == null) {
                    presenter = new InspectionReportPresenter(InspectionReportActivity.this);
                }
                presenter.getInspectionReport();
            }
        });
    }
}
