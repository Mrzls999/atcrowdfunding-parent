package com.zls.atcrowdfunding.service;

import com.zls.atcrowdfunding.bean.TAdmin;
import com.zls.atcrowdfunding.bean.TAdminExample;
import com.zls.atcrowdfunding.mapper.TAdminMapper;
import com.zls.atcrowdfunding.utils.Const;
import com.zls.atcrowdfunding.utils.DateUtil;
import com.zls.atcrowdfunding.utils.MD5Util;
import com.zls.atcrowdfunding.utils.StringUtil;
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

    /**
     * 用户登录，获取一个用户
     * @param tAdmin
     * @return
     * @throws Exception
     */
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

    /**
     * 分页查询
     * 模糊查询（账号或邮箱或名称）
     * pageNum 当前的页码
     * pageSize 每页显示的行数
     * keyWord 模糊查询的条件
     * @return
     * select * from t_admin where loginacct like "%?%" or ? or ?;
     */
    public List<TAdmin> listAdminByPage(String keyWord){
        TAdminExample example = new TAdminExample();
        if(StringUtil.isNotEmpty(keyWord)){
            TAdminExample.Criteria criteria1 = example.createCriteria();
            criteria1.andLoginacctLike("%"+keyWord+"%");

            TAdminExample.Criteria criteria2 = example.createCriteria();
            criteria2.andEmailLike("%"+keyWord+"%");

            TAdminExample.Criteria criteria3 = example.createCriteria();
            criteria3.andUsernameLike("%"+keyWord+"%");

            example.or(criteria1);
            example.or(criteria2);
            example.or(criteria3);
        }
        return tAdminMapper.selectByExample(example);
    }

    /**
     * 新增用户
     */
    public void saveAdmin(TAdmin admin){
        admin.setUserpswd(MD5Util.digest(Const.DEFALUT_PASSWORD));
        admin.setCreatetime(DateUtil.getFormatTime());
        tAdminMapper.insertSelective(admin);
    }

    public TAdmin getAdminById(Integer id) {
        return tAdminMapper.selectByPrimaryKey(Long.valueOf(id));
    }
}
