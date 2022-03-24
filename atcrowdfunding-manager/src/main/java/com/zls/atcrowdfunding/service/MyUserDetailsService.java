package com.zls.atcrowdfunding.service;

import com.zls.atcrowdfunding.bean.TAdmin;
import com.zls.atcrowdfunding.bean.TAdminExample;
import com.zls.atcrowdfunding.bean.TPermission;
import com.zls.atcrowdfunding.bean.TRole;
import com.zls.atcrowdfunding.mapper.TAdminMapper;
import com.zls.atcrowdfunding.mapper.TPermissionMapper;
import com.zls.atcrowdfunding.mapper.TRoleMapper;
import com.zls.atcrowdfunding.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zls
 * @date 2022/3/23 23:31:13
 * @description XXX
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private TAdminMapper tAdminMapper;
    @Autowired
    private TRoleMapper tRoleMapper;
    @Autowired
    private TPermissionMapper tPermissionMapper;
    /**
     * 登录认证 查询用户的角色和权限
     * @param userName
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        TAdminExample example = new TAdminExample();
        TAdminExample.Criteria criteria = example.createCriteria();
        criteria.andLoginacctEqualTo(userName);
        List<TAdmin> tAdmins = tAdminMapper.selectByExample(example);
        TAdmin tAdmin = tAdmins.get(0);

        //存放角色和权限的集合
        Set<GrantedAuthority> authorities = new HashSet<>();
        Long id = tAdmin.getId();
        List<TRole> roles = tRoleMapper.listRoleByAdminId(id);
        List<TPermission> permissions = tPermissionMapper.listPermissionByAdminId(id);
        //roles,permissions的值取出来放到统一的set集合中
        for (TRole role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));//注意：在权限集合中，必须区分角色ROLE_XXXXX
        }
        for (TPermission permission : permissions) {
            if(StringUtil.isNotEmpty(permission.getName())){
                authorities.add(new SimpleGrantedAuthority(permission.getName()));
            }
        }
        return new User(tAdmin.getLoginacct(), tAdmin.getUserpswd(),authorities);//bean中的loginacct和userpswd是string类型的，所以不用转了
    }
}
