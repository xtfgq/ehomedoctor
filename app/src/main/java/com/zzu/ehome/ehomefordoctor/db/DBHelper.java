package com.zzu.ehome.ehomefordoctor.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by zzu on 2016/4/6.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 2;
    private static final String NAME = "EHOME.db";

    private static final String SQL_DOCTOR_LOGIN_CREAT = "create table login_doctor(_id integer primary key autoincrement,DoctorID text ,Mobile text,DoctorName text,HospitalName text,Title text,ImageURL text,Department text)";
    private static final String SQLDOCTOR_LOGIN_DROP = "drop table if exists login_doctor";

    public static DBHelper helper = null;
    public static Context mContext;

    public static DBHelper getInstance(Context context) {
        if (helper == null) {
            synchronized (DBHelper.class) {
                if (helper == null) {
                    helper = new DBHelper(context.getApplicationContext());
                }
            }
        }
        mContext = context;
        return helper;
    }

    private DBHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(SQL_DOCTOR_LOGIN_CREAT);
    }

    /**
     * 当数据库更新时，调用该方法
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(SQLDOCTOR_LOGIN_DROP);

    }
}
