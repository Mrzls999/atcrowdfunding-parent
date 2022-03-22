package com.zls.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService {
     @Autowired
     private JdbcTemplate jdbcTemplate;
    /**
     * 登录验证方法
     * @param username  loginacct
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /**
         * 单行数据 queryForObject
         * 多行  query()
         * 增删改  update |updateBath
         */
        Map<String, Object> map = jdbcTemplate.queryForMap("select * from t_admin where loginacct=?", username);


        //查询用户拥有的角色集合
        String sql1="SELECT t_role.* FROM t_role LEFT JOIN t_admin_role ON t_admin_role.roleid=t_role.id WHERE t_admin_role.adminid=?";
        List<Map<String, Object>> roleList = jdbcTemplate.query(sql1, new ColumnMapRowMapper(), map.get("id"));

//查询用户拥有的权限集合
        String sql2 = "SELECT distinct t_permission.* FROM t_permission LEFT JOIN t_role_permission ON t_role_permission.permissionid = t_permission.id LEFT JOIN t_admin_role ON t_admin_role.roleid=t_role_permission.roleid WHERE t_admin_role.adminid=?";
        List<Map<String, Object>> permissionList = jdbcTemplate.query(sql2, new ColumnMapRowMapper(), map.get("id"));

//用户权限=【角色+权限】
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

        for (Map<String, Object> rolemap : roleList) {
            String rolename = rolemap.get("name").toString();
            authorities.add(new SimpleGrantedAuthority("ROLE_"+rolename));
        }
        for (Map<String, Object> permissionmap : permissionList) {
            String permissionName = permissionmap.get("name").toString();
            if(!StringUtils.isEmpty(permissionName)) {
                authorities.add(new SimpleGrantedAuthority(permissionName));
            }
        }

        //AuthorityUtils.createAuthorityList("太极拳","七伤拳")  权限和角色 必须是数据库中的信息
        return new User(map.get("loginacct").toString(),map.get("userpswd").toString(), authorities);
    }
}
