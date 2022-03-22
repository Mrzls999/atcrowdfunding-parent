package com.zls.security.config;

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
 * @date 2022/3/18 16:01:42
 * @description 权限框架的配置类，等价于配置文件
 * @Configuration 组合注解 管理spring的bean对象
 * @EnableWebSecurity 对springsecurity框架种注解的支持
 */

/**
 * 权限框架的配置类(等价于配置文件)
 *
 * @Configuration 组合注解   管理spring中的bean对象
 * @EnableWebSecurity 对springsecurity框架中注解的支持
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class AppWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
  /*    @Autowired
      private PasswordEncoder passwordEncoder;*/

    //认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // super.configure(auth);   如果认证失败 Bad credentials  密码失效
        //实验四：自定义认证用户信息   在服务器的内存中加载临时用户(最后要用数据库代替)  username loginacct
      /*  auth.inMemoryAuthentication().withUser("zhangsan").password("123").roles("学徒")
                .and()
                .withUser("lisi").password("123").authorities("太极拳", "七伤拳");*/
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);  父类中的方法默认禁用所有的资源
        //3.1	实验一：授权首页和静态资源
        http.authorizeRequests().antMatchers("/index.jsp", "/layui/**").permitAll()//放行
                //试验6  资源匹配角色或者权限
                /*       .antMatchers("/level1/**").hasRole("学徒")
                       .antMatchers("/level2/1").hasAuthority("太极拳")
                       .antMatchers("/level2/2").hasAuthority("七伤拳")
                       .antMatchers("/level2/3").hasAuthority("梯云纵")
                       *//*         .antMatchers("/level2/**").hasRole("大师")*//*

                .antMatchers("/level3/**").hasRole("宗师")*/


                .anyRequest().authenticated();//其他的资源必须要认证才能访问


        //HTTP Status 403 - Access Denied   访问受限
        //3.2	实验二：默认及自定义登录页    登录失败跳转到登录页面   	默认登录页：http.formLogin() 默认登录页  访问login控制器
        http.formLogin().loginPage("/index.jsp");
        //实验三：自定义表单登录逻辑分析
        http.formLogin().loginProcessingUrl("/login")//login 控制器
                .usernameParameter("username")// getParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/main.html");//登录成功之后跳转的控制器地址

//HTTP Status 403 - Could not verify the provided CSRF token because your session was not found.

        //服务器禁用csrf的验证
        http.csrf().disable();

        //登录注销
        http.logout();//session.invalidate();
//实验七：自定义访问拒绝处理页面  处理403访问受限的页面
        /*    http.exceptionHandling().accessDeniedPage("/unauth.html");*/
        http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                request.setAttribute("err", accessDeniedException.getMessage());
                request.getRequestDispatcher("/WEB-INF/views/unauth.jsp").forward(request, response);
            }
        });
//试验8
        http.rememberMe();
    }
}