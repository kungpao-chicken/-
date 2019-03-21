package com.shanke.forum.configure;

import com.shanke.forum.interceptor.UserAuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @Bean
    public UserAuthInterceptor userAuthInterceptor() {
        return new UserAuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userAuthInterceptor()).excludePathPatterns("/user/login", "/user/register");
        super.addInterceptors(registry);
    }
}
