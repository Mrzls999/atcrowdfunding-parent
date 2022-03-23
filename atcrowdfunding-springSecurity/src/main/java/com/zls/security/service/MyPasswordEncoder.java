package com.zls.security.service;

import com.zls.security.utils.MD5Util;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author zls
 * @date 2022/3/23 16:34:54
 * @description 此处使用的是MD5加密
 */
@Service
public class MyPasswordEncoder implements PasswordEncoder {

    /**
     * 使用md5加密
     * @param rawPassword 原密码（明文）
     * @return
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return MD5Util.digest(rawPassword.toString());
    }

    /**
     *
     * @param rawPassword   明文
     * @param encodedPassword 密文
     * @return
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return MD5Util.digest(rawPassword.toString()).equals(encodedPassword);
    }
}
