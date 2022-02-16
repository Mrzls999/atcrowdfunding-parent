package com.zls.atcrowdfunding.controller;

import com.zls.atcrowdfunding.bean.TAdmin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zls
 * @date 2022/2/16 10:52:12
 * @description XXX
 */
@Controller
public class TAdminController {

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
    public String login(TAdmin tAdmin){
        System.out.println(tAdmin);
        return "redirect:/main";
    }

}
