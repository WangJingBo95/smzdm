package com.wangdao.smzdm.conf;

import com.wangdao.smzdm.interceptor.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Component
public class MyWebConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    MyInterceptor myInterceptor;
//, "/like", "/dislike"
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor).addPathPatterns("/user/*/*", "/user/*","/msg/*");
        super.addInterceptors(registry);
    }
}
