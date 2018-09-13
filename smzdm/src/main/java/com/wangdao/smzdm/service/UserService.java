package com.wangdao.smzdm.service;

import com.wangdao.smzdm.bean.User;

public interface UserService {
    User findUserByUsernameAndPassword(String username, String password);

    User findUserByUsername(String username);

    boolean registerUser(User user_reg);

    User findUserById(Integer id);
}
