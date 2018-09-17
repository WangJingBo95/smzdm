package com.wangdao.smzdm.service.impl;

import com.wangdao.smzdm.bean.Conversation;
import com.wangdao.smzdm.dao.MessageMapper;
import com.wangdao.smzdm.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageMapper messageMapper;
    @Override
    public List<Conversation> findMsgByTid(Integer tid) {
        return messageMapper.findMsgByTid(tid);
    }

    @Override
    public ArrayList<Conversation> findMSgByConversationId(String to_from) {
        return messageMapper.findMSgByConversationId(to_from);
    }

    @Override
    public ArrayList<Conversation> findMSgByTwoConversationId(String conversationId, String to_from) {
        return messageMapper.findMSgByTwoConversationId(conversationId,to_from);
    }

    @Override
    public void updateUnreadStatusById(Integer id) {
        messageMapper.updateUnreadStatusById(id);
    }

    @Override
    public void deleteOneById(Integer cid) {
        messageMapper.deleteOneById(cid);
    }

    @Override
    public List<Conversation> findMsgByTidOrFid(Integer tid) {
        return messageMapper.findMsgByTidOrFid(tid);
    }
}
