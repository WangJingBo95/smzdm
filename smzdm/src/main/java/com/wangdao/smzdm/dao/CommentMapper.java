package com.wangdao.smzdm.dao;

import com.wangdao.smzdm.bean.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

@Mapper
public interface CommentMapper {
    @Select("select * from t_comment where id=#{id}")
    Comment findCommentById(@Param("id") Integer id);

    @Insert("insert into t_comment values(null,#{comment.createdDate},#{comment.content},#{comment.uid})")
    void addComment(@Param("comment") Comment comment);

    @Select("select * from t_comment where uid=#{uid} and createdDate=#{cdate}")
    Comment findCommentByUidAndCDate(@Param("uid") Integer uid, @Param("cdate") Date createdDate);
}
