package com.zls.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zls
 * @date 2022/3/23 15:37:26
 * @description XXX
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     * 单行数据 queryForObject
     * 多行   query()
     * 增删改  update | updateBath
     */
    /**
     * 登录验证方法
     * @param userName  loginacct
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        String sql = "select * from t_admin where loginacct=?";
        Map<String, Object> map = jdbcTemplate.queryForMap(sql, userName);
        //return new User(map.get("loginacct").toString(),map.get("userpswd").toString(), AuthorityUtils.createAuthorityList("太极拳","七伤拳","ROLE_学徒"));
        return new User(map.get("loginacct").toString(),map.get("userpswd").toString(), AuthorityUtils.createAuthorityList("luohan","wudang","ROLE_学徒"));
    }
}
