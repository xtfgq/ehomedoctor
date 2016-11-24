package com.zzu.ehome.ehomefordoctor.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zzu.ehome.ehomefordoctor.entity.DoctorBean;
import com.zzu.ehome.ehomefordoctor.entity.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzu on 2016/4/6.
 */
public class EHomeDaoImpl implements EHomeDao {
    private DBHelper helper;
    private Context context;

    public EHomeDaoImpl(Context context) {
        helper = DBHelper.getInstance(context);
        this.context = context;
    }

    @Override
    public List<DoctorBean> findAllUser() {
        List<DoctorBean> list = new ArrayList<DoctorBean>();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "select * from login_doctor",
                null);
        while (cursor.moveToNext()) {
            DoctorBean user = new DoctorBean();
            String userid = cursor.getString(cursor.getColumnIndex("DoctorID"));
            user.setDoctorID(userid);
            String username = cursor.getString(cursor.getColumnIndex("DoctorName"));
            user.setDoctorName(username);
            String mobile = cursor.getString(cursor.getColumnIndex("Mobile"));
            user.setMobile(mobile);
            String ImageURL = cursor.getString(cursor.getColumnIndex("ImageURL"));
            user.setImageURL(ImageURL);
            list.add(user);
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public DoctorBean findUserInfoById(String userid) {
        List<DoctorBean> list = new ArrayList<DoctorBean>();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from login_doctor where DoctorID=?",
                new String[]{userid});
        while (cursor.moveToNext()) {
            DoctorBean user = new DoctorBean();
            String docid = cursor.getString(cursor.getColumnIndex("DoctorID"));
            user.setDoctorID(docid);
            String username = cursor.getString(cursor.getColumnIndex("DoctorName"));
            user.setDoctorName(username);
            String mobile = cursor.getString(cursor.getColumnIndex("Mobile"));
            user.setMobile(mobile);
            String ImageURL = cursor.getString(cursor.getColumnIndex("ImageURL"));
            user.setImageURL(ImageURL);
            list.add(user);
        }
        cursor.close();
        db.close();
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }

    }

    @Override
    public void delUserInfoById(String userid) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from login_doctor where DoctorID=?",
                new Object[]{userid});
        db.close();
    }

    @Override
    public boolean findUserIsExist(String userid) {
        boolean flag = false;
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "select * from login_doctor where DoctorID=? ",
                new String[]{userid});
        while (cursor.moveToNext()) {
            flag = true;
        }
        cursor.close();
        db.close();
        return flag;
    }

    @Override
    public void addUserInfo(DoctorBean user) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL(
                "insert into login_doctor(DoctorID,Mobile,DoctorName,HospitalName,Title,ImageURL) values(?,?,?,?,?,?)",
                new Object[]{user.getDoctorID(), user.getMobile(),
                        user.getDoctorName(), user.getHospitalName(), user.getTitle(), user.getImageURL()});
        db.close();
    }

    @Override
    public void updateUserInfo(DoctorBean user, String userid) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("UPDATE login_doctor SET DoctorID=?,Mobile=?,DoctorName=?,HospitalName=?,ImageURL=? where DoctorID=?", new Object[]{user.getDoctorID(), user.getMobile(),
                user.getDoctorName(), user.getHospitalName(), user.getTitle(), user.getImageURL()});
        db.close();
    }



}
