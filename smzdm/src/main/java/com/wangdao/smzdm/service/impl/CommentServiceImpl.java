package com.wangdao.smzdm.service.impl;

import com.wangdao.smzdm.bean.Comment;
import com.wangdao.smzdm.dao.CommentMapper;
import com.wangdao.smzdm.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;
    @Override
    public Comment findCommentById(Integer id) {
        return commentMapper.findCommentById(id);
    }

    @Override
    public void addComment(Comment comment) {
        commentMapper.addComment(comment);
    }

    @Override
    public Comment findCommentByUidAndCDate(Integer uid, Date createdDate) {
        return commentMapper.findCommentByUidAndCDate(uid,createdDate);
    }
}
