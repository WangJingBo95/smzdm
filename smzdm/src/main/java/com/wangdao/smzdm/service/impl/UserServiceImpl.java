package com.wangdao.smzdm.service.impl;

import com.wangdao.smzdm.bean.Conversation;
import com.wangdao.smzdm.bean.User;
import com.wangdao.smzdm.dao.UserMapper;
import com.wangdao.smzdm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        return userMapper.findUserByUsernameAndPassword(username, password);
    }

    @Override
    public User findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    @Override
    public boolean registerUser(User user_reg) {

        return userMapper.registerUser(user_reg) == 1;
    }

    @Override
    public User findUserById(Integer id) {
        return userMapper.findUserById(id);
    }

    @Override
    public void addMsg(Conversation conversation) {
        userMapper.addMsg(conversation);
    }

}
