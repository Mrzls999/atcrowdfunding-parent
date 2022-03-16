package com.zls.atcrowdfunding.service;

import com.zls.atcrowdfunding.bean.TRole;
import com.zls.atcrowdfunding.bean.TRoleExample;
import com.zls.atcrowdfunding.mapper.TRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zls
 * @date 2022/3/5 12:20:46
 * @description 角色信息Service层
 */
@Service
public class TRoleService {
    @Autowired
    private TRoleMapper tRoleMapper;

    //查询角色信息
    public List<TRole> getTRoles(String keyWord){
        TRoleExample example = new TRoleExample();
        TRoleExample.Criteria criteria = example.createCriteria();
        criteria.andNameLike("%"+keyWord+"%");
        return tRoleMapper.selectByExample(example);
    }

    //保存角色信息
    public Integer saveRole(TRole tRole) {
        return tRoleMapper.insertSelective(tRole);
    }

    //删除单个角色
    public int deleteRole(int id) {
        return tRoleMapper.deleteByPrimaryKey((long) id) ==1 ? 1 : 0;//如果影响的行数等于1则返回1，否则返回0，1表示成功删除
    }

    //删除一整页角色
    public int deleteOnePageRoles(String ids) {
//        如果传进来的是"["43","44"]",则
//        String replace1 = ids.replace("[", "");
//        String replace2 = replace1.replace("]", "");
//        String replace3 = replace2.replace("\"", "");
//        String[] split = replace3.split(",");
//        如果是另一种"43,44"
        String[] split = ids.split(",");
        List<Long> roleIds = new ArrayList<>();
        for (String s : split) {
            roleIds.add(Long.valueOf(s));
        }

        TRoleExample example = new TRoleExample();
        TRoleExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(roleIds);
        return tRoleMapper.deleteByExample(example);
    }
}
