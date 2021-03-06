package com.zls.atcrowdfunding.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zls.atcrowdfunding.bean.TAdmin;
import com.zls.atcrowdfunding.bean.TMenu;
import com.zls.atcrowdfunding.service.TAdminService;
import com.zls.atcrowdfunding.service.TMenuService;
import com.zls.atcrowdfunding.utils.Const;
import com.zls.atcrowdfunding.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.*;

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
//    @RequestMapping("/login")
//    public String login(TAdmin tAdmin, HttpSession session){
//        try {
//            TAdmin admin = tAdminService.getTAdmin(tAdmin);
//            session.setAttribute("admin",admin);
//        } catch (Exception e) {
//            session.setAttribute("err",e.getMessage());//用户名或密码错误
//            return "redirect:/welcome.jsp";
//        }
//        return "redirect:/actrowfunding/main";
//    }

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
     * 分页查询
     * pageNum 当前的页码
     * pageSize 每页显示的行数
     * keyWord 查询条件
     * @return
     */
    @PreAuthorize("hasRole('学徒') AND hasAuthority('putong:luohan')")
    @RequestMapping("/admin/index")
    public String index(@RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                        @RequestParam(value = "pageSize",required = false,defaultValue = "3") Integer pageSize,
                        @RequestParam(value = "keyWord",required = false,defaultValue = "") String keyWord,
                        Model model,
                        HttpSession session){
//        if (session == null||session.getAttribute("admin")==null) {//如果用户未登录则跳转到欢迎页面
//            return "redirect:/welcome.jsp";
//        }else {
            PageHelper.startPage(pageNum,pageSize);//开启分页 limit ?,?[pageSize-->每页显示多少条数据]
            List<TAdmin> tAdmins = tAdminService.listAdminByPage(keyWord);
            PageInfo<TAdmin> pageInfo = new PageInfo<>(tAdmins,5);//有多少个按钮页面[默认是8个]
            model.addAttribute("pageInfo", pageInfo);
            return "admin/index";
//        }

    }

    /**
     * 系统退出
     */
//    @RequestMapping("/admin/logOut")
//    public String logOut(HttpSession session){
//        if(session!=null){//如果session不为空，则令其手动失效
//            session.invalidate();
//        }
//        return "redirect:/welcome.jsp";
//    }

    /**
     * 先跳转到新增用户的界面
     * @return
     */
    @RequestMapping("/admin/toAdd")
    public String toAdd(){
        return "/admin/add";
    }

    /**
     * 新增用户
     */
    @RequestMapping("/admin/saveAdmin")
    public String saveAdmin(TAdmin admin){
        tAdminService.saveAdmin(admin);
        return "redirect:/admin/index?pageNum="+Integer.MAX_VALUE;
        ////重定向到index，这样会默认查询一遍
        //新插入的数据在最后一页，那么设置pageNum=Integer.MAX_VALUE,详见spring-mybatis分页合理化
    }

    /**
     * 先跳转到修改页面，并将数据回显
     */
    @RequestMapping("/admin/toEdit")
    public String toEdit(Integer id,Integer pageNum, Map<String,Object> map){
        TAdmin admin = tAdminService.getAdminById(id);
        map.put("admin",admin);
        map.put("pageNum",pageNum);
        return "admin/edit";
    }

    /**
     * 修改用户
     * @return
     */
    @RequestMapping("/admin/editAdmin")
    public String editAdmin(TAdmin admin,Integer pageNum){
        tAdminService.editAdmin(admin);
        return "redirect:/admin/index?pageNum="+pageNum;
    }

    /**
     * 删除单个用户（没做级联删除）
     */
    @RequestMapping("/admin/deleteAdmin")
    public String deleteAdmin(Integer id){
        tAdminService.deleteAdminById(id);
        return "redirect:/admin/index";
    }

    /**
     * 删除多个用户
     */
    @RequestMapping("/admin/deleteSelectedAdmins")
    public String deleteAdmins(String ids){
//        String[] adminIds = ids.split(",");
//        for (String adminId : adminIds) {如果删除很多用户的话，会往oracle发送太多请求，所以这样不行
//            tAdminService.deleteAdminById(Integer.valueOf(adminId));
//        }
        tAdminService.deleteAdminByIds(ids);
        return "redirect:/admin/index";
    }
}
