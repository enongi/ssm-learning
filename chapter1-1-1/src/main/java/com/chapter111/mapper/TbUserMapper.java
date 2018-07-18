package com.chapter111.mapper;

import com.chapter111.entity.TbUser;
import org.springframework.stereotype.Repository;

//@Repository标签，表明它是数据访问组件
@Repository
public interface TbUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbUser record);

    int insertSelective(TbUser record);

    TbUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbUser record);

    int updateByPrimaryKey(TbUser record);
}