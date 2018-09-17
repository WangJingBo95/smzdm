package com.wangdao.smzdm.controller;

import com.wangdao.smzdm.bean.Conversation;
import com.wangdao.smzdm.bean.ConversationVo;
import com.wangdao.smzdm.bean.User;
import com.wangdao.smzdm.service.MessageService;
import com.wangdao.smzdm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Controller
public class MessageController {
    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;

    /**
     * 打开站内信列表
     *
     * @param mv
     * @return
     */
    @RequestMapping("/msg/list")
    public ModelAndView toMsgList(ModelAndView mv, HttpSession session) {
        User user_login = (User) session.getAttribute("user");
        Integer tid = user_login.getId();

        //查询发给该登录用户的所有消息
        List<Conversation> conversationList = messageService.findMsgByTid(tid);

        //将该用户的和其他用户的会话信息按发件人不同来分类
        HashSet<String> strs = new HashSet<>();
        for (Conversation conversation : conversationList) {
            String conversationId = conversation.getConversationId();
            strs.add(conversationId);
        }

        //构建一个会话集合，每个对象为一用户和登录用户的会话消息的链表
        List<ConversationVo> conversationVoList = new ArrayList<>();
        //将%发给登录用户的消息封装，同时查询登录用户和对应用户发送的消息
        for (String s : strs) {
            String[] split = s.split("_");
            String to_from = split[1] + "_" + split[0];
            ArrayList<Conversation> cs_to_from = messageService.findMSgByConversationId(to_from);

            ConversationVo conversationVo = new ConversationVo();
            //将登录用户发给某用户的消息放进链表
            ArrayList<Conversation> all = new ArrayList<>(cs_to_from);
            int count = 0;
            for (Conversation con : conversationList) {
                String conversationId = con.getConversationId();
                if (s.equals(conversationId)) {
                    all.add(con);
                    //获取发信人
                    Integer fromid = con.getFromid();
                    User userById = userService.findUserById(fromid);
                    conversationVo.setUser(userById);
                    //获取该信息是否被读取
                    Integer unread1 = con.getUnread();
                    if (unread1 == 1) {
                        //没有被读取
                        count++;
                    }
                }
            }
            conversationVo.setUnread(count);
            conversationVo.setCount(all.size());
            conversationVo.setConversations(all);
            conversationVo.setConversation(all.get(all.size() - 1));

            conversationVoList.add(conversationVo);
        }


        mv.addObject("conversations", conversationVoList);
        mv.setViewName("/letter");
        return mv;
    }

    /**
     * 打开消息详情页面
     *
     * @param conversationId
     * @param mv
     * @return
     */
    @RequestMapping("/msg/detail")
    public ModelAndView detail(String conversationId, ModelAndView mv) {
        //将from_to和to_from的消息查询，然后按时间倒序
        String[] split = conversationId.split("_");
        String to_from = split[1] + "_" + split[0];

        ArrayList<Conversation> messages = messageService.findMSgByTwoConversationId(conversationId, to_from);
        if (messages != null) {
            for (Conversation message : messages) {
                messageService.updateUnreadStatusById(message.getId());
                Integer fromid = message.getFromid();
                User userById = userService.findUserById(fromid);
                String name = userById.getName();
                String headUrl = userById.getHeadUrl();
                message.setHeadUrl(headUrl);
                message.setName(name);
            }
            //倒序
            Collections.reverse(messages);
        }
        mv.addObject("messages", messages);
        mv.setViewName("/letterDetail");
        return mv;
    }

    @RequestMapping("/msg/delete")
    public void delteOne(String conversationId, Integer cid, ModelAndView mv, HttpServletResponse response) {
        messageService.deleteOneById(cid);
    }

}
