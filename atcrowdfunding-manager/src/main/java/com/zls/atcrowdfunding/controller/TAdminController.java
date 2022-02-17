package com.zls.atcrowdfunding.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zls.atcrowdfunding.bean.TAdmin;
import com.zls.atcrowdfunding.bean.TMenu;
import com.zls.atcrowdfunding.service.TAdminService;
import com.zls.atcrowdfunding.service.TMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author zls
 * @date 2022/2/16 10:52:12
 * @description XXX
 */
@Controller
public class TAdminController {

    @Autowired
    public TAdminService tAdminService;
    @Autowired
    private TMenuService tMenuService;

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
        return "redirect:/actrowfunding/main";
    }

    /**
     * 获取side侧边栏数据
     * @param session
     * @return
     */
    @RequestMapping("/actrowfunding/main")
    public String side(HttpSession session){
        List<TMenu> tMenus = tMenuService.listMenu();
        List<TMenu> pMenus = new ArrayList<>();

        for (TMenu tMenu : tMenus) {
            if (tMenu.getPid() == 0) {//将父节点添加到集合中
                pMenus.add(tMenu);
            }
        }
        for (TMenu tMenu : tMenus) {
            if (tMenu.getId() != 0) {//如果是子节点
                for (TMenu pMenu : pMenus) {
                    if(Objects.equals(pMenu.getId(), tMenu.getPid())){//如果父节点的id和子节点的父id一样，那么
                        pMenu.getChildMenus().add(tMenu);
                    }
                }
            }
        }
        session.setAttribute("pMenus",pMenus);//将左侧页面节点添加到session域中
        return "redirect:/main";
    }

    /**
     * pageNum 当前的页码
     * pageSize 每页显示的行数
     * keyWord 查询条件
     * @return
     */
    @RequestMapping("/admin/index")
    public String index(@RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                        @RequestParam(value = "pageSize",required = false,defaultValue = "2") Integer pageSize,
                        @RequestParam(value = "keyWord",required = false,defaultValue = "") String keyWord,
                        Model model){
        PageHelper.startPage(pageNum,5);//开启分页 limit ?,?[pageSize-->每页显示多少条数据]
        List<TAdmin> tAdmins = tAdminService.listAdminByPage(keyWord);
        PageInfo<TAdmin> pageInfo = new PageInfo<>(tAdmins,5);//有多少个按钮页面[默认是8个]
        model.addAttribute("pageInfo", pageInfo);
        return "admin/index";
    }

}
