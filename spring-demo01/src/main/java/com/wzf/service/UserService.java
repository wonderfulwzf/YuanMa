package com.wzf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
public class UserService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private User admin;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void test(){
        System.out.println("正在调用test方法");
    }

    @PostConstruct
    public void a(){
        System.out.println("进行初始化前赋值");
    }

    @Transactional
    public void jdbcMethod(){
        System.out.println("进行数据库操作");
        jdbcTemplate.execute("insert into demo1 values (1,'ww')");
        jdbcMethod01();
    }

    @Transactional(propagation = Propagation.NEVER)
    public void jdbcMethod01(){
        System.out.println("进行数据库操作");
        jdbcTemplate.execute("insert into demo1 values (2,'ww')");
        //throw new NullPointerException();
    }

}
