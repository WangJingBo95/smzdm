package com.wangdao.smzdm.controller;

import com.wangdao.smzdm.bean.User;
import com.wangdao.smzdm.bean.Vo;
import com.wangdao.smzdm.service.UserService;
import com.wangdao.smzdm.service.VoService;
import org.apache.velocity.tools.generic.DateTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class FormController {
    @Autowired
    VoService voService;
    @Autowired
    UserService userService;

    /**
     * 打开首页
     * @param mv
     * @param pop
     * @param session
     * @param request
     * @return
     */
    @ResponseBody
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

        mv.addObject("vos", vos);
//        mv.addObject("date", new DateTool());
        session.setAttribute("date", new DateTool());
        mv.setViewName("home");
        return mv;
    }

    /**
     * 打开html
     * @param mv
     * @param formName
     * @return
     */
    @ResponseBody
    @RequestMapping("/{formName}")
    public ModelAndView goPage(ModelAndView mv, @PathVariable String formName) {
        if ("favicon".equals(formName)) {
            mv.setViewName("home");
            return mv;
        }
        mv.setViewName(formName);
        return mv;
    }

}
