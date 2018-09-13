package com.wangdao.smzdm.service.impl;

import com.wangdao.smzdm.bean.CommentVo;
import com.wangdao.smzdm.dao.CommentVoMapper;
import com.wangdao.smzdm.service.CommentVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentVoServiceImpl implements CommentVoService {
    @Autowired
    CommentVoMapper commentVoMapper;

    /**
     * 根据新闻的id,查找其所有的评论
     * @param id
     * @return
     */
    @Override
    public List<CommentVo> findCommentVosByNid(Integer id) {
        return commentVoMapper.findCommentVosByNid(id);
    }

    @Override
    public void addCommentVo(CommentVo commentVo) {
        commentVoMapper.addCommentVo(commentVo);
    }
}
