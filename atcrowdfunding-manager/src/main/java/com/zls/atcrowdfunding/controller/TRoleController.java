package com.zls.atcrowdfunding.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zls.atcrowdfunding.bean.TRole;
import com.zls.atcrowdfunding.service.TRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author zls
 * @date 2022/3/5 11:46:13
 * @description 管理角色信息
 */
@Controller
public class TRoleController {

    @Autowired
    private TRoleService tRoleService;
    @RequestMapping("/role/index")
    public String toRoleIndex(){
        return "/role/index";
    }


    @ResponseBody
    @RequestMapping("/role/loadData")
    public PageInfo<TRole> loadData(@RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize",required = false,defaultValue = "3")Integer pageSize,
                           @RequestParam(value = "keyWord",required = false,defaultValue = "")String keyWord){
        PageHelper.startPage(pageNum,pageSize);
        List<TRole> tRoles = tRoleService.getTRoles(keyWord);
        return new PageInfo<TRole>(tRoles, 5);
    }

    @ResponseBody
    @RequestMapping("/role/addRole")
    public int addRole(TRole tRole){
        return tRoleService.saveRole(tRole)>0?1:0;
    }
}
