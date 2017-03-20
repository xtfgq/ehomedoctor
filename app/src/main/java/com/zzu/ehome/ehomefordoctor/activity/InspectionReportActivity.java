package com.zzu.ehome.ehomefordoctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.adapter.InspectionReportAdapter;
import com.zzu.ehome.ehomefordoctor.entity.ResultContent;
import com.zzu.ehome.ehomefordoctor.mvp.presenter.InspectionReportPresenter;
import com.zzu.ehome.ehomefordoctor.mvp.view.InspectionReportView;
import com.zzu.ehome.ehomefordoctor.utils.DateUtils;
import com.zzu.ehome.ehomefordoctor.view.HeadView;
import com.zzu.ehome.ehomefordoctor.view.ProgressStateLayout;
import com.zzu.ehome.ehomefordoctor.view.PullToRefreshLayout;

import java.util.ArrayList;
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

    @BindView(R.id.progressStateLayout)
    ProgressStateLayout progressStateLayout;
    @BindView(R.id.common_actionbar)
    HeadView commonActionbar;
    private LinearLayout layout_none;
    private PullToRefreshLayout pulltorefreshlayout;
    private String mUserNo;
    private int pagesize = 10;
    private int page = 1;
    private InspectionReportPresenter presenter;
    private InspectionReportAdapter adapter;
    private List<ResultContent> mList=new ArrayList<>();
    private boolean isFirst = true;
    private boolean isReflash = false;
    private boolean isLoading = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_inspection_report);
        ButterKnife.bind(this);
        init();

    }

    @Override
    public void init() {
        setLeftWithTitleViewMethod(R.mipmap.icon_arrow_left, "血常规检查报告", new HeadView.OnLeftClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        mUserNo = getIntent().getStringExtra(UserNo);
        presenter = new InspectionReportPresenter(this);
        presenter.getInspectionReport();

        pulltorefreshlayout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        layout_none = (LinearLayout) findViewById(R.id.layout_none);
        layout_none.setVisibility(View.GONE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(InspectionReportActivity.this, InspectionReportDetailActivity.class);
                i.putExtra("Type", mList.get(position).getOCRType());
                i.putExtra("RecordID", mList.get(position).getID());
                i.putExtra("TypeTitle","血常规");
                i.putExtra("Title", DateUtils.StringPattern(mList.get(position).getCreatedDate(), "yyyy/MM/dd HH:mm:ss", "yyyy-MM-dd"));
                i.putExtra("UserNo", mUserNo);
                startActivity(i);
            }
        });
        pulltorefreshlayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                page = 1;
                isFirst = true;
                isReflash = true;
                isLoading = false;
                presenter.getInspectionReport();


            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                page++;
                isLoading = true;
                isReflash = false;
                presenter.getInspectionReport();
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
    public String getType() {
        return "01";
    }

    @Override
    public <T> void onSuccess(T t) {

        if ((List<ResultContent>) t != null && ((List<ResultContent>) t).size() > 0) {
            if (page == 1) {
                mList.clear();
                mList = (List<ResultContent>) t;
            } else {
                mList.addAll((List<ResultContent>) t);
            }
            if (isFirst) {
                adapter = new InspectionReportAdapter(InspectionReportActivity.this, mList);
                listView.setAdapter(adapter);
                isFirst = false;
            }

            if (isReflash) {
                isReflash = false;
                isFirst = false;
                adapter.setmList(mList);
                adapter.notifyDataSetChanged();
                pulltorefreshlayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            } else if (isLoading) {
                isLoading = false;
                isFirst = false;
                adapter.setmList(mList);
                adapter.notifyDataSetChanged();
                pulltorefreshlayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
            progressStateLayout.showContent();
        } else if ((List<ResultContent>) t == null && page == 1) {

            layout_none.setVisibility(View.VISIBLE);
            pulltorefreshlayout.setVisibility(View.GONE);
        } else if ((List<ResultContent>) t == null && page > 1) {
            isLoading = false;
            isFirst = false;
            adapter.notifyDataSetChanged();
            pulltorefreshlayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            Toast.makeText(InspectionReportActivity.this, "已经没有更多数据了",
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onError() {
        progressStateLayout.showError(new ProgressStateLayout.ReloadListener() {
            @Override
            public void onClick() {
                page = 1;
                isFirst = true;
                isReflash = false;
                isLoading = false;
                if (presenter == null) {
                    presenter = new InspectionReportPresenter(InspectionReportActivity.this);
                }
                presenter.getInspectionReport();
            }
        });
    }
}
