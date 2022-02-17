package com.zls.atcrowdfunding.service;

import com.zls.atcrowdfunding.bean.TMenu;
import com.zls.atcrowdfunding.mapper.TMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zls
 * @date 2022/2/17 21:57:13
 * @description XXX
 */
@Service
public class TMenuService {

    @Autowired
    private TMenuMapper tMenuMapper;

    public List<TMenu> listMenu(){
        return tMenuMapper.selectByExample(null);
    }

}
