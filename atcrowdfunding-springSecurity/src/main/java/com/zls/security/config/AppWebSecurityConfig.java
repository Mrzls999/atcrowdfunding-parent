package com.zls.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author zls
 * @date 2022/3/18 16:01:42
 * @description 权限框架的配置类，等价于配置文件
 * @Configuration 组合注解 管理spring的bean对象
 * @EnableWebSecurity 对springsecurity框架种注解的支持
 */
@Configuration
@EnableWebSecurity
public class AppWebSecurityConfig extends WebSecurityConfigurerAdapter {
    //认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http); 父类种的方法默认禁用所有的资源
        //实验一：授权首页和静态资源
        http.authorizeRequests()
                .antMatchers("/layui/**","/index.jsp").permitAll()//设置匹配的资源放行
                .anyRequest().authenticated();//剩余任何资源必须认证 才能访问
        //实验二：默认及自定义登录页 登录失败跳转到登录页面 * 默认登录页 http.formLogin() 首先访问login控制器
        http.formLogin().loginPage("/index.jsp");//如果登录失败，则跳转到此处
    }
}
