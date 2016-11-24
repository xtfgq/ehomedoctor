package com.zzu.ehome.ehomefordoctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.view.HeadView;
import com.zzu.ehome.ehomefordoctor.view.PhotoViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Mersens on 2016/10/26.
 */

public class ImageECGDetail extends BaseActivity implements PhotoViewAttacher.OnViewTapListener {


    @BindView(R.id.viewpager)
    PhotoViewPager viewpager;
    private PhotoViewAttacher mAttacher;
    private PhotoView mImageView;
    private PhotoViewAdapter mAdapter;
    private String imgstr = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detal_des);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public void init() {
        imgstr = getIntent().getStringExtra("imurl");
        setLeftViewMethod(R.mipmap.icon_arrow_left, new HeadView.OnLeftClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        mAdapter = new PhotoViewAdapter();
        viewpager.setAdapter(mAdapter);
        viewpager.setCurrentItem(0);
    }

    @Override
    public void onViewTap(View view, float x, float y) {

    }
    class PhotoViewAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = container.inflate(ImageECGDetail.this,
                    R.layout.layout_image_detail, null);
            mImageView = (PhotoView) view.findViewById(R.id.image);
            mAttacher = new PhotoViewAttacher(mImageView);
            mAttacher.setOnViewTapListener(ImageECGDetail.this);
            Glide.with(ImageECGDetail.this).load(imgstr).into(mImageView);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            mAttacher = null;
            container.removeView((View) object);

        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
