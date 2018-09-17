package com.wangdao.smzdm.service;

import com.wangdao.smzdm.bean.Conversation;

import java.util.ArrayList;
import java.util.List;

public interface MessageService {

    List<Conversation> findMsgByTid(Integer tid);

    ArrayList<Conversation> findMSgByConversationId(String to_from);

    ArrayList<Conversation> findMSgByTwoConversationId(String conversationId, String to_from);

    void updateUnreadStatusById(Integer id);

    void deleteOneById(Integer cid);
}
