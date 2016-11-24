package com.zzu.ehome.ehomefordoctor.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.app.Constans;
import com.zzu.ehome.ehomefordoctor.entity.TreatmentInquirywWithPage;
import com.zzu.ehome.ehomefordoctor.view.HeadView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mersens on 2016/10/26.
 */

public class JzjlDetalActivity extends BaseActivity {
    @BindView(R.id.tv_hospital)
    TextView tvHospital;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.edt_time)
    TextView edtTime;
    @BindView(R.id.lljzhen)
    RelativeLayout lljzhen;
    @BindView(R.id.edt_jzjg)
    TextView edtJzjg;
    @BindView(R.id.edt_yyjy)
    TextView edtYyjy;
    @BindView(R.id.result_recycler)
    RecyclerView resultRecycler;
    private TreatmentInquirywWithPage records;
    private Intent mIntent;
    private GridAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_medical_records_des);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public void init() {
        setLeftWithTitleViewMethod(R.mipmap.icon_arrow_left, "就诊记录", new HeadView.OnLeftClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        mIntent=this.getIntent();
        records=(TreatmentInquirywWithPage)mIntent.getSerializableExtra("MedicalRecords");
        tvName.setText(records.getHosname());
        edtTime.setText(records.getTime().split(" ")[0]);
        edtJzjg.setText(records.getZhenduan().replace("&lt;", "<").replace("&gt;", ">").replace("&amp;", "&"));
        edtYyjy.setText(records.getOpinion().replace("&lt;", "<").replace("&gt;", ">").replace("&amp;", "&"));
        String imgstr = records.getImages();
        List<String> mList = new ArrayList<String>();
        if (imgstr.indexOf(",") >= 0) {
            String[] strs = imgstr.split("\\,");
            for (int m = 0; m < strs.length; m++) {
                String imgurl = Constans.EhomeURL + strs[m].replace("~", "").replace("\\", "/");
                mList.add(imgurl);
            }
        } else {
            if (!TextUtils.isEmpty(imgstr)) {
                String imgurl2 = Constans.EhomeURL + imgstr.replace("~", "").replace("\\", "/");
                mList.add(imgurl2);
            }

        }
        mAdapter = new GridAdapter(mList, JzjlDetalActivity.this);
        resultRecycler.setLayoutManager(new GridLayoutManager(JzjlDetalActivity.this, 3));
        resultRecycler.setAdapter(mAdapter);
    }

     class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {
        private Context mContext;


        public List<String> getmList() {
            return mList;
        }

        public void setmList(List<String> mList) {
            this.mList = mList;
        }

        private List<String> mList;

        public GridAdapter(List<String> list, Context context) {
            this.mList = list;
            this.mContext = context;

        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result_report, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            Glide.with(JzjlDetalActivity.this)
                    .load(mList.get(position))
                    .centerCrop()
                    .into(holder.imageView);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ImageAlbumManager.class);
                    intent.putStringArrayListExtra("imgs", (ArrayList<String>) mList);
                    intent.putExtra("position", position);
                    intent.putExtra("type", 1);
                    mContext.startActivity(intent);
                }
            });


        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.image);

            }
        }
    }

}
