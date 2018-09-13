package com.wangdao.smzdm.service;

import com.wangdao.smzdm.bean.CommentVo;

import java.util.List;

public interface CommentVoService {
    List<CommentVo> findCommentVosByNid(Integer id);

    void addCommentVo(CommentVo commentVo);
}
