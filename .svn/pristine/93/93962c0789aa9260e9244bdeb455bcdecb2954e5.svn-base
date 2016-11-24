package com.zzu.ehome.ehomefordoctor.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.activity.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mersens on 2016/9/28.
 */

public class MsgFragment extends BaseFragment {
    @BindView(R.id.btnlogin)
    Button btnlogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_msg, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    public static Fragment getInstance() {

        return new MsgFragment();
    }

    @Override
    public void init() {

    }

    @OnClick(R.id.btnlogin)
    public void onClick() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }
}
