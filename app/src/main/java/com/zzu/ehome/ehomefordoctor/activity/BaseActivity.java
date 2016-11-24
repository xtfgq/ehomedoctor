package com.zzu.ehome.ehomefordoctor.activity;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.Window;


/**
 * Created by Mersens on 2016/9/28.
 */

public abstract class BaseActivity extends SupperBaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setTranslucentStatus();
    }
}
