package com.zzu.ehome.ehomefordoctor.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiang.android.lib.adapter.expand.StickyRecyclerHeadersDecoration;
import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.adapter.ContactAdapter;
import com.zzu.ehome.ehomefordoctor.entity.UsersBySignDoctor;
import com.zzu.ehome.ehomefordoctor.mvp.model.IHzInfoImpl;
import com.zzu.ehome.ehomefordoctor.mvp.presenter.HzInfoPresenter;
import com.zzu.ehome.ehomefordoctor.mvp.view.IHzInfoView;
import com.zzu.ehome.ehomefordoctor.utils.CommonUtils;
import com.zzu.ehome.ehomefordoctor.utils.ToastUtils;
import com.zzu.ehome.ehomefordoctor.view.ProgressStateLayout;
import com.zzu.ehome.ehomefordoctor.view.SideBar;
import com.zzu.ehome.ehomefordoctor.view.ZSideBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;


/**
 * Created by Mersens on 2016/9/28.
 */

public class HZFragment extends BaseFragment implements IHzInfoView {

    @BindView(R.id.contact_member)
    RecyclerView listView;
    @BindView(R.id.contact_dialog)
    TextView contactDialog;
    @BindView(R.id.sidebar)
    SideBar contactSidebar;
    @BindView(R.id.zsidebar)
    ZSideBar contactZsidebar;
    @BindView(R.id.progressStateLayout)
    ProgressStateLayout progressStateLayout;
//    @BindView(R.id.swipeRefreshLayout)
//    SwipeRefreshLayout swipeRefreshLayout;
    private ContactAdapter mAdapter;
    private HzInfoPresenter presenter;
    private boolean isRefresh=false;
    public List<UsersBySignDoctor> getmList() {
        return mList;
    }

    public void setmList(List<UsersBySignDoctor> mList) {
        this.mList = mList;
    }

    private List<UsersBySignDoctor> mList=new ArrayList<>();

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    private boolean isClick=true;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hz, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    public static Fragment getInstance() {
        return new HZFragment();
    }

    @Override
    public void init() {
        presenter = new HzInfoPresenter(this);
        presenter.doGetInfo();
       initEvent();
    }

    public void initEvent() {

//        MyLayoutManager manager = new MyLayoutManager(getActivity());
//        manager.setOrientation(LinearLayout.VERTICAL);//默认是LinearLayout.VERTICAL
//        listView.setLayoutManager(manager);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        listView.setLayoutManager(layoutManager);
        listView.setHasFixedSize(true);
//        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.actionbar_color));
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                isRefresh=true;
//                presenter.doGetInfo();
//            }
//        });
        contactSidebar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                if (mAdapter != null) {
                    int position = mAdapter.getPositionForSection(s.charAt(0));
                    if (position != -1) {
                        listView.getLayoutManager().scrollToPosition(position);
                    }
                }
            }
        });
    }

    @Override
    public void hidePro() {

    }

    @Override
    public void showPro() {
        progressStateLayout.showLoading();
    }

    @Override
    public void onSuccess(List<UsersBySignDoctor> list) {
       if(isRefresh){
           mList.clear();
         mList.addAll(list);
       }else{
           mList=list;
       }
        if (mList != null && mList.size() > 0) {
            if (mList.get(0).getResultBean() == null) {

                for (UsersBySignDoctor bean : mList) {
                        RongIM.getInstance().refreshUserInfoCache(new UserInfo(bean.getUser_RegisterId(), bean.getUser_FullName(), Uri.parse(bean.getUser_Icon())));
                }
                setDatas(mList);

                progressStateLayout.showContent();
            } else {
                progressStateLayout.showEmpty(R.mipmap.icon_wuhuanzhe,"");

            }
        } else {
            progressStateLayout.showEmpty(R.mipmap.icon_wuhuanzhe,"");
        }

    }

    public void setDatas(List<UsersBySignDoctor> list) {


             mAdapter = new ContactAdapter(getActivity(), list);
            listView.setAdapter(mAdapter);
            StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mAdapter);
            listView.addItemDecoration(headersDecor);
            contactZsidebar.setupWithRecycler(listView);
            mAdapter.setOnItemClickListener(new ContactAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public <T> void onItemClick(View view, T t) {
                    if(isClick) {
                        UsersBySignDoctor u = (UsersBySignDoctor) t;
//                RongIM.getInstance().refreshUserInfoCache(new UserInfo("51064", "啊明", Uri.parse("http://rongcloud-web.qiniudn.com/docs_demo_rongcloud_logo.png")));
                        RongIM.getInstance().refreshUserInfoCache(new UserInfo(u.getUser_RegisterId(), u.getUser_FullName(), Uri.parse(u.getUser_Icon())));
                        RongIM.getInstance().startPrivateChat(getActivity(), u.getUser_RegisterId(), u.getUser_FullName());
                    }
                }
            });

    }

    @Override
    public void onErroe(Exception e) {
        Log.e("onErroe",e.toString());
//        CommonUtils.saveCrashInfo2File(e);
//       ToastUtils.showMessage(getActivity(),e.toString());
        progressStateLayout.showEmpty(R.mipmap.icon_none,"数据加载失败"+e.toString());
        progressStateLayout.showError(new ProgressStateLayout.ReloadListener() {
            @Override
            public void onClick() {
                presenter.doGetInfo();

            }
        });
    }
    public void refush(){
        isRefresh=true;
        presenter.doGetInfo();

    }






}
