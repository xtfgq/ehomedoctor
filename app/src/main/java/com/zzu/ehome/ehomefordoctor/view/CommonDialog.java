package com.zzu.ehome.ehomefordoctor.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;


import com.zzu.ehome.ehomefordoctor.activity.LoginActivity;
import com.zzu.ehome.ehomefordoctor.utils.DialogUtils;
import com.zzu.ehome.ehomefordoctor.utils.SharePreferenceUtil;

/**
 * Created by Administrator on 2016/9/30.
 */

public class CommonDialog {
    public static void confirmExit(final Context context) {

        DialogEnsureCancelView dialogEnsureCancelView = new DialogEnsureCancelView(
                context,false).setDialogMsg("", "你的账号在其他设备上登陆？", "重新登陆")
                .setOnClickListenerEnsure(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        SharePreferenceUtil.getInstance(context).setUserId("");
                        context.startActivity(new Intent(context, LoginActivity.class));

                    }
                });
        DialogUtils.showSelfDialog(context, dialogEnsureCancelView);

    }
}
