package com.chapter111.mapper;

import com.chapter111.entity.TbUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TbUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbUser record);

    int insertSelective(TbUser record);

    TbUser selectByPrimaryKey(Integer id);

    List<TbUser> selectSelective(TbUser record);

    int updateByPrimaryKeySelective(TbUser record);

    int updateByPrimaryKey(TbUser record);
}