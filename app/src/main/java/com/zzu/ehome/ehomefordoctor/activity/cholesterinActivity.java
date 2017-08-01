package com.zzu.ehome.ehomefordoctor.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.emilsjolander.components.stickylistheaders.StickyListHeadersListView;
import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.adapter.CholChatAdapter;

import com.zzu.ehome.ehomefordoctor.entity.CholHistoryBean;
import com.zzu.ehome.ehomefordoctor.entity.CholRes;

import com.zzu.ehome.ehomefordoctor.mvp.presenter.ChHistoryPresenter;
import com.zzu.ehome.ehomefordoctor.mvp.presenter.CholesterinDataPresenter;

import com.zzu.ehome.ehomefordoctor.mvp.view.ChHistoryView;
import com.zzu.ehome.ehomefordoctor.mvp.view.CholesterinDataView;

import com.zzu.ehome.ehomefordoctor.view.CholestenoneView;
import com.zzu.ehome.ehomefordoctor.view.HeadView;


import org.xclcharts.chart.PointD;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zzu.ehome.ehomefordoctor.activity.MedicalRecordActivity.TARGETID;
import static com.zzu.ehome.ehomefordoctor.activity.MedicalRecordActivity.UserNo;


/**
 * Created by Administrator on 2016/10/27.
 */

public class CholesterinActivity extends BaseActivity implements CholesterinDataView,ChHistoryView,StickyListHeadersListView.OnHeaderClickListener, StickyListHeadersListView.OnLoadingMoreLinstener {
    private LinearLayout heardchat;
    private int page;
    @BindView(R.id.lv_ua)
    StickyListHeadersListView listview;
    private LayoutInflater inflater;
    private CholestenoneView mChart;

    private TextView tvnodata;

    private RelativeLayout moredata;
    private View progressBarView;
    private CholChatAdapter mAadpter;
    private TextView progressBarTextView;

    private List<CholHistoryBean> mList=new ArrayList<>();
    private AnimationDrawable loadingAnimation; //加载更多，动画
    private boolean isLoading = false;
    private String mTargetId,mUserNo;
    private CholesterinDataPresenter mCholesterinDataPresenter;

    private ChHistoryPresenter mChHistoryPresenter;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        page = 1;
        setContentView(R.layout.activity_cholestenone_layout);
        ButterKnife.bind(this);
        init();


    }

    @Override
    public void init() {
        setLeftWithTitleViewMethod(R.mipmap.icon_arrow_left, "胆固醇", new HeadView.OnLeftClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });

        mTargetId = getIntent().getStringExtra(TARGETID);
        mUserNo = getIntent().getStringExtra(UserNo);
        mCholesterinDataPresenter=new CholesterinDataPresenter(this);
        mChHistoryPresenter=new ChHistoryPresenter(this);
        inflater = LayoutInflater.from(this);
        mAadpter = new CholChatAdapter(CholesterinActivity.this);
        heardchat = (LinearLayout) inflater.inflate(R.layout.layout_cholestenone_new_chat, null);
        mChart = (CholestenoneView) heardchat.findViewById(R.id.chart);
        tvnodata = (TextView) heardchat.findViewById(R.id.tvnodate);
        moredata = (RelativeLayout) inflater.inflate(R.layout.moredata, null);
        progressBarView = (View) moredata.findViewById(R.id.loadmore_foot_progressbar);
        progressBarTextView = (TextView) moredata.findViewById(R.id.loadmore_foot_text);
        loadingAnimation = (AnimationDrawable) progressBarView.getBackground();
        listview.addHeaderView(heardchat);
        listview.addFooterView(moredata);
        listview.setOnHeaderClickListener(this);
        listview.setLoadingMoreListener(this);
        listview.setAdapter(mAadpter);
        mCholesterinDataPresenter.getChData();
        mChHistoryPresenter.getChHistoryData();

    }






    @Override
    public void onHeaderClick(StickyListHeadersListView l, View header, int itemPosition, long headerId, boolean currentlySticky) {

    }

    @Override
    public void OnLoadingMore() {
        progressBarView.setVisibility(View.VISIBLE);
        progressBarTextView.setVisibility(View.VISIBLE);
        loadingAnimation.start();
        page++;
        if (!isLoading) {
            isLoading = true;
            mChHistoryPresenter.getChHistoryData();
        }

    }

    public void loadingFinished() {

        if (null != loadingAnimation && loadingAnimation.isRunning()) {
            loadingAnimation.stop();
        }
        progressBarView.setVisibility(View.INVISIBLE);
        progressBarTextView.setVisibility(View.INVISIBLE);
        isLoading = false;
        mAadpter.notifyDataSetChanged();
    }

    @Override
    public <T> void onSuccess(T t) {
        List<CholRes> list = (List<CholRes>) t;
        if(list.get(0).getmResultBean()!=null){
            tvnodata.setVisibility(View.VISIBLE);
            return;
        }
        List<PointD> linePoint2 = new ArrayList<PointD>();

        for (int i = 0; i < list.size(); i++) {

            linePoint2.add(new PointD(Double.valueOf(i+1), Double.valueOf(list.get(i).getCHOL())));
        }

        mChart.setX(list.size());
        mChart.refresh(linePoint2);
    }

    @Override
    public String getUserId() {
        return mTargetId;
    }

    @Override
    public String getUserNo() {
        return mUserNo;
    }

    @Override
    public String getStartTime() {
        return "";
    }

    @Override
    public String getEndTime() {
        return "";
    }

    @Override
    public String getType() {
        return "T";
    }

    @Override
    public String getTop() {
        return "7";
    }

    @Override
    public String getPageSize() {
        return 10+"";
    }

    @Override
    public String getPageIndex() {
        return page+"";
    }

    @Override
    public String getTypeHistory() {
        return "Cholestenone";
    }

    @Override
    public <T> void onResultSuccess(T t) {
        List<CholHistoryBean> list = (List<CholHistoryBean>) t;
        if (list.get(0).getmResultBean() != null ) {
            loadingFinished();
            return;
        }

        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                mList.add(list.get(i));
            }
            mAadpter.setList(mList);

            loadingFinished();
        }

    }
}