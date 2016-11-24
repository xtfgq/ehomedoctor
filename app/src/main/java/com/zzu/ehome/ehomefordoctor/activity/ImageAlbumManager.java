package com.zzu.ehome.ehomefordoctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.view.PhotoViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imageloader.core.ImageLoader;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Mersens on 2016/10/26.
 */

public class ImageAlbumManager extends BaseActivity implements PhotoViewAttacher.OnViewTapListener {
    @BindView(R.id.viewpager)
    PhotoViewPager viewpager;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_)
    TextView tv;
    @BindView(R.id.tv_num)
    TextView tvNum;
    private String[] strs = null;
    private int index = 0;
    ArrayList<String> listUrls;
    private int size;
    private int type;
    private PhotoView mImageView;
    private PhotoViewAttacher mAttacher;
    private PhotoViewAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public void init() {
        Intent intent = this.getIntent();
        listUrls = intent.getStringArrayListExtra("imgs");
        index = intent.getIntExtra("position", 0);
        type = intent.getIntExtra("type", 0);
        size = listUrls.size();
        viewpager.setOnPageChangeListener(mListener);
        mAdapter = new PhotoViewAdapter();
        viewpager.setAdapter(mAdapter);
        viewpager.setCurrentItem(index);
        tvCount.setText(size + "");
        tvNum.setText(index + 1 + "");
    }

    @Override
    public void onViewTap(View view, float x, float y) {

    }

    class PhotoViewAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = container.inflate(ImageAlbumManager.this,
                    R.layout.layout_image_detail, null);
            mImageView = (PhotoView) view.findViewById(R.id.image);
            //给图片增加点击事件
            mAttacher = new PhotoViewAttacher(mImageView);
            mAttacher.setOnViewTapListener(ImageAlbumManager.this);
            if (type == 1) {
                Glide.with(ImageAlbumManager.this).load(listUrls.get(position)).into(mImageView);
            } else {
                ImageLoader.getInstance().displayImage("file://" + listUrls.get(position), mImageView);
            }
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
            return size;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private ViewPager.OnPageChangeListener mListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            tvNum.setText((position + 1) + "");

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

}
