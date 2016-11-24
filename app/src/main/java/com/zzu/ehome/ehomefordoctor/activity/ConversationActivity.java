package com.zzu.ehome.ehomefordoctor.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.app.App;
import com.zzu.ehome.ehomefordoctor.app.CommonApi;
import com.zzu.ehome.ehomefordoctor.mvp.listener.Tool;
import com.zzu.ehome.ehomefordoctor.utils.SharePreferenceUtil;
import com.zzu.ehome.ehomefordoctor.view.HeadView;

import java.util.Locale;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.LocationMessage;


/**
 * Created by Bob on 15/8/18.
 * 会话页面
 */
public class ConversationActivity extends BaseActivity implements RongIM.LocationProvider, RongIM.ConversationBehaviorListener {

    private TextView mTitle;
    private RelativeLayout mBack;
    String name;
    private String mTargetId;
    String token = null;
    /**
     * 刚刚创建完讨论组后获得讨论组的id 为targetIds，需要根据 为targetIds 获取 targetId
     */
    private String mTargetIds;

    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;
    private int state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
        state=SharePreferenceUtil.getInstance(this).getRongState();
        Intent intent = getIntent();
        getIntentDate(intent);
        setActionBar();
        RongIM.setLocationProvider(this);
        RongIM.setConversationBehaviorListener(this);
        isReconnect(intent);
    }

    @Override
    public void init() {

    }

    /**
     * 展示如何从 Intent 中得到 融云会话页面传递的 Uri
     */
    private void getIntentDate(Intent intent) {
        mTargetId = intent.getData().getQueryParameter("targetId");

         name = getIntent().getData().getQueryParameter("title");
        mConversationType = Conversation.ConversationType.valueOf(intent.getData().getLastPathSegment().toUpperCase(Locale.getDefault()));
    }


    /**
     * 加载会话页面 ConversationFragment
     *
     * @param mConversationType
     * @param mTargetId
     */
    private void enterFragment(Conversation.ConversationType mConversationType, String mTargetId) {

        ConversationFragment fragment = new ConversationFragment();

        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
                .appendQueryParameter("targetId", mTargetId).build();

        fragment.setUri(uri);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //xxx 为你要加载的 id
        transaction.replace(R.id.rong_content, fragment);
        transaction.commit();
    }


    /**
     * 判断消息是否是 push 消息
     */
    private void isReconnect(Intent intent) {


        String token = SharePreferenceUtil.getInstance(ConversationActivity.this).getRongToken();


        //push或通知过来
        if (intent != null && intent.getData() != null && intent.getData().getScheme().equals("rong")) {

            //通过intent.getData().getQueryParameter("push") 为true，判断是否是push消息

            if (intent.getData().getScheme().equals("rong") && intent.getData().getQueryParameter("isFromPush") != null&& state==1) {
                CommonApi.connent(token, new CommonApi.RongIMListener() {
                    @Override
                    public void OnSuccess(String userid) {
                        enterFragment(mConversationType, mTargetId);
                    }
                });

            } else {

                //程序切到后台，收到消息后点击进入,会执行这里
                if (RongIM.getInstance() == null || RongIM.getInstance().getRongIMClient() == null&& state==1) {

                    CommonApi.connent(token, new CommonApi.RongIMListener() {
                        @Override
                        public void OnSuccess(String userid) {

                        }
                    });
                } else {
                    if (RongIM.getInstance().getCurrentConnectionStatus().equals(RongIMClient.ConnectionStatusListener.ConnectionStatus.DISCONNECTED)&& state==1) {
                        CommonApi.connent(token, new CommonApi.RongIMListener() {
                            @Override
                            public void OnSuccess(String userid) {
                                enterFragment(mConversationType, mTargetId);
                            }
                        });

                    } else {
                        if(state==0){
                            RongIM.getInstance().logout();
                            RongIM.getInstance().disconnect();
                        }
                        enterFragment(mConversationType, mTargetId);
                    }
                }
            }
        }
    }

    /**
     * 设置 actionbar 事件
     */
    private void setActionBar() {
        setDefaultTXViewMethod(R.mipmap.icon_arrow_left, name, "病历", new HeadView.OnLeftClickListener() {
            @Override
            public void onClick() {
                finish();

            }
        }, new HeadView.OnRightClickListener() {
            @Override
            public void onClick() {
                Intent intent=new Intent(ConversationActivity.this,MedicalRecordActivity.class);
                intent.putExtra("TargetId",mTargetId);
                startActivity(intent);
            }
        });


    }


    /**
     * 设置 actionbar title
     */
    private void setActionBarTitle(String targetid) {

        mTitle.setText(targetid);
    }


    @Override
    public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
        return false;
    }

    @Override
    public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
        return false;
    }


    @Override
    public boolean onMessageLinkClick(Context context, String s) {
        return false;
    }

    @Override
    public boolean onMessageLongClick(Context context, View view, Message message) {
        return false;
    }

    @Override
    public boolean onMessageClick(Context arg0, View arg1, Message arg2) {
        // TODO Auto-generated method stub
        if (arg2.getContent() instanceof LocationMessage) {
            Intent intent = new Intent(ConversationActivity.this, MapLocationActivity.class);
            intent.putExtra("location", arg2.getContent());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }


        return false;
    }

    @Override
    public void onStartLocation(Context context, LocationCallback callback) {
        Tool.mLastLocationCallback = callback;
        Intent intent = new Intent(context, MapLocationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}
