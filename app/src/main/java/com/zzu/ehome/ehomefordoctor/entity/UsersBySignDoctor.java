package com.zzu.ehome.ehomefordoctor.entity;

import com.zzu.ehome.ehomefordoctor.view.Indexable;

/**
 * Created by Mersens on 2016/10/9.
 */

public class UsersBySignDoctor implements Indexable {
    /**
     * User_FullName :
     * User_Name :
     * User_Icon : ~/UploadFile/image/2016080414385851117.jpg
     * User_RegisterId : 51117
     * Pinyin : C
     */
    private String User_FullName;
    private String User_Name;
    private String User_Icon;
    private String User_RegisterId;
    private String Pinyin;
    private ResultBean resultBean;

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    private String sortLetters;
    public ResultBean getResultBean() {
        return resultBean;
    }

    public void setResultBean(ResultBean resultBean) {
        this.resultBean = resultBean;
    }



    public String getUser_FullName() {
        return User_FullName;
    }

    public void setUser_FullName(String User_FullName) {
        this.User_FullName = User_FullName;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String User_Name) {
        this.User_Name = User_Name;
    }

    public String getUser_Icon() {
        return User_Icon;
    }

    public void setUser_Icon(String User_Icon) {
        this.User_Icon = User_Icon;
    }

    public String getUser_RegisterId() {
        return User_RegisterId;
    }

    public void setUser_RegisterId(String User_RegisterId) {
        this.User_RegisterId = User_RegisterId;
    }

    public String getPinyin() {
        return Pinyin;
    }

    public void setPinyin(String Pinyin) {
        this.Pinyin = Pinyin;
    }

    @Override
    public String getIndex() {
        return sortLetters;
    }
}
