package com.wangdao.smzdm.controller;


import com.wangdao.smzdm.bean.User;
import com.wangdao.smzdm.service.NewsService;
import com.wangdao.smzdm.service.UserService;
import com.wangdao.smzdm.service.VoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    VoService voService;
    @Autowired
    NewsService newsService;

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @param rember
     * @return
     */
    @RequestMapping("/login")
    public Map<String, Object> login(String username, String password, Integer rember, HttpSession session, HttpServletResponse response) {
        HashMap<String, Object> map = new HashMap<>();

        User user_find = userService.findUserByUsername(username);
        if (user_find == null) {
            map.put("msgname", "用户名错误");
            return map;
        }

        User user_find2 = userService.findUserByUsernameAndPassword(username, password);
        if (user_find2 != null) {
            map.put("code", 0);
            session.setAttribute("user", user_find2);
            //记住用户登陆信息
            if (rember == 1) {
                //创建两个Cookie对象
                Cookie nameCookie = new Cookie("username", username);
                //设置Cookie的有效期为3天
                nameCookie.setMaxAge(60 * 60 * 24 * 3);
                Cookie pwdCookie = new Cookie("password", password);
                pwdCookie.setMaxAge(60 * 60 * 24 * 3);
                //设置Cookie的路径，保证首页可以读取到
                nameCookie.setPath("/");
                pwdCookie.setPath("/");
                response.addCookie(nameCookie);
                response.addCookie(pwdCookie);
            }
        } else {
            map.put("msgpwd", "密码错误");
        }
        return map;
    }

    /**
     * 用户注册
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/reg")
    public Map<String, Object> reg(String username, String password, Model model, HttpSession session) {
        HashMap<String, Object> map = new HashMap<>();

        User user_find = userService.findUserByUsername(username);
        if (user_find != null) {
            map.put("msgname", "用户名重复");
            return map;
        } else {
            User user_reg = new User();
            user_reg.setName(username);
            user_reg.setPassword(password);
            //共有13个头像,随机产生一个整数,为用户分配头像
            int headpic = new Random().nextInt(14);
            String headUrl = "/avatars/" + headpic + ".jpg";
            user_reg.setHeadUrl(headUrl);
            boolean ret = userService.registerUser(user_reg);
            if (ret) {
                map.put("code", 0);
                User user_had_reg = userService.findUserByUsername(username);
                session.setAttribute("user", user_had_reg);
            } else {
                map.put("msgpwd", "xxx");
            }
            return map;
        }
    }

    /**
     * 获取用户个人信息
     *
     * @param id
     * @param mv
     * @return
     */
    @RequestMapping("/user/{id}")
    public ModelAndView showUser(@PathVariable Integer id, ModelAndView mv) {
        User user = userService.findUserById(id);
        mv.addObject("user_check", user);
        mv.setViewName("personal");
        return mv;
    }

    /**
     * 用户注销
     *
     * @param mv
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public ModelAndView logout(ModelAndView mv, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        session.invalidate();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                //立即销毁保存用户登录信息的cookie
                if ("username".equals(cookie.getName()) || "password".equals(cookie.getName())) {
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
        }
        mv.setViewName("redirect:/");
        return mv;
    }
}
