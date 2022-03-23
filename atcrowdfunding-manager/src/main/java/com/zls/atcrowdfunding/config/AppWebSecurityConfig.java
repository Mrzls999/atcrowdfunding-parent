package com.zls.atcrowdfunding.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zls
 * @date 2022/3/22 20:56:54
 * @description 权限框架时配置类（等价于配置文件）
 * @Configuration 组合注解 管理spring中的bean对象
 * @EnableWebSecurity   对springSecurity框架中注解的支持
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class AppWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    //认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());//使用BCrypt加密
    }

    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/welcome.jsp","/static/**").permitAll()//放行
                                .anyRequest().authenticated();//其他的资源必须要认证才能访问（只要登录成功，就能访问）
        http.formLogin()//在进行表单登录的时候
            .loginPage("/welcome.jsp");//如果出现错误，则跳转到登录页面

        http.formLogin().loginProcessingUrl("/login")//登录处理路径
                        .usernameParameter("loginacct")// getParameter("loginacct")-->username和password是在默认登录页里边的那个，所以，就这样写了（可以改）
                        .passwordParameter("userpswd")
                        .defaultSuccessUrl("/actrowfunding/main");//如果username和password认证成功，登录成功之后跳转的控制器地址 "/main.html"
        //登录注销
        http.logout();//session.invalidate();

        http.exceptionHandling()//异常处理
                .accessDeniedHandler(new AccessDeniedHandler() {//自定义
                    @Override
                    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
                        //携带错误信息
                        httpServletRequest.setAttribute("err",e.getMessage());
                        //请求转发
                        httpServletRequest.getRequestDispatcher("/WEB-INF/views/unauth.jsp").forward(httpServletRequest,httpServletResponse);
                    }
                });
        http.rememberMe();
    }
}
