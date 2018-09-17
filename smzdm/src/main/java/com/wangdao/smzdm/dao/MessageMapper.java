package com.wangdao.smzdm.dao;

import com.wangdao.smzdm.bean.Conversation;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface MessageMapper {
    @Select("select * from t_conversation where toid=#{t}")
    List<Conversation> findMsgByTid(@Param("t") Integer tid);

    @Select("select * from t_conversation where conversationId=#{cid}")
    ArrayList<Conversation> findMSgByConversationId(@Param("cid") String to_from);

    @Select("select * from t_conversation where conversationId=#{cid1} or conversationId=#{cid2}")
    ArrayList<Conversation> findMSgByTwoConversationId(@Param("cid1") String conversationId, @Param("cid2") String to_from);

    @Update("update t_conversation set unread=0 where id=#{id}")
    void updateUnreadStatusById(@Param("id") Integer id);

    @Delete("delete from t_conversation where id=#{id}")
    void deleteOneById(@Param("id") Integer cid);
    @Select("select * from t_conversation where fromid=#{id} or toid=#{id}")
    List<Conversation> findMsgByTidOrFid(@Param("id") Integer tid);
}
