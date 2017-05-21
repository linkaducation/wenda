package com.zm.configuration;

import com.zm.intercepter.LoginRequirdInterceptor;
import com.zm.intercepter.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Ellen on 2017/5/21.
 */
@Component
public class WendaWebConfiguration  extends WebMvcConfigurerAdapter{
    @Autowired
    PassportInterceptor passportInterceptor;

    @Autowired
    LoginRequirdInterceptor loginRequirdInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(loginRequirdInterceptor).addPathPatterns("/user/*");
        super.addInterceptors(registry);
    }
}
