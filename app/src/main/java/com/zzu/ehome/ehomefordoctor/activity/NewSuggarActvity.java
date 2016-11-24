package com.zzu.ehome.ehomefordoctor.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.emilsjolander.components.stickylistheaders.StickyListHeadersListView;
import com.zzu.ehome.ehomefordoctor.R;

import com.zzu.ehome.ehomefordoctor.adapter.BloodSuggarChatAdapter;
import com.zzu.ehome.ehomefordoctor.entity.BloodSuggarBean;

import com.zzu.ehome.ehomefordoctor.entity.BloodSuggarRes;
import com.zzu.ehome.ehomefordoctor.mvp.model.DataTimeType;

import com.zzu.ehome.ehomefordoctor.mvp.presenter.SuggarHistoryPresenter;
import com.zzu.ehome.ehomefordoctor.mvp.presenter.SuggarPresenter;
import com.zzu.ehome.ehomefordoctor.mvp.view.ISuggarDateView;
import com.zzu.ehome.ehomefordoctor.mvp.view.ISuggarHistoryView;
import com.zzu.ehome.ehomefordoctor.utils.CommonUtils;
import com.zzu.ehome.ehomefordoctor.view.HeadView;
import com.zzu.ehome.ehomefordoctor.view.SuggarView;

import org.xclcharts.chart.PointD;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zzu.ehome.ehomefordoctor.activity.MedicalRecordActivity.TARGETID;
import static com.zzu.ehome.ehomefordoctor.activity.MedicalRecordActivity.UserNo;


/**
 * Created by Administrator on 2016/10/26.
 */

public class NewSuggarActvity extends BaseActivity  implements ISuggarDateView,ISuggarHistoryView,StickyListHeadersListView.OnHeaderClickListener, StickyListHeadersListView.OnLoadingMoreLinstener{
    @BindView(R.id.lv_temp)
    StickyListHeadersListView listView;
    @BindView(R.id.radioGroup)
    RadioGroup group;
    @BindView(R.id.rb_day)
    RadioButton rbday;
    @BindView(R.id.rb_week)
    RadioButton rbweek;
    @BindView(R.id.rb_month)
    RadioButton rbmonth;
    private LayoutInflater inflater;

    private SuggarView mChart;
    private LinearLayout heardchat, lltmp;
    private TextView tvnodata;
    private RelativeLayout moredata;
    private View progressBarView;
    private TextView progressBarTextView;
    private AnimationDrawable loadingAnimation; //加载更多，动画
    private boolean isLoading = false;
    private BloodSuggarChatAdapter mAadpter;
    private String mTargetId;
    private String mUserNo;
    DataTimeType mDataTimeType;
    private LinkedList<String> labels = new LinkedList<String>();
    //最近一周日期
    private List<String> week=new ArrayList<>();
    //一个月日期
    private List<String> month=new ArrayList<>();
    private SuggarPresenter presenter;
    private SuggarHistoryPresenter historyPresenter;
    private List<BloodSuggarRes> mList=new ArrayList<BloodSuggarRes>();
    private int page=1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_temp_chat);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public void init() {

        mTargetId=getIntent().getStringExtra(TARGETID);
        mUserNo=getIntent().getStringExtra(UserNo);
        mDataTimeType=DataTimeType.DAY;
        inflater = LayoutInflater.from(NewSuggarActvity.this);
        setLeftWithTitleViewMethod(R.mipmap.icon_arrow_left, "血糖", new HeadView.OnLeftClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        week=CommonUtils.getDays(sdf.format(CommonUtils.changeDate(-6).getTime()), sdf.format(CommonUtils.changeDate(-1).getTime() + 60 * 60 * 24 * 1000));
        month=CommonUtils.getDays(sdf.format(CommonUtils.changeDate(-29).getTime()), sdf.format(CommonUtils.changeDate(-1).getTime() + 60 * 60 * 24 * 1000));
        heardchat = (LinearLayout) inflater.inflate(R.layout.new_suggar, null);
        mChart = (SuggarView) heardchat.findViewById(R.id.chart);
        mAadpter = new BloodSuggarChatAdapter(NewSuggarActvity.this);
        tvnodata = (TextView) heardchat.findViewById(R.id.tvnodate);
        moredata = (RelativeLayout) inflater.inflate(R.layout.moredata, null);
        progressBarView = (View) moredata.findViewById(R.id.loadmore_foot_progressbar);
        progressBarTextView = (TextView) moredata.findViewById(R.id.loadmore_foot_text);
        loadingAnimation = (AnimationDrawable) progressBarView.getBackground();
        listView.addHeaderView(heardchat);
        listView.addFooterView(moredata);
        listView.setOnHeaderClickListener(this);
        listView.setLoadingMoreListener(this);
        listView.setAdapter(mAadpter);
        presenter=new SuggarPresenter(this);
        presenter.getSuggarData();
        historyPresenter=new SuggarHistoryPresenter(this);
        historyPresenter.getSuggarHistoryData();

    }
    @OnClick({R.id.rb_day, R.id.rb_week,R.id.rb_month})

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_day:
                rbday.setChecked(true);
                rbday.setTextColor(getResources().getColor(R.color.base_color_text_white));
                rbweek.setChecked(false);
                rbweek.setTextColor(getResources().getColor(R.color.actionbar_color));
                rbmonth.setChecked(false);
                rbmonth.setTextColor(getResources().getColor(R.color.actionbar_color));
                mDataTimeType=DataTimeType.DAY;
                presenter.getSuggarData();
                break;
            case R.id.rb_week:
                rbday.setChecked(false);
                rbday.setTextColor(getResources().getColor(R.color.actionbar_color));
                rbweek.setChecked(true);
                rbweek.setTextColor(getResources().getColor(R.color.base_color_text_white));
                rbmonth.setChecked(false);
                rbmonth.setTextColor(getResources().getColor(R.color.actionbar_color));
                mDataTimeType=DataTimeType.WEEK;
                presenter.getSuggarData();
                break;
            case R.id.rb_month:
                rbday.setChecked(false);
                rbday.setTextColor(getResources().getColor(R.color.actionbar_color));
                rbweek.setChecked(false);
                rbweek.setTextColor(getResources().getColor(R.color.actionbar_color));
                rbmonth.setChecked(true);
                rbmonth.setTextColor(getResources().getColor(R.color.base_color_text_white));
                mDataTimeType=DataTimeType.MONTH;
                presenter.getSuggarData();
                break;
        }
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
            historyPresenter.getSuggarHistoryData();
        }
    }

    @Override
    public <T> void onSuccess(T t) {
        //空腹血糖
        List<PointD> linePoint1 = new ArrayList<PointD>();
        //餐后血糖
        List<PointD> linePoint2 = new ArrayList<PointD>();
        //随机血糖
        List<PointD> linePoint3 = new ArrayList<PointD>();
        List<BloodSuggarBean> list=(List<BloodSuggarBean>) t;
        if(list.get(0).getmResultBean()!=null){
            mChart.refresh(linePoint1, linePoint2,linePoint3);
            tvnodata.setVisibility(View.VISIBLE);
            return;
        }
        tvnodata.setVisibility(View.GONE);

        switch (mDataTimeType){
            case DAY:
                labels=CommonUtils.getDayLables();
                mChart.setX(labels, 24);
                setDayChat(mChart,list,linePoint1,linePoint2,linePoint3);
                break;
            case WEEK:
                labels=CommonUtils.getWeekLables();
                mChart.setX(labels, 6);
                setWeekChat(mChart,list,linePoint1,linePoint2,linePoint3);
                break;
            case MONTH:
                labels=CommonUtils.getMonthLables();
                mChart.setX(labels, 30);
                setMonthChat(mChart,list,linePoint1,linePoint2,linePoint3);
                break;
        }
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
        String startTime="";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      switch (mDataTimeType){
          case DAY:
              startTime = sdf.format(CommonUtils.changeDate(-1).getTime() + 60 * 60 * 24 * 1000);
          break;
          case WEEK:
              startTime = sdf.format(CommonUtils.changeDate(-6).getTime());
              break;
          case MONTH:
              startTime = sdf.format(CommonUtils.changeDate(-29).getTime());
              break;
      }
        return startTime;
    }

    @Override
    public String getEndTime() {
        String endTime="";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        switch (mDataTimeType){
            case DAY:
                endTime = sdf.format(CommonUtils.changeDate(-1).getTime() + 60 * 60 * 24 * 1000 * 2);
                break;
            case WEEK:
                endTime = sdf.format((new Date()).getTime() + 60 * 60 * 24 * 1000);
                break;
            case MONTH:
                endTime = sdf.format(CommonUtils.changeDate(-1).getTime() + 60 * 60 * 24 * 1000 * 2);
                break;
        }
        return endTime;
    }

    @Override
    public String getType() {
        String type="";
        switch (mDataTimeType){
            case DAY:
                type="H";
                break;
            case WEEK:
               type="D";
                break;
            case MONTH:
               type="D";
                break;
        }
        return type;
    }
    private void setDayChat(SuggarView mChart,List<BloodSuggarBean> list,List<PointD> linePoint1,List<PointD> linePoint2,List<PointD> linePoint3){
        for (BloodSuggarBean th : list) {
            if (Integer.valueOf(th.getMonitorPoint())==1) {
                Double xd = Double.valueOf(th.getMonitorTime().split("\\ ")[1]);
                Double ydH=Double.valueOf(th.getBloodSugarValue());
                linePoint1.add(new PointD(xd, ydH));
            }
            else if(Integer.valueOf(th.getMonitorPoint())==0){
                Double xd = Double.valueOf(th.getMonitorTime().split("\\ ")[1]);
                Double ydl= Double.valueOf(th.getBloodSugarValue());
                linePoint2.add(new PointD(xd, ydl));
            }else{
                Double xd = Double.valueOf(th.getMonitorTime().split("\\ ")[1]);
                Double ydl=  Double.valueOf(th.getBloodSugarValue());
                linePoint3.add(new PointD(xd, ydl));
            }

        }
        mChart.refresh(linePoint1, linePoint2,linePoint3);

    }
    private void setWeekChat(SuggarView mChart,List<BloodSuggarBean> list,List<PointD> linePoint1,List<PointD> linePoint2,List<PointD> linePoint3){

        for (BloodSuggarBean th : list) {
            if (Integer.valueOf(th.getMonitorPoint())==1) {
                Double xd = CommonUtils.position(th.getMonitorTime().split("\\ ")[0], week);
                Double ydH= Double.valueOf(th.getBloodSugarValue());
                linePoint1.add(new PointD(xd, ydH));
            } else if(Integer.valueOf(th.getMonitorPoint())==0){
                Double xd = CommonUtils.position(th.getMonitorTime().split("\\ ")[0], week);
                Double ydl= Double.valueOf(th.getBloodSugarValue());
                linePoint2.add(new PointD(xd, ydl));
            }
            else {
                Double xd = CommonUtils.position(th.getMonitorTime().split("\\ ")[0], week);
                Double ydl=Double.valueOf(th.getBloodSugarValue());
                linePoint3.add(new PointD(xd, ydl));

            }
        }
        mChart.refresh(linePoint1, linePoint2,linePoint3);

    }
    private void setMonthChat(SuggarView mChart,List<BloodSuggarBean> list,List<PointD> linePoint1,List<PointD> linePoint2,List<PointD> linePoint3){

        for (BloodSuggarBean th : list) {
            if (Integer.valueOf(th.getMonitorPoint())==1) {
                Double xd = CommonUtils.position(th.getMonitorTime().split("\\ ")[0], month);
                Double ydH= Double.valueOf(th.getBloodSugarValue());
                linePoint1.add(new PointD(xd, ydH));
            } else if(Integer.valueOf(th.getMonitorPoint())==0){
                Double xd = CommonUtils.position(th.getMonitorTime().split("\\ ")[0], month);
                Double ydl= Double.valueOf(th.getBloodSugarValue());
                linePoint2.add(new PointD(xd, ydl));
            }
            else {
                Double xd = CommonUtils.position(th.getMonitorTime().split("\\ ")[0], month);
                Double ydl = Double.valueOf(th.getBloodSugarValue());
                linePoint3.add(new PointD(xd, ydl));
            }

        }
        mChart.refresh(linePoint1, linePoint2,linePoint3);

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
        return "BloodSugar";
    }

    @Override
    public <T> void onResultSuccess(T t) {
        List<BloodSuggarRes> list=(List<BloodSuggarRes>) t;
        if(list.get(0).getmResultBean()!=null){
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
    public void loadingFinished() {

        if (null != loadingAnimation && loadingAnimation.isRunning()) {
            loadingAnimation.stop();
        }
        progressBarView.setVisibility(View.INVISIBLE);
        progressBarTextView.setVisibility(View.INVISIBLE);
        isLoading = false;

        mAadpter.notifyDataSetChanged();
    }
}
