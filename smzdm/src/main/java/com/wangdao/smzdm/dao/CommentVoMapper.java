package com.wangdao.smzdm.dao;

import com.wangdao.smzdm.bean.CommentVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentVoMapper {

    List<CommentVo> findCommentVosByNid(Integer id);
    @Insert("insert into t_commentvo values(null,#{cv.uid},#{cv.cid},#{cv.nid})")
    void addCommentVo(@Param("cv") CommentVo commentVo);
}
