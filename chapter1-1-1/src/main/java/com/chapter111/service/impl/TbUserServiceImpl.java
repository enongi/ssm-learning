package com.chapter111.service.impl;

import com.chapter111.entity.TbUser;
import com.chapter111.mapper.TbUserMapper;
import com.chapter111.service.TbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TbUserServiceImpl implements TbUserService {
    @Autowired
    private TbUserMapper tbUserMapper;
    public int insert(TbUser tbUser) {
        return tbUserMapper.insert(tbUser);
    }

    public List<TbUser> selectSelective(TbUser tbUser) {
        return tbUserMapper.selectSelective(tbUser);
    }
}
