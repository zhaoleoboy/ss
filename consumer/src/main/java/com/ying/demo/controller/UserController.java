package com.ying.demo.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.ying.demo.api.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Reference(version = "${user.service.version}")
    private UserService userService;

    @GetMapping("/sayHi")
    public String sayHi() {
        return userService.sayHi();
    }

    public String sayHiError() {
        return "Hystrix Fallback.";
    }

}
