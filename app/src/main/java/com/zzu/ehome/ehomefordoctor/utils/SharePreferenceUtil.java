package com.zzu.ehome.ehomefordoctor.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Mersens
 * @title SharePreferenceUtil
 * @description:SharePreference工具类，数据存储
 * @time 2016年4月6日
 */
public class SharePreferenceUtil {
    private static SharePreferenceUtil sp;
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor editor;
    private static final String PREFERENCE_NAME = "_sharedinfo";
    private static final String USER_ID = "user_id";
    private static final String IS_FIRST = "is_first";
    private static final String PARENT_ID = "parent_id";
    private static final String RONG_TOKEN = "RONG_TOKEN";
    private static final String RONG_NICK = "RONG_NICK";
    private static final String RONG_HEAD = "RONG_HEAD";
    private static final String RONG_STATE = "RONG_STATE";


    public static Boolean getIsFirst() {
        return mSharedPreferences.getBoolean(IS_FIRST, false);
    }

    public static void setIsFirst(Boolean isIsFirst) {
        editor.putBoolean(IS_FIRST, isIsFirst);
        editor.commit();

    }


    private SharePreferenceUtil() {

    }

    public static synchronized SharePreferenceUtil getInstance(Context context) {
        if (sp == null) {
            sp = new SharePreferenceUtil();
            mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            editor = mSharedPreferences.edit();
        }
        return sp;
    }

    //用户亲友id
    public String getPARENTID() {
        return mSharedPreferences.getString(PARENT_ID, "");
    }

    public void setPARENTID(String parentid) {
        editor.putString(PARENT_ID, parentid);
        editor.commit();
    }

    //用户id
    public String getUserId() {
        return mSharedPreferences.getString(USER_ID, "");
    }

    public void setUserId(String userid) {
        editor.putString(USER_ID, userid);
        editor.commit();
    }


    //清除数据
    public void clearAllData() {
        editor.clear().commit();
    }

    public void setRongToken(String token){
        editor.putString(RONG_TOKEN, token);
        editor.commit();
    }
    public String getRongToken(){
        return mSharedPreferences.getString(RONG_TOKEN, "");
    }

    public void setNICK(String nick){
        editor.putString(RONG_NICK, nick);
        editor.commit();
    }
    public String getNICK(){
        return mSharedPreferences.getString(RONG_NICK, "");
    }

    public void setRongHead(String head){
        editor.putString(RONG_HEAD, head);
        editor.commit();
    }
    public String getRongHead(){
        return mSharedPreferences.getString(RONG_HEAD, "");
    }
    public void setRongState(int  state){
        editor.putInt(RONG_STATE, state);
        editor.commit();
    }
    public int getRongState(){
        return mSharedPreferences.getInt(RONG_STATE,1);
    }


}
