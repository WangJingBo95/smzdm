package com.wangdao.smzdm.service;

import com.wangdao.smzdm.bean.Vo;

import java.util.List;

public interface VoService {
    List<Vo> findAllVo();

    Vo findVoByNid(Integer id);

    void addVo(Vo vo);
}
