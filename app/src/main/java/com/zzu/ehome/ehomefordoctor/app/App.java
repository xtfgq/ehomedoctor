package com.zzu.ehome.ehomefordoctor.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;

import java.util.LinkedList;
import java.util.List;

import io.rong.imkit.RongIM;


/**
 * Created by Mersens on 2016/9/28.
 */

public class App extends Application {
    public static List<Activity> mList = new LinkedList<Activity>();
    private static App mApp;
    public  static int count;


    public static App getInstance() {
        if (mApp == null) {
            synchronized (App.class) {
                if (mApp == null) {
                    mApp = new App();
                }
            }
        }
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        CrashHandler.getInstance().init(getApplicationContext());
        //初始化融云

        SDKInitializer.initialize(this);
        RongIM.init(this);
    }

    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return null;
    }
}
