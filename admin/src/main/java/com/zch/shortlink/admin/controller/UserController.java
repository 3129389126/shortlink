package com.zch.shortlink.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.zch.shortlink.admin.common.convention.result.Result;
import com.zch.shortlink.admin.common.convention.result.Results;
import com.zch.shortlink.admin.dto.resp.UserActualRespDTO;
import com.zch.shortlink.admin.dto.resp.UserRespDTO;
import com.zch.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController

/*
* 用户管理控制层
* */
@RestController
@RequiredArgsConstructor//编译期自动生成一个包含所有 final 字段的构造方法
public class UserController {

    private final UserService userService;

    /*
    * 根据用户名查询用户信息
    * */
    // @GetMapping 定路由，告诉 Spring凡是收到 GET 请求、且 URL 长这样的。{username} —— 占位符
    @GetMapping("/api/shortlink/v1/user/{username}")
    //@PathVariable("username")——把 URL 占位符里的值赋给方法参数 username
    public Result<UserRespDTO> getUserByUsername(@PathVariable("username") String username){
        return Results.success(userService.getUserByUsername(username));
    }

    //BeanUtil.toBean(源对象, 目标类.class)——Hutool 的对象复印机
    //new 一个 UserActualRespDTO，反射遍历源对象的同名字段，逐个复制过去
    //我的用户信息不需要返回脱敏的手机号码
    @GetMapping("/api/shortlink/v1/actual/user/{username}")
    //@PathVariable("username")——把 URL 占位符里的值赋给方法参数 username
    public Result<UserActualRespDTO> getActualUserByUsername(@PathVariable("username") String username){
        return Results.success(BeanUtil.toBean(userService.getUserByUsername(username),UserActualRespDTO.class));
    }

    /**
     *查询用户名是否存在
     */
    @GetMapping("/api/shortlink/v1/user/has-username")
    public Result<Boolean> hasUsername(@RequestParam("username") String username){
        return Results.success(userService.hasUsername(username));
    }

}
