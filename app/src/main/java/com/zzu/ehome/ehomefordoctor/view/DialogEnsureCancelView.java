package com.zzu.ehome.ehomefordoctor.view;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzu.ehome.ehomefordoctor.R;


public class DialogEnsureCancelView extends MyDialogView {
    private TextView mTextTitle, mTextMsg, mTextEnsure, mTextCancel;
    private OnClickListener mClickListenerEnsure, mClickListenerCancle;
    private Boolean isCancle=true;
    public DialogEnsureCancelView(Context context) {
        super(context);
        initView();
    }
    public DialogEnsureCancelView(Context context, Boolean isCancle) {
        super(context);
        this.isCancle=isCancle;
        initView();
    }

    private void initView() {
        removeAllViews();
        LinearLayout layout = (LinearLayout) LayoutInflater.from(context)
                .inflate(R.layout.dialog_default_ensure_click, this, false);
        mTextTitle = (TextView) layout.findViewById(R.id.dialog_default_click_text_title);

        mTextMsg = (TextView) layout.findViewById(R.id.dialog_default_click_text_msg);
        mTextEnsure = (TextView) layout.findViewById(R.id.dialog_default_click_ensure);
        mTextCancel = (TextView) layout.findViewById(R.id.dialog_default_click_cancel);
        mTextEnsure.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//				ToastUtils.showShort(context, "功能待开放");
                if (mClickListenerEnsure != null) {
                    mClickListenerEnsure.onClick(v);
                }
                if (mExitDialog != null) {
                    mExitDialog.exitDialog();
                }
            }
        });

        mTextCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mClickListenerCancle != null) {
                    mClickListenerCancle.onClick(v);
                }
                if (mExitDialog != null) {
                    mExitDialog.exitDialog();
                }
            }
        });
        if(isCancle){
            mTextCancel.setVisibility(VISIBLE);
        }else{
            mTextCancel.setVisibility(GONE);
        }

        this.addView(layout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    @Override
    public DialogInterface.OnClickListener getPositiveButtonListener() {
        return null;
    }

    /**
     * 确定按钮点击事件
     */
    public DialogEnsureCancelView setOnClickListenerEnsure(OnClickListener clickListener) {
        mClickListenerEnsure = clickListener;
        /*if(mClickListener != null) {
			mLayoutEnsure.setVisibility(View.VISIBLE);
		}
		else {
			mLayoutEnsure.setVisibility(View.GONE);
		}*/
        return this;

    }

    /**
     * 取消按钮点击事件
     */
    public DialogEnsureCancelView setOnClickListenerCancle(OnClickListener clickListener) {
        mClickListenerCancle = clickListener;
		/*if(mClickListener != null) {
			mLayoutEnsure.setVisibility(View.VISIBLE);
		}
		else {
			mLayoutEnsure.setVisibility(View.GONE);
		}*/
        return this;
    }

    /**
     * 确定按钮点击事件
     */
    public DialogEnsureCancelView setDialogMsg(String title, String msg, String ensure) {
        if (!TextUtils.isEmpty(title)) {
            mTextTitle.setText(title);
        }
        if (!TextUtils.isEmpty(msg)) {
            mTextMsg.setText(msg);
        }
        if (!TextUtils.isEmpty(ensure)) {
            mTextEnsure.setText(ensure);
        }
        return this;
    }

    @Override
    public DialogInterface.OnClickListener getNegativeButtonListener() {
        return new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();
            }
        };
    }

}
