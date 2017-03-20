package com.zzu.ehome.ehomefordoctor.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.platform.comapi.map.M;
import com.bumptech.glide.Glide;
import com.zzu.ehome.ehomefordoctor.R;

import com.zzu.ehome.ehomefordoctor.app.Constans;
import com.zzu.ehome.ehomefordoctor.entity.MedicalBean;
import com.zzu.ehome.ehomefordoctor.mvp.presenter.MeidicalReportDesPresenter;
import com.zzu.ehome.ehomefordoctor.mvp.presenter.MeidicalReportInquiryPresenter;
import com.zzu.ehome.ehomefordoctor.mvp.view.IMeidicalReportDesView;
import com.zzu.ehome.ehomefordoctor.utils.DateUtils;
import com.zzu.ehome.ehomefordoctor.view.HeadView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imageloader.core.ImageLoader;


import static com.zzu.ehome.ehomefordoctor.R.id.edt_time;
import static com.zzu.ehome.ehomefordoctor.R.id.listView;
import static com.zzu.ehome.ehomefordoctor.activity.MedicalRecordActivity.TARGETID;


/**
 * Created by Administrator on 2016/12/7.
 */

public class MedicalExaminationDesActivity extends BaseActivity implements IMeidicalReportDesView {
    private Intent mIntent;
    private String id, userid;
    private GridAdapter mAdapter;
    @BindView(edt_time)
    TextView edtTime;
    @BindView(R.id.lljzhen)
    LinearLayout lljzhen;
    @BindView(R.id.edt_jzdw)
    TextView edtJzdw;
    @BindView(R.id.tvname)
    TextView tvname;
    @BindView(R.id.result_recycler)
    RecyclerView resultRecycler;
    @BindView(R.id.scrollView2)
    ScrollView scrollView2;
    private MeidicalReportDesPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_examination_layout);
        ButterKnife.bind(this);
        mIntent=this.getIntent();
        presenter=new MeidicalReportDesPresenter(this);
        id=mIntent.getStringExtra("ID");
        userid=mIntent.getStringExtra(TARGETID);
        init();
    }

    @Override
    public void init() {
        setLeftWithTitleViewMethod(R.mipmap.icon_arrow_left, "体检报告", new HeadView.OnLeftClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        presenter.doMeidicalDes();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public <T> void onSuccess(T t) {
        List<MedicalBean> meList = (List<MedicalBean>) t;
        String checktime = DateUtils.StringPattern(meList.get(0).getCheckTime(), "yyyy/MM/dd HH:mm:ss", "yyyy/M/dd");
        edtTime.setText(checktime);
        edtJzdw.setText(meList.get(0).getInstituteName());
        tvname.setText(meList.get(0).getUserName());
        String imgstr = meList.get(0).getReportImage();
        if (imgstr.indexOf(",") >= 0) {

            String[] strs = imgstr.split("\\,");
            List<String> images = new ArrayList<String>();
            for (int m = 0; m < strs.length; m++) {
                String imgurl = Constans.WEBSERVICE_URL + strs[m].replace("~", "").replace("\\", "/");
                images.add(imgurl);
            }
            mAdapter = new GridAdapter(images, MedicalExaminationDesActivity.this);
            resultRecycler.setLayoutManager(new GridLayoutManager(MedicalExaminationDesActivity.this, 3));
            resultRecycler.setAdapter(mAdapter);
        } else {
            if (!TextUtils.isEmpty(imgstr)) {
//                                llcheckr.setVisibility(View.VISIBLE);
                List<String> images2 = new ArrayList<String>();
                String imgurl2 = Constans.WEBSERVICE_URL + imgstr.replace("~", "").replace("\\", "/");
                images2.add(imgurl2);
                mAdapter = new GridAdapter(images2, MedicalExaminationDesActivity.this);
                resultRecycler.setLayoutManager(new GridLayoutManager(MedicalExaminationDesActivity.this, 3));
                resultRecycler.setAdapter(mAdapter);
            }
        }
    }

    @Override
    public String getUserId() {
        return userid;
    }

    @Override
    public void onError(String error) {

    }

    private class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {
        private Context mContext;
        private ImageLoader imageLoader;

        public List<String> getmList() {
            return mList;
        }

        public void setmList(List<String> mList) {
            this.mList = mList;
        }

        private List<String> mList;

        public GridAdapter(List<String> list, Context context) {
            super();
            this.mList = list;
            this.mContext = context;
            imageLoader = ImageLoader.getInstance();
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result_report, parent, false);

            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            Glide.with(MedicalExaminationDesActivity.this)
                    .load(mList.get(position))
                    .centerCrop()
                    .into(holder.imageView);
//            imageLoader.displayImage(mList.get(position),holder.imageView);
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
