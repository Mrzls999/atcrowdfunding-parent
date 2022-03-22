package com.zls.security.service;

import com.zls.atcrowdfunding.utils.MD5Util;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyPasswordEncoder implements PasswordEncoder {
    /**
     *
     * 使用MD5加密
     *
     * @param rawPassword  原密码(明文)
     * @return
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return MD5Util.digest(rawPassword.toString());
    }

    /**
     *  再次确认密码
     * @param rawPassword  明文
     * @param encodedPassword 密文
     * @return
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return MD5Util.digest(rawPassword.toString()).equals(encodedPassword);
    }
}
