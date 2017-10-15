package com.misnearzhang.service.impl;

import com.misnearzhang.dao.UserDao;
import com.misnearzhang.pojo.User;
import com.misnearzhang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhanglong on 2017/7/24.
 */
@Service("userService")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;
    @Override
    public List<User> getUserInfo() {
        return userDao.findUserByName();
    }
}
