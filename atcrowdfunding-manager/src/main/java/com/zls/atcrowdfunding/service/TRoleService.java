package com.zls.atcrowdfunding.service;

import com.zls.atcrowdfunding.bean.TRole;
import com.zls.atcrowdfunding.bean.TRoleExample;
import com.zls.atcrowdfunding.mapper.TRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
