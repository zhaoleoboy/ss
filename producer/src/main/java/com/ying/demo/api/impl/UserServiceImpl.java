package com.ying.demo.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ying.demo.api.UserService;
import org.springframework.beans.factory.annotation.Value;

@Service(version = "${user.service.version}")
public class UserServiceImpl implements UserService {

    @Value("${dubbo.protocol.port}")
    private String port;

    @Override
    public String sayHi() {
        System.out.println("hello,Dubbo!");
        return "Say Hello, i am from " + port;
    }
}
