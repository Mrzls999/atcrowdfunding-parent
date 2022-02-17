package com.zls.atcrowdfunding.controller;

import com.zls.atcrowdfunding.bean.TAdmin;
import com.zls.atcrowdfunding.service.TAdminService;
import com.zls.atcrowdfunding.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author zls
 * @date 2022/2/16 10:52:12
 * @description XXX
 */
@Controller
public class TAdminController {

    @Autowired
    public TAdminService tAdminService;

    //解决登录表单的重复提交问题
    @RequestMapping("/main")
    public String main(){
        return "main";
    }

    /**
     * 登录方法，重定向到main.jsp-->解决登录表单的重复提交问题
     * @param tAdmin
     * @return
     */
    @RequestMapping("/login")
    public String login(TAdmin tAdmin, HttpSession session){
        try {
            TAdmin admin = tAdminService.getTAdmin(tAdmin);
            session.setAttribute("admin",admin);
        } catch (Exception e) {
            session.setAttribute("err",e.getMessage());//用户名或密码错误
            return "redirect:/welcome.jsp";
        }
        return "redirect:/main";
    }

}
