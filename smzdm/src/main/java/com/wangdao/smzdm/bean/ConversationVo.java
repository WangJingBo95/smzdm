package com.wangdao.smzdm.bean;

import java.util.ArrayList;

public class ConversationVo {
    Integer id;
    ArrayList<Conversation> conversations;
    Conversation conversation;
    User user;
    Integer unread;
    Integer count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ArrayList<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(ArrayList<Conversation> conversations) {
        this.conversations = conversations;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getUnread() {
        return unread;
    }

    public void setUnread(Integer unread) {
        this.unread = unread;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
}
