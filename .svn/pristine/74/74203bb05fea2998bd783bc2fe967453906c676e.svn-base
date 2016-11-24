package com.zzu.ehome.ehomefordoctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.adapter.JzjlAdapter;
import com.zzu.ehome.ehomefordoctor.entity.TreatmentInquirywWithPage;
import com.zzu.ehome.ehomefordoctor.mvp.presenter.JzjlPresenter;
import com.zzu.ehome.ehomefordoctor.mvp.view.IJzjlView;
import com.zzu.ehome.ehomefordoctor.view.HeadView;
import com.zzu.ehome.ehomefordoctor.view.ProgressStateLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mersens on 2016/10/25.
 */

public class JzjlActivity extends BaseActivity implements IJzjlView{
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progressStateLayout)
    ProgressStateLayout progressStateLayout;
    private String mTargetId;
    private int pagesize=10;
    private int page=0;
    private JzjlPresenter presenter;
    List<TreatmentInquirywWithPage> list ;
    private JzjlAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_jzjl);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public void init() {
        mTargetId = getIntent().getStringExtra(MedicalRecordActivity.TARGETID);
        setLeftWithTitleViewMethod(R.mipmap.icon_arrow_left, "就诊记录", new HeadView.OnLeftClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        presenter=new JzjlPresenter(this);
        presenter.getInfo();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getInfo();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(JzjlActivity.this,JzjlDetalActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("MedicalRecords",list.get(position) );
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public String getUserid() {
        return mTargetId;
    }

    @Override
    public String getPageSize() {
        return pagesize+"";
    }

    @Override
    public String getPage() {
        return page+"";
    }

    @Override
    public <T> void onSuccess(T t) {
        swipeRefreshLayout.setRefreshing(false);
        list=(List<TreatmentInquirywWithPage>)t;
        if(list!=null && list.size()>0){
            adapter=new JzjlAdapter(JzjlActivity.this,list);
            listView.setAdapter(adapter);
            progressStateLayout.showContent();
        }else{
            progressStateLayout.showEmpty(R.mipmap.icon_none,"暂无就诊记录数据");
        }
    }

    @Override
    public void onError() {
        swipeRefreshLayout.setRefreshing(false);
        progressStateLayout.showError(new ProgressStateLayout.ReloadListener() {
            @Override
            public void onClick() {
                page=0;
                presenter.getInfo();
            }
        });
    }
}
