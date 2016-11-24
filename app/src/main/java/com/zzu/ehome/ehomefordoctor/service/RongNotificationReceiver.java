package com.zzu.ehome.ehomefordoctor.service;

import android.content.Context;

import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

/**
 * Created by Administrator on 2016/9/2.
 */
public class RongNotificationReceiver extends PushMessageReceiver {
    @Override
    public boolean onNotificationMessageArrived(Context context, PushNotificationMessage pushNotificationMessage) {
        pushNotificationMessage.getPushData();
        pushNotificationMessage.getExtra();
        pushNotificationMessage.getTargetId();

        return false;
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushNotificationMessage pushNotificationMessage) {
        pushNotificationMessage.getPushContent();
        pushNotificationMessage.getPushData();
        return false;

    }
}
