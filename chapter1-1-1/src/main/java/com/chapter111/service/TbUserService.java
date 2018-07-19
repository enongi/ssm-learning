package com.chapter111.service;

import com.chapter111.entity.TbUser;

import java.util.List;

public interface TbUserService {
    int insert(TbUser tbUser);
    List<TbUser> selectSelective(TbUser tbUser);
}
