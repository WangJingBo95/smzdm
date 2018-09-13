package com.wangdao.smzdm.service.impl;

import com.wangdao.smzdm.bean.Vo;
import com.wangdao.smzdm.dao.VoMapper;
import com.wangdao.smzdm.service.VoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoServiceImpl implements VoService {
    @Autowired
    VoMapper voMapper;
    @Override
    public List<Vo> findAllVo() {
        return voMapper.findAllVo();
    }

    @Override
    public Vo findVoByNid(Integer id) {
        return voMapper.findVoByNid(id);
    }

    @Override
    public void addVo(Vo vo) {
        voMapper.addVo(vo);
    }
}
