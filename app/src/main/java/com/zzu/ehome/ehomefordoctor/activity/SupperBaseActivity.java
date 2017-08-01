package com.zzu.ehome.ehomefordoctor.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.app.App;
import com.zzu.ehome.ehomefordoctor.entity.User;
import com.zzu.ehome.ehomefordoctor.reciver.EventType;
import com.zzu.ehome.ehomefordoctor.reciver.RxBus;
import com.zzu.ehome.ehomefordoctor.utils.SharePreferenceUtil;
import com.zzu.ehome.ehomefordoctor.utils.SystemStatusManager;
import com.zzu.ehome.ehomefordoctor.view.CommonDialog;
import com.zzu.ehome.ehomefordoctor.view.CustomProgressDialog;
import com.zzu.ehome.ehomefordoctor.view.DialogTips;
import com.zzu.ehome.ehomefordoctor.view.HeadView;

import kr.co.namee.permissiongen.PermissionGen;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

import static com.igexin.sdk.PushService.context;
import static com.zzu.ehome.ehomefordoctor.app.App.mList;

/**
 * Created by Administrator on 2016/11/22.
 */

public abstract class SupperBaseActivity extends FragmentActivity {
    private Context mContext;
    private HeadView mHeadView;
    private CustomProgressDialog progressDialog = null;
    private CompositeSubscription compositeSubscription;
    private boolean isVisible=false;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    stopProgressDialog();
                    break;
            }
            super.handleMessage(msg);
        }
    };
    public void startTitleProgressDialog(String title){
        if (progressDialog == null) {
            progressDialog = CustomProgressDialog.createDialog(this);
            progressDialog.setMessage(title);
        }

        progressDialog.show();
        mHandler.sendEmptyMessageDelayed(0, 30000);
    }

    public void startProgressDialog() {
        if (progressDialog == null) {
            progressDialog = CustomProgressDialog.createDialog(this);
            //progressDialog.setMessage("正在加载中...");
        }

        progressDialog.show();
        mHandler.sendEmptyMessageDelayed(0, 8000);
    }

    public void stopProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopProgressDialog();
        compositeSubscription.unsubscribe();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        App.getInstance().addActivity(this);
        this.mContext = this;
        compositeSubscription = new CompositeSubscription();

        //监听订阅事件
        Subscription subscription = RxBus.getInstance().toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object event) {
                        if (event == null) {
                            return;
                        }

                        if (event instanceof EventType){
                            EventType type=(EventType)event;
                            if("rongyun".equals(type.getType())) {
                                App.getInstance().isOnline=false;

                            }

                        }

                    }
                });
        //subscription交给compositeSubscription进行管理，防止内存溢出
        compositeSubscription.add(subscription);

    }

    @Override
    protected void onResume() {
        super.onResume();
        isVisible=true;
        if(!App.getInstance().isOnline){
            ExitAlter(App.getInstance().getStackTopActivity());
        }
    }
    @Override
    protected void onStop() {
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
        isVisible = false;

    }

    /**
     * @param leftsrcid
     * @param title
     * @param rightsrcid
     * @param onleftclicklistener
     * @param onrightclicklistener
     * @author Mersens
     * setDefaultViewMethod--默认显示左侧按钮，标题和右侧按钮
     */
    public void setDefaultViewMethod(int leftsrcid, String title, int rightsrcid, HeadView.OnLeftClickListener onleftclicklistener, HeadView.OnRightClickListener onrightclicklistener) {
        mHeadView = (HeadView) findViewById(R.id.common_actionbar);
        mHeadView.init(HeadView.HeaderStyle.DEFAULT);
        mHeadView.setDefaultViewMethod(leftsrcid, title, rightsrcid, onleftclicklistener, onrightclicklistener);
    }

    public void setDefaultTXViewMethod(int leftsrcid, String title, String rightsrcid, HeadView.OnLeftClickListener onleftclicklistener, HeadView.OnRightClickListener onrightclicklistener) {
        mHeadView = (HeadView) findViewById(R.id.common_actionbar);
        mHeadView.init(HeadView.HeaderStyle.DEFAULT_TX);
        mHeadView.setDefaultTXViewMethod(leftsrcid, title, rightsrcid, onleftclicklistener, onrightclicklistener);
    }

    /**
     * @param title
     * @param rightsrcid
     * @param onRightClickListener
     * @author Mersens
     * setRightAndTitleMethod--显示右侧按钮和标题
     */
    public void setRightAndTitleMethod(String title, int rightsrcid, HeadView.OnRightClickListener onRightClickListener) {
        mHeadView = (HeadView) findViewById(R.id.common_actionbar);
        mHeadView.init(HeadView.HeaderStyle.RIGHTANDTITLE);
        mHeadView.setRightAndTitleMethod(title, rightsrcid, onRightClickListener);
    }


    /**
     * @param leftsrcid
     * @param title
     * @param onleftclicklistener
     * @author Mersens
     * setLeftWithTitleViewMethod--显示左侧按钮和标题
     */
    public void setLeftWithTitleViewMethod(int leftsrcid, String title, HeadView.OnLeftClickListener onleftclicklistener) {
        mHeadView = (HeadView) findViewById(R.id.common_actionbar);
        mHeadView.init(HeadView.HeaderStyle.LEFTANDTITLE);
        mHeadView.setLeftWithTitleViewMethod(leftsrcid, title, onleftclicklistener);
    }

    /**
     * @param title
     * @author Mersens
     * setOnlyTileViewMethod--只显示标题
     */
    public void setOnlyTileViewMethod(String title) {
        mHeadView = (HeadView) findViewById(R.id.common_actionbar);
        mHeadView.init(HeadView.HeaderStyle.ONLYTITLE);
        mHeadView.setOnlyTileViewMethod(title);
    }

    /**
     * @param leftsrcid
     * @param onleftclicklistener
     * @author Mersens
     * setLeftViewMethod--只显示左侧按钮
     */
    public void setLeftViewMethod(int leftsrcid, HeadView.OnLeftClickListener onleftclicklistener) {
        mHeadView = (HeadView) findViewById(R.id.common_actionbar);
        mHeadView.init(HeadView.HeaderStyle.LEFT);
        mHeadView.setLeftViewMethod(leftsrcid, onleftclicklistener);
    }

    public void setRightText(String title) {
        mHeadView.setRightText(title);
    }
    public void setTitle(String title) {
        mHeadView.setTitle(title);
    }
    /**
     * @param resid
     * @throws
     * @Title: setHeadViewBg
     * @Description: 设置HeadView的背景颜色
     * @author Mersens
     */
    public void setHeadViewBg(int resid) {
        mHeadView.setHeadViewBackground(resid);
    }

    /**
     * @param resid
     * @throws
     * @Title: setHeadViewTitleColor
     * @Description:设置HeadView的标题颜色
     * @author Mersens
     */
    public void setHeadViewTitleColor(int resid) {
        mHeadView.setHeadViewTitleColor(resid);
    }
    public void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
        SystemStatusManager tintManager = new SystemStatusManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.actionbar_color);// 状态栏无背景
    }

    public abstract void init();
    public void getPermission(){
        PermissionGen.needPermission(SupperBaseActivity.this, 100,
                new String[] {
                        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
                }
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
    public  void show(String message) {

        DialogTips dialog = new DialogTips(SupperBaseActivity.this, message, "确定");
        dialog.show();
        dialog = null;

    }

    private void ExitAlter(final Context act){
        LayoutInflater inflater = LayoutInflater.from(act);
        View layout = inflater.inflate(R.layout.dialog_default_ensure_click, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
        dialog.show();

        TextView tvok = (TextView) layout.findViewById(R.id.dialog_default_click_ensure);
        tvok.setText("重新登陆");
        TextView tvCancle = (TextView) layout.findViewById(R.id.dialog_default_click_cancel);
        tvCancle.setVisibility(View.GONE);

        TextView tvtitel = (TextView) layout.findViewById(R.id.dialog_default_click_text_title);
        tvtitel.setText("提   示");
        TextView tvcontent = (TextView) layout.findViewById(R.id.dialog_default_click_text_msg);
        tvcontent.setText("您的账号在别的设备上登录，您被迫下线");
        tvok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePreferenceUtil.getInstance(mContext).setUserId("");
                act.startActivity(new Intent(act, LoginActivity.class));
                App.getInstance().isOnline=true;
            }
        });
    }



}
