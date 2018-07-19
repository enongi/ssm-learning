package com.chapter111.mapper;

import com.chapter111.entity.TbUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

public class TbUserMapperTest {
    private ApplicationContext applicationContext;
    @Autowired
    private TbUserMapper tbUserMapper;
    @Before
    public void setUp() throws Exception {
        // 加载spring配置文件
        applicationContext = new ClassPathXmlApplicationContext("classpath:config/applicationContext.xml");
        // 导入需要测试的
        tbUserMapper = applicationContext.getBean(TbUserMapper.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void insert() {
        TbUser tbUser = new TbUser();
        tbUser.setUsername("lisi");
        tbUser.setNickname("李四");
        tbUser.setPassword("123456");
        int result = tbUserMapper.insert(tbUser);
        System.out.println(result);
        assert (result == 1);
    }
}