package com.zzu.ehome.ehomefordoctor.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.activity.ECGPDFStaticActivity;
import com.zzu.ehome.ehomefordoctor.activity.StaticECGDetailActivity;
import com.zzu.ehome.ehomefordoctor.adapter.ECGStaticAadapter;
import com.zzu.ehome.ehomefordoctor.entity.StaticBean;
import com.zzu.ehome.ehomefordoctor.mvp.presenter.StaticDtatPresenter;
import com.zzu.ehome.ehomefordoctor.mvp.view.IDynamicView;
import com.zzu.ehome.ehomefordoctor.view.ProgressStateLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zzu.ehome.ehomefordoctor.db.DBHelper.mContext;

/**
 * Created by Mersens on 2016/10/25.
 */

public class StaticFragment extends BaseFragment implements IDynamicView {
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progressStateLayout)
    ProgressStateLayout progressStateLayout;
    private View mView;
    private String userid;
    public static final String USERID = "userid";
    private StaticDtatPresenter presenter;
    private List<StaticBean> list;
    private ECGStaticAadapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_bsae_list, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;
        init();
    }

    @Override
    public void init() {
        userid = getArguments().getString(USERID);
        presenter=new StaticDtatPresenter(this);
        presenter.getStaticData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getStaticData();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final StaticBean item = adapter.getItem(position);

                if(item.getImgPath().contains("pdf")){
                    Intent i = new Intent(getActivity(), ECGPDFStaticActivity.class);
                    i.putExtra("imurl", item.getImgPath());
                    i.putExtra("Diagnosis", item.getDiagnosis());
                    i.putExtra("PatientName", item.getPatientName());
                    i.putExtra("CollectTime", item.getCollectTime());
                    getActivity().startActivity(i);
                }else{
                    Intent i = new Intent(getActivity(), StaticECGDetailActivity.class);
                    i.putExtra("imurl", item.getImgPath());
                    i.putExtra("Diagnosis", item.getDiagnosis());
                    i.putExtra("PatientName", item.getPatientName());
                    i.putExtra("CollectTime", item.getCollectTime());
                    startActivity(i);
                }

            }
        });
    }

    public static Fragment getInstance(String userid) {
        StaticFragment fragment = new StaticFragment();
        Bundle bundle = new Bundle();
        bundle.putString(USERID, userid);
        fragment.setArguments(bundle);
        return fragment;
    }

;
    @Override
    public String getUserid() {
//        userid = "92";
        return userid;
    }

    @Override
    public <T> void onSuccess(T t) {
        swipeRefreshLayout.setRefreshing(false);
        list=(List<StaticBean>)t;
        if(list!=null && list.size()>0){
            adapter=new ECGStaticAadapter(getActivity(),list);
            listView.setAdapter(adapter);
            progressStateLayout.showContent();
        }else{
            progressStateLayout.showEmpty(R.mipmap.icon_none,"暂无心电的报告数据");
        }
    }

    @Override
    public void onError() {
        swipeRefreshLayout.setRefreshing(false);
        progressStateLayout.showEmpty(R.mipmap.icon_none,"数据加载失败");
    }
}
