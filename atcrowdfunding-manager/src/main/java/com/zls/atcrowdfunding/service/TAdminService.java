package com.zls.atcrowdfunding.service;

import com.zls.atcrowdfunding.bean.TAdmin;
import com.zls.atcrowdfunding.bean.TAdminExample;
import com.zls.atcrowdfunding.mapper.TAdminMapper;
import com.zls.atcrowdfunding.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zls
 * @date 2022/2/16 23:02:15
 * @description XXX
 */
@Service
public class TAdminService {

    @Autowired
    private TAdminMapper tAdminMapper;

    public TAdmin getTAdmin(TAdmin tAdmin) throws Exception {
        TAdminExample tAdminExample = new TAdminExample();
        TAdminExample.Criteria criteria = tAdminExample.createCriteria();
        criteria.andLoginacctEqualTo(tAdmin.getLoginacct());
        criteria.andUserpswdEqualTo(MD5Util.digest(tAdmin.getUserpswd()));//将md5值传进去
        // ? and ?
        List<TAdmin> tAdmins = tAdminMapper.selectByExample(tAdminExample);
        if(tAdmins.size()==0){//查看源码，发现源码中的List判断是否为空的方法为判断length是否为0
            throw new Exception("用户名或密码错误");
        }
        return tAdmins.get(0);
    }

}
