package com.wangdao.smzdm.dao;

import com.wangdao.smzdm.bean.Conversation;
import com.wangdao.smzdm.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select({"select * from t_user where name=#{name} and password=#{pwd}"})
    User findUserByUsernameAndPassword(@Param("name") String username, @Param("pwd") String password);

    @Select("select * from t_user where name=#{name}")
    User findUserByUsername(@Param("name") String username);

    @Select("select * from t_user where id=#{id}")
    User findUserById(@Param("id") Integer id);

    @Insert("insert into t_user values(null,#{user.name},#{user.password},#{user.headUrl})")
    int registerUser(@Param("user") User user_reg);

    @Insert("insert into t_conversation values(null,#{co.fromid},#{co.toid}," +
            "#{co.content},#{co.createdDate},#{co.unread},#{co.conversationId})")
    void addMsg(@Param("co") Conversation conversation);


}
