package com.zzu.ehome.ehomefordoctor.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.activity.ECGDetailsActivity;
import com.zzu.ehome.ehomefordoctor.adapter.ECGReportAdapter;
import com.zzu.ehome.ehomefordoctor.app.Constans;
import com.zzu.ehome.ehomefordoctor.db.EHomeDao;
import com.zzu.ehome.ehomefordoctor.db.EHomeDaoImpl;
import com.zzu.ehome.ehomefordoctor.entity.ECGDynamicBean;
import com.zzu.ehome.ehomefordoctor.entity.StaticBean;
import com.zzu.ehome.ehomefordoctor.entity.StaticDate;
import com.zzu.ehome.ehomefordoctor.mvp.presenter.DynamicPresenter;
import com.zzu.ehome.ehomefordoctor.mvp.view.IDynamicView;
import com.zzu.ehome.ehomefordoctor.utils.DateUtils;
import com.zzu.ehome.ehomefordoctor.view.ProgressStateLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mersens on 2016/10/25.
 */

public class DynamicFragment extends BaseFragment implements IDynamicView {
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.progressStateLayout)
    ProgressStateLayout progressStateLayout;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private View mView;
    private DynamicPresenter presenter;
    private String userid,cardno;
    public static final String USERID="userid";
    public static final String CARDNO = "cardno";
    private ECGReportAdapter adapter;
    private List<StaticBean> list;
    private EHomeDao dao;

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
        userid=getArguments().getString(USERID);
        cardno=getArguments().getString(CARDNO);
        presenter=new DynamicPresenter(this);
        presenter.getDynamicData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getDynamicData();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final StaticBean item = adapter.getItem(position);
                Intent i = new Intent(getActivity(), ECGDetailsActivity.class);
                i.putExtra("Result", item.getECGResult());
                i.putExtra("Download", item.getReportURL());
                i.putExtra("ReportType", item.getReportType());
                i.putExtra("username", item.getRealName());
                i.putExtra("time", DateUtils.StringPattern(item.getReportTime(),
                        "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd"));
                startActivity(i);
            }
        });
    }

    public static Fragment getInstance(String userid,String cardno) {
        DynamicFragment fragment=new DynamicFragment();
        Bundle bundle=new Bundle();
        bundle.putString(USERID,userid);

        bundle.putString(CARDNO, cardno);
        fragment.setArguments(bundle);
        return fragment ;
    }



    @Override
    public String getUserid() {

        return userid;
    }

    @Override
    public String getNo() {
        return cardno;
    }

    @Override
    public <T> void onSuccess(T t) {
        swipeRefreshLayout.setRefreshing(false);
        list=(List<StaticBean>)t;
            if(list!=null && list.size()>0){
                adapter = new ECGReportAdapter(getActivity(), list);
                listView.setAdapter(adapter);
                progressStateLayout.showContent();
            }else{
                progressStateLayout.showEmpty(R.mipmap.icon_none,"暂无心电的报告数据");
            }
        }


    @Override
    public void onError(String error) {
        swipeRefreshLayout.setRefreshing(false);
        progressStateLayout.showEmpty(R.mipmap.icon_none,"数据加载失败"+error);
    }


}
