package com.wangdao.smzdm.dao;

import com.wangdao.smzdm.bean.Vo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VoMapper {
    List<Vo> findAllVo();

    Vo findVoByNid(Integer id);

    @Insert("insert into t_vo values(null,#{vo.uid},#{vo.nid},#{vo.like})")
    void addVo(@Param("vo") Vo vo);
}
