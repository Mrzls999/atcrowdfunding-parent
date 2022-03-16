package com.zls.atcrowdfunding.controller;

import com.alibaba.druid.support.json.JSONParser;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.zls.atcrowdfunding.bean.TRole;
import com.zls.atcrowdfunding.service.TRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
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


    //加载角色信息
    @ResponseBody
    @RequestMapping("/role/loadData")
    public PageInfo<TRole> loadData(@RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize",required = false,defaultValue = "3")Integer pageSize,
                           @RequestParam(value = "keyWord",required = false,defaultValue = "")String keyWord){
        PageHelper.startPage(pageNum,pageSize);
        List<TRole> tRoles = tRoleService.getTRoles(keyWord);
        return new PageInfo<>(tRoles, 5);
    }

    //添加角色
    @ResponseBody
    @RequestMapping("/role/addRole")
    public String addRole(TRole tRole){
        int row=  tRoleService.saveRole(tRole);
        if(row>0){
            return "yes";
        }
        return "no";
    }

    //删除一个角色
    @ResponseBody
    @RequestMapping("/role/deleteOneRole")
    public int deleteOneRole(int id){
        return tRoleService.deleteRole(id);
    }

    //删除一整页角色
    @ResponseBody
    @RequestMapping("/role/deleteOnePageRoles")
    public int deleteOnePageRoles(String ids){
        return tRoleService.deleteOnePageRoles(ids);
    }
}
