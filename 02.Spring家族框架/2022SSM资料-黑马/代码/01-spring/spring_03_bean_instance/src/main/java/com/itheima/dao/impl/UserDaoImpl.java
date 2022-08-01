package com.itheima.dao.impl;

import com.itheima.dao.UserDao;

public class UserDaoImpl implements UserDao {

    public void save() {
        System.out.println("user dao save ...");
    }

    public UserDaoImpl() {
        System.out.println("构造方法.......");
    }
}
