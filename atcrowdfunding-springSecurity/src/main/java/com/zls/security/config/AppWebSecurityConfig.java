package com.zls.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
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
@EnableWebSecurity
public class AppWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    //认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth); //如果认证失败 Bad credentials 密码错误
        //实验四：自定义认证用户信息 在服务器的内存中加载临时用户(最后用数据库代替) username loginacct
//        auth.inMemoryAuthentication().withUser("zhangsan").password("123").roles("学徒","大师")
//                .and()
//                .withUser("lisi").password("123").authorities("太极拳","七伤拳");
        auth.userDetailsService(userDetailsService);
    }

    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);//父类中的方法    默认禁用所有的资源
        //3.1实验一：授权首页和静态资源
        http.authorizeRequests().antMatchers("/index.jsp","/layui/**").permitAll()//放行
                                 //实验六：基于角色的访问控制[hasRole]
                                .antMatchers("/level1/**").hasRole("学徒")//** --> level1下边的所有，包括文件夹
                                //如果此用户没有这个权限[或角色]，则拒绝访问，不往下进行了；如果用户有这个权限[或角色]，则可以访问，不往下进行了
                                .antMatchers("/level2/1").hasAuthority("太极拳")
                                .antMatchers("/level2/2").hasAuthority("七伤拳")
                                .antMatchers("/level2/3").hasAuthority("梯云纵")
                                .antMatchers("/level2/**").hasRole("大师")
                                .anyRequest().authenticated();//其他的资源必须要认证才能访问（只要登录成功，就能访问）
        //HTTP状态 403 - 被禁止(denied) 访问受限
        //3.2实验二：默认及自定义登录页      登录失败跳转到登录页面
        // 默认登录页：http.formLogin()；默认登录页先去访问 控制器login 访问login后再跳转到那个丑的登录页
        http.formLogin()//在进行表单登录的时候
            .loginPage("/index.jsp");//如果出现错误，则跳转到登录页面
        //实验三：自定义表单登录逻辑分析
        http.formLogin().loginProcessingUrl("/login")//登录处理路径
                        .usernameParameter("username")// getParameter("username")-->username和password是在默认登录页里边的那个，所以，就这样写了（可以改）
                        .passwordParameter("password")
                        .defaultSuccessUrl("/main.html");//如果username和password认证成功，登录成功之后跳转的控制器地址 "/main.html"
        //HTTP状态 403 - 被禁止（denied） Could not verify the provided CSRF token because your session was not found.
        //服务器禁用csrf的验证  (防止跨站请求伪造，那么就不要禁用了)
        http.csrf().disable();//如果登录注销使用的是超链接形式的话，那么会出现 HTTP?? 404 - ???
        //如果使用超链接形式进行退出登录，那么得禁用csrf
        //如果想开启csrf，那么不能用超链接形式，得用提交表单的形式

        //实验五：用户注销完成
        //登录注销
        http.logout();//session.invalidate();

        //实验七：自定义访问拒绝处理页面
        http.exceptionHandling()//异常处理
                //.accessDeniedPage("/unauth.html");//经过拒绝的页面[通过/unauth.html处理器，跳转到指定的页面]
                .accessDeniedHandler(new AccessDeniedHandler() {//自定义
                    @Override
                    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
                        //携带错误信息
                        httpServletRequest.setAttribute("err",e.getMessage());
                        //请求转发
                        httpServletRequest.getRequestDispatcher("/WEB-INF/views/unauth.jsp").forward(httpServletRequest,httpServletResponse);
                    }
                });
        //实验八：记住我功能-Cookie版
        http.rememberMe();
    }
}
