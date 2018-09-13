package com.wangdao.smzdm.service;

import com.wangdao.smzdm.bean.Comment;

import java.util.Date;

public interface CommentService {
    Comment findCommentById(Integer id);

    void addComment(Comment comment);

    Comment findCommentByUidAndCDate(Integer uid, Date createdDate);
}
