package com.wangdao.smzdm.service;

import com.wangdao.smzdm.bean.Conversation;
import com.wangdao.smzdm.bean.User;

import java.util.List;

public interface UserService {
    User findUserByUsernameAndPassword(String username, String password);

    User findUserByUsername(String username);

    boolean registerUser(User user_reg);

    User findUserById(Integer id);

    void addMsg(Conversation conversation);
}
