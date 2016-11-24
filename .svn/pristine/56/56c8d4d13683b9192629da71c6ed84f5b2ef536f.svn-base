package com.zzu.ehome.ehomefordoctor.db;

import com.zzu.ehome.ehomefordoctor.entity.DoctorBean;
import com.zzu.ehome.ehomefordoctor.entity.UserBean;

import java.util.List;

/**
 * Created by zzu on 2016/4/6.
 */
public interface EHomeDao {

    //查询所有用户信息
    public List<DoctorBean> findAllUser();

    //根据id查找用户信息
    public DoctorBean findUserInfoById(String userid);

    //删除用户信息
    public void delUserInfoById(String userid);

    //判断用户是否存在
    public boolean findUserIsExist(String userid);

    //添加用户信息
    public void addUserInfo(DoctorBean user);

    //修改用户信息
    public void updateUserInfo(DoctorBean user, String userid);




}
