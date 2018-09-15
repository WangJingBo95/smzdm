package com.wangdao.smzdm.controller;

import com.wangdao.smzdm.bean.News;
import com.wangdao.smzdm.bean.User;
import com.wangdao.smzdm.bean.Vo;
import com.wangdao.smzdm.service.UserService;
import com.wangdao.smzdm.service.VoService;
import com.wangdao.smzdm.utils.JedisUtils;
import org.apache.velocity.tools.generic.DateTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class FormController {
    @Autowired
    VoService voService;
    @Autowired
    UserService userService;

    /**
     * 打开首页
     *
     * @param mv
     * @param pop
     * @param session
     * @param request
     * @return
     */
    @RequestMapping("/")
    public ModelAndView goHomePage(ModelAndView mv, @RequestParam(name = "pop", defaultValue = "0") String pop,
                                   HttpSession session, HttpServletRequest request) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            mv.addObject("pop", 0);
        } else {
            //从cookie中查找用户信息
            String username = "";
            String password = "";
            //获取当前站点的所有Cookie
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {//对cookies中的数据进行遍历，找到用户名、密码的数据
                    if ("username".equals(cookie.getName())) {
                        username = cookie.getValue();
                    } else if ("password".equals(cookie.getName())) {
                        password = cookie.getValue();
                    }
                }
                if (!username.isEmpty() && !password.isEmpty()) {
                    //查找到用户保存的登陆信息
                    User user_find_cookie = userService.findUserByUsernameAndPassword(username, password);
                    session.setAttribute("user", user_find_cookie);
                    mv.addObject("pop", 0);
                } else {
                    mv.addObject("pop", pop);
                }
            } else {
                mv.addObject("pop", pop);
            }
        }

        List<Vo> vos = voService.findAllVo();

        //遍历vo，为其中的likeCount赋值，Redis
        for (Vo vo : vos) {
            Long scard_dislike = JedisUtils.scard(vo.getNid() + "dislike");
            Long scard_like = JedisUtils.scard(vo.getNid() + "like");
            //将登录用户点过赞或者踩的进行标记
            if (user!=null){
                Boolean is_dislike = JedisUtils.sismember(vo.getNid() + "dislike", user.getId().toString());
                Boolean is_like = JedisUtils.sismember(vo.getNid() + "like", user.getId().toString());
                if (is_like) {
                    vo.setLike(1);
                } else if (is_dislike) {
                    vo.setLike(-1);
                }
            }
            Long msg = scard_like - scard_dislike;
            News news = vo.getNews();
            news.setLikeCount(msg.intValue());
            vo.setNews(news);

        }

        mv.addObject("vos", vos);
        Object date = session.getAttribute("date");
        if (date == null) {
            session.setAttribute("date", new DateTool());
        }
        mv.setViewName("/home");

        return mv;
    }

}
