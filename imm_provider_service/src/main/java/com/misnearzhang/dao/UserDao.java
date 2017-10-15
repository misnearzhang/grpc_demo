package com.misnearzhang.dao;

import com.misnearzhang.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * Created by zhanglong on 2017/7/24.
 */
@Mapper
public interface UserDao {
        List<User> findUserByName();
}
