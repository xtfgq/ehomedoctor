package com.zzu.ehome.ehomefordoctor.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.adapter.BaseListAdapter;
import com.zzu.ehome.ehomefordoctor.adapter.OcrDetailAdapter;
import com.zzu.ehome.ehomefordoctor.entity.AdviceBean;
import com.zzu.ehome.ehomefordoctor.entity.BloodRoutine;
import com.zzu.ehome.ehomefordoctor.mvp.presenter.InspectionReportDetialPresenter;
import com.zzu.ehome.ehomefordoctor.mvp.view.InspectionReportDetialView;
import com.zzu.ehome.ehomefordoctor.view.HeadView;
import com.zzu.ehome.ehomefordoctor.view.MyScrollListView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.id.list;
import static com.zzu.ehome.ehomefordoctor.R.id.llad;
import static com.zzu.ehome.ehomefordoctor.R.id.lladnone;

/**
 * Created by Mersens on 2016/10/29.
 */

public class InspectionReportDetailActivity extends BaseActivity implements InspectionReportDetialView {
    @BindView(R.id.lilstView)
    MyScrollListView lilstView;
    private String title,type,id,tvtitle;
    private String mUserNo;
    private InspectionReportDetialPresenter presenter;
    private OcrDetailAdapter adapter;
    private TextView textViewtitle;
    HashMap<String, Object> mMap=new HashMap<>();
    private LinearLayout llad,lladnone;
    private ListView listView2;
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
        tvtitle=this.getIntent().getStringExtra("TypeTitle");
        type=this.getIntent().getStringExtra("Type");
        id=this.getIntent().getStringExtra("RecordID");
        mUserNo=getIntent().getStringExtra("UserNo");
        textViewtitle=(TextView)findViewById(R.id.tvtitle);
        llad=(LinearLayout)findViewById(R.id.llad);
        lladnone=(LinearLayout)findViewById(R.id.lladnone);
        listView2=(ListView)findViewById(R.id.lilstView2);
        textViewtitle.setText(tvtitle);
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
        mMap=(HashMap)t;
        if(mMap!=null) {
            List<BloodRoutine> list = (List<BloodRoutine>) mMap.get("br");
            if (list != null && list.size() > 0) {
                adapter = new OcrDetailAdapter(InspectionReportDetailActivity.this, list);
                lilstView.setAdapter(adapter);
            }
            if(mMap.containsKey("advice")){
                List<AdviceBean> list2=(List<AdviceBean>) mMap.get("advice");
                if (list2.size() > 0) {
                    llad.setVisibility(View.VISIBLE);
                    lladnone.setVisibility(View.GONE);
                    AdviceDetailAdapter mAadpter2 = new AdviceDetailAdapter(InspectionReportDetailActivity.this, list2);
                    listView2.setAdapter(mAadpter2);

                } else {
                    llad.setVisibility(View.GONE);
                    lladnone.setVisibility(View.VISIBLE);
                }
            }

        }
    }

    @Override
    public void onError() {
        Toast.makeText(this, "数据请求失败", Toast.LENGTH_SHORT).show();

    }
    public class AdviceDetailAdapter extends BaseListAdapter<AdviceBean> {
        private List<AdviceBean> list;



        public AdviceDetailAdapter(Context context, List<AdviceBean> objects) {
            super(context, objects);
            this.list = objects;

        }

        @Override
        public View getGqView(int position, View convertView, ViewGroup parent) {
            View mView = getInflater().inflate(R.layout.item_new_instruction, null);
            TextView name = (TextView) mView.findViewById(R.id.tv_content);
            TextView tvDescription = (TextView) mView.findViewById(R.id.tvLast);
            TextView tvDate = (TextView) mView.findViewById(R.id.tvDate);
            TextView tvnum = (TextView) mView.findViewById(R.id.tvnum);
            View view=(View) mView.findViewById(R.id.vvview);
            tvnum.setText("  " + (position + 1) + ".");
            tvDescription.setText("体检结论:" + list.get(position).getDescription());
            name.setText(list.get(position).getAdvice());
            if(position==list.size()-1){
                view.setVisibility(View.GONE);
            }else{
                view.setVisibility(View.VISIBLE);
            }
            return mView;
        }
    }
}
