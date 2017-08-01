package com.zzu.ehome.ehomefordoctor.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.igexin.sdk.PushManager;
import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.app.App;
import com.zzu.ehome.ehomefordoctor.app.CommonApi;
import com.zzu.ehome.ehomefordoctor.app.Constans;
import com.zzu.ehome.ehomefordoctor.entity.UpInfoBean;
import com.zzu.ehome.ehomefordoctor.entity.User;
import com.zzu.ehome.ehomefordoctor.entity.UserInfoDate;
import com.zzu.ehome.ehomefordoctor.entity.UsersBySignDoctor;
import com.zzu.ehome.ehomefordoctor.fragment.HZFragment;
import com.zzu.ehome.ehomefordoctor.mvp.presenter.HzOfflinePresenter;
import com.zzu.ehome.ehomefordoctor.mvp.presenter.HzOnlinePresenter;
import com.zzu.ehome.ehomefordoctor.mvp.presenter.UpDataPresenter;
import com.zzu.ehome.ehomefordoctor.mvp.presenter.UserDataPresenter;
import com.zzu.ehome.ehomefordoctor.mvp.view.IOffLineView;
import com.zzu.ehome.ehomefordoctor.mvp.view.IOnLineView;
import com.zzu.ehome.ehomefordoctor.mvp.view.IUpView;
import com.zzu.ehome.ehomefordoctor.mvp.view.IUserDateView;
import com.zzu.ehome.ehomefordoctor.reciver.EventType;
import com.zzu.ehome.ehomefordoctor.reciver.RxBus;
import com.zzu.ehome.ehomefordoctor.service.DownloadServiceForAPK;
import com.zzu.ehome.ehomefordoctor.utils.DialogUtils;
import com.zzu.ehome.ehomefordoctor.utils.JsonTools;
import com.zzu.ehome.ehomefordoctor.utils.SharePreferenceUtil;
import com.zzu.ehome.ehomefordoctor.utils.ToastUtils;

import com.zzu.ehome.ehomefordoctor.view.DialogEnsureCancelView;
import com.zzu.ehome.ehomefordoctor.view.DialogTips;
import com.zzu.ehome.ehomefordoctor.view.HeadView;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;


public class MainActivity extends BaseActivity implements IOnLineView, IOffLineView, IUpView {
    private static final int REQUEST_PERMISSION = 0;

    @BindView(R.id.image_msg)
    ImageView imageMsg;
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.layout_msg)
    RelativeLayout layoutMsg;
    @BindView(R.id.img_hz)
    ImageView imgHz;
    @BindView(R.id.tv_hz)
    TextView tvHz;
    @BindView(R.id.layout_hz)
    RelativeLayout layoutHz;
    @BindView(R.id.icon_state)
    ImageView iconState;
    private int index = 1;
    private int selectColor;
    private int unSelectColor;
    private int currentTabIndex = 1;
    private Fragment[] fragments;
    private RelativeLayout[] mTabs;
    private Fragment mConversationListFragment = null;
    private HZFragment hzFragment;
    private String ClientID;
    private int indexButton;
    private HzOnlinePresenter mHzOnlinePresenter;
    private TextView tv_dot;
    private HzOfflinePresenter mHzOfflinePresenter;
    private UpDataPresenter mUpDataPresenter;
    private UserDataPresenter presenter;
    private boolean isfirst=false;
    public RongIM.OnReceiveUnreadCountChangedListener mCountListener = new RongIM.OnReceiveUnreadCountChangedListener() {
        @Override
        public void onMessageIncreased(int count) {
            App.getInstance().count = count;
            if (count > 0) {
                tv_dot.setText(App.getInstance().count + "");
                tv_dot.setVisibility(View.VISIBLE);

            } else {
                tv_dot.setVisibility(View.GONE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initRong();
        init();
    }


    @Override
    public void init() {
        initGeTui();
        setOnlyTileViewMethod("消息");
        App.finishSingleActivityByClass(LoginActivity.class);
        App.finishSingleActivityByClass(FindPsdActivity.class);
//        setDefaultTXViewMethod(R.mipmap.icon_arrow_left, "消息", "退出", new HeadView.OnLeftClickListener() {
//            @Override
//            public void onClick() {
//                finish();
//
//            }
//        }, new HeadView.OnRightClickListener() {
//            @Override
//            public void onClick() {
//             SharePreferenceUtil.getInstance(MainActivity.this).setUserId("");
//                SharePreferenceUtil.getInstance(MainActivity.this).setRongToken("");
//
//                mHzOfflinePresenter.postOffline();
//                startActivity(new Intent(MainActivity.this,LoginActivity.class));
//                finish();
//            }
//        });
        selectColor = getResources().getColor(R.color.bottom_text_color_pressed);
        unSelectColor = getResources().getColor(R.color.bottom_text_color_normal);
        mTabs = new RelativeLayout[2];
        mTabs[0] = layoutMsg;
        mTabs[1] = layoutHz;
        mConversationListFragment = initConversationList();
        hzFragment = (HZFragment) HZFragment.getInstance();
        fragments = new Fragment[]{mConversationListFragment, hzFragment};
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragments[1])
                .commit();
        iconState.setImageResource(R.mipmap.icon_rest);
        mHzOnlinePresenter = new HzOnlinePresenter(this);
        mHzOfflinePresenter = new HzOfflinePresenter(this);
        mUpDataPresenter = new UpDataPresenter(this);


        mUpDataPresenter.getUp();
        setTitls(1);
        setTabs(1);
        if (SharePreferenceUtil.getInstance(this).getRongState() == 1) {
            mHzOnlinePresenter.postOnline();

            iconState.setImageResource(R.mipmap.icon_rest);
            indexButton = 1;
        } else {
            indexButton = 0;
            iconState.setImageResource(R.mipmap.icon_online);
            mHzOfflinePresenter.postOffline();
        }
        tv_dot = (TextView) findViewById(R.id.tv_dot);

        RongIM.getInstance().setCurrentUserInfo(new UserInfo(SharePreferenceUtil.getInstance(MainActivity.this).getUserId(),
                SharePreferenceUtil.getInstance(MainActivity.this).getNICK(),
                Uri.parse(SharePreferenceUtil.getInstance(MainActivity.this).getRongHead())));
        RongIM.getInstance().setMessageAttachedUserInfo(true);
        initUnreadCountListener();
        receiveMsg();
    }

    @OnClick({R.id.layout_msg, R.id.layout_hz, R.id.icon_state})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.layout_msg:
//                mConversationListFragment=ConversationListFragment.getInstance();
//                FragmentTransaction trx = getSupportFragmentManager()
//                        .beginTransaction();
//                trx.replace(R.id.fragment_container,mConversationListFragment).;

                index = 0;
                setTitls(index);
                setTabs(index);
                selectItem(index);
                hzFragment.setClick(false);

                break;
            case R.id.layout_hz:
                index = 1;
                hzFragment=(HZFragment)HZFragment.getInstance();
                FragmentTransaction trx2 = getSupportFragmentManager()
                        .beginTransaction();
                trx2.replace(R.id.fragment_container,hzFragment).commit();
                setTitls(index);
                setTabs(index);
                selectItem(index);
                hzFragment.setClick(true);

                break;
            case R.id.icon_state:
                isfirst=true;
                if (indexButton % 2 == 0) {
                    SharePreferenceUtil.getInstance(MainActivity.this).setRongState(1);
                    initRong();
                    mHzOnlinePresenter.postOnline();
                    iconState.setImageResource(R.mipmap.icon_rest);
                } else {
                    SharePreferenceUtil.getInstance(MainActivity.this).setRongState(0);
                    iconState.setImageResource(R.mipmap.icon_online);
                    mHzOfflinePresenter.postOffline();
                    RongIM.getInstance().logout();
                    RongIM.getInstance().disconnect();
                }
                indexButton++;

                break;
        }
//        selectItem(index);
    }


    private void selectItem(int index) {
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager()
                    .beginTransaction();

            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        mTabs[currentTabIndex].setSelected(false);
        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }

    public void setTitls(int index) {
        switch (index) {
            case 0:
                setTitle("消息");
                break;
            case 1:
                setTitle("患者");
                break;
        }
    }

    public void setTabs(int index) {
        resetImgs();
        switch (index) {
            case 0:
                tvMsg.setTextColor(selectColor);
                imageMsg.setImageResource(R.mipmap.icon_message_pressed);
                break;
            case 1:
                tvHz.setTextColor(selectColor);
                imgHz.setImageResource(R.mipmap.icon_huanzhe_pressed);
                break;
        }
    }

    public void resetImgs() {
        tvMsg.setTextColor(unSelectColor);
        tvHz.setTextColor(unSelectColor);
        imageMsg.setImageResource(R.mipmap.icon_message_normal);
        imgHz.setImageResource(R.mipmap.icon_huanzhe_normal);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            if ((grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                PushManager.getInstance().initialize(this.getApplicationContext());
            } else {
                PushManager.getInstance().initialize(this.getApplicationContext());
            }
        } else {
            onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public <T> void onUpSuccess(T t) {
        UpInfoBean mUpInfoBean = (UpInfoBean) t;
        if (mUpInfoBean.getmResultBean() != null) {
            return;
        }

        try {
            int newCode = Integer.valueOf(mUpInfoBean.getVersionID());
            int VersionFlag = Integer.valueOf(mUpInfoBean.getVersionFlag());
            PackageManager manager = getPackageManager();
            PackageInfo info = manager.getPackageInfo(
                    getPackageName(), 0);
            String appVersion = info.versionName; // 版本名
            int oldCode = info.versionCode; // 版本号
            if (oldCode < newCode) {
                String versionlog = mUpInfoBean.getVersionLog()
                        .replace("@", "\n");
                if (VersionFlag == 1) {
                    showForceUpdate(versionlog);
                } else {
                    showUpdateDialog(versionlog);
                }

            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }




    private class MyConnectionStatusListener implements RongIMClient.ConnectionStatusListener {

        @Override
        public void onChanged(ConnectionStatus connectionStatus) {

            switch (connectionStatus) {

                case CONNECTED://连接成功。
                    break;
                case DISCONNECTED://断开连接。

                    break;
                case CONNECTING://连接中。

                    break;
                case NETWORK_UNAVAILABLE://网络不可用。

                    break;
                case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线

                    RxBus.getInstance().send(new EventType("rongyun"));
                    break;
            }
        }
    }

    private void initGeTui() {
        PackageManager pkgManager = getPackageManager();
        // 读写 sd card 权限非常重要, android6.0默认禁止的, 建议初始化之前就弹窗让用户赋予该权限
        boolean sdCardWritePermission =
                pkgManager.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        getPackageName()) == PackageManager.PERMISSION_GRANTED;
        // read phone state用于获取 imei 设备信息
        boolean phoneSatePermission =
                pkgManager.checkPermission(Manifest.permission.READ_PHONE_STATE, getPackageName()) ==
                        PackageManager.PERMISSION_GRANTED;
        if (Build.VERSION.SDK_INT >= 23 && !sdCardWritePermission || !phoneSatePermission) {
            requestPermission();
        } else {
            // SDK初始化，第三方程序启动时，都要进行SDK初始化工作
            PushManager.getInstance().initialize(this.getApplicationContext());
            ClientID = PushManager.getInstance().getClientid(MainActivity.this);
        }
    }

    private Fragment initConversationList() {
        ConversationListFragment listFragment = ConversationListFragment.getInstance();
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false") //设置私聊会话是否聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false") //设置私聊会话是否聚合显示
                .build();
        listFragment.setUri(uri);
        return listFragment;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            this.confirmExit();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void confirmExit() {
        DialogTips dialog = new DialogTips(MainActivity.this, "", "是否退出软件？",
                "确定", true, true);
        dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int userId) {
                App.getInstance().exit();
                finish();
            }
        });
        dialog.show();
        dialog = null;
    }

    private void initRong() {
        String token = SharePreferenceUtil.getInstance(this).getRongToken();

        if (!TextUtils.isEmpty(token)) {
            CommonApi.connent(token, new CommonApi.RongIMListener() {
                @Override
                public void OnSuccess(String userid) {
                    RongIM.getInstance().getRongIMClient().setConnectionStatusListener(new
                            MyConnectionStatusListener());
                    RongIM.getInstance().setCurrentUserInfo(new UserInfo(
                            SharePreferenceUtil.getInstance(MainActivity.this).getUserId(),
                            SharePreferenceUtil.getInstance(MainActivity.this).getNICK(),
                            Uri.parse(SharePreferenceUtil.
                                    getInstance(MainActivity.this).getRongHead())));
                    RongIM.getInstance().setMessageAttachedUserInfo(true);
                    refreshList();
                }
            });
        }
    }

    @Override
    public void onOffLineSuccess(String str) {
        if(isfirst) {
            ToastUtils.showMessage(MainActivity.this, "离线时间已记录成功！");
        }
    }

    @Override
    public void onOffLineErroe(Exception e) {

    }

    @Override
    public void onLineSuccess(String str) {
        if(isfirst) {
            ToastUtils.showMessage(MainActivity.this, "上线时间已记录成功！");
        }
    }

    @Override
    public void onLineErroe(Exception e) {

    }

    private void showForceUpdate(String content) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View layout = inflater.inflate(R.layout.dialog_default_ensure, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        builder.setCancelable(false);
        builder.create().show();
        TextView tvok = (TextView) layout.findViewById(R.id.dialog_default_click_ensure);
        TextView tvtitel = (TextView) layout.findViewById(R.id.dialog_default_click_text_title);
        tvtitel.setText("检测到新版本");
        TextView tvcontent = (TextView) layout.findViewById(R.id.dialog_default_click_text_msg);
        tvcontent.setText(content);
        tvok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent it = new Intent(MainActivity.this,
                        DownloadServiceForAPK.class);
                startService(it);

            }
        });
    }

    private void showUpdateDialog(String content) {
        DialogEnsureCancelView dialogEnsureCancelView = new DialogEnsureCancelView(
                this).setDialogMsg("检测到新版本", content, "下载")
                .setOnClickListenerEnsure(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        final Intent it = new Intent(MainActivity.this,
                                DownloadServiceForAPK.class);
                        startService(it);

                    }
                });
        DialogUtils.showSelfDialog(this, dialogEnsureCancelView);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (SharePreferenceUtil.getInstance(this).getRongState() == 1) {
            mHzOfflinePresenter.postOffline();
        }
    }

    private void initUnreadCountListener() {
        final Conversation.ConversationType[] conversationTypes = {Conversation.ConversationType.PRIVATE, Conversation.ConversationType.DISCUSSION,
                Conversation.ConversationType.GROUP, Conversation.ConversationType.SYSTEM,
                Conversation.ConversationType.PUBLIC_SERVICE};

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                RongIM.getInstance().setOnReceiveUnreadCountChangedListener(mCountListener, conversationTypes);
            }
        }, 500);
    }
    private void refreshList(){
        RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(Message message, int i) {
                String sendid=message.getSenderUserId();
                if(hzFragment.getmList().size()>0){
                    boolean isHasNew=true;
                    for(UsersBySignDoctor bean:hzFragment.getmList()){
                        if(sendid.equals(bean.getUser_RegisterId())){
                            isHasNew=false;
                            break;
                        }
                    }
                    if(isHasNew){
                        CommonApi.getUser(sendid, new CommonApi.UserInfoListener() {
                            @Override
                            public void OnSuccess(String msg) {
                                UserInfoDate date = JsonTools.getData(msg.toString(), UserInfoDate.class);
                                List<User> list = date.getData();
                                User user = list.get(0);
                                String url=user.getImgHead();
                                if(url.contains("vine.gif")){
                                    url="";
                                }else{
                                    url=Constans.JE_BASE_URL3 + url.replace("~", "").replace("\\", "/");

                                }
                                RongIM.getInstance().refreshUserInfoCache(new UserInfo(user.getUserid(), user.getUsername(), Uri.parse(url)));
                            }
                        });
//                       hzFragment.refush();
                    }
                }
                return false;
            }
        });
    }
    /**
     * 接收消息
     */
    private void receiveMsg() {
        RongIMClient.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            /**
             * 收到消息的处理。
             * @param message 收到的消息实体。
             * @param i       剩余未拉取消息数目。
             * @return 是否接收
             */
            @Override
            public boolean onReceived(final Message message, int i) {
                return false;
            }
        });
    }

}
