package com.zch.shortlink.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.zch.shortlink.admin.common.convention.result.Result;
import com.zch.shortlink.admin.common.convention.result.Results;
import com.zch.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.zch.shortlink.admin.dto.req.UserUpdateReqDTO;
import com.zch.shortlink.admin.dto.resp.UserActualRespDTO;
import com.zch.shortlink.admin.dto.resp.UserRespDTO;
import com.zch.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/*
* 用户管理控制层
* */
//@RestController Spring MVC 把 HTTP 请求路由到这个类
@RestController
@RequiredArgsConstructor//编译期自动生成一个包含所有 final 字段的构造方法
public class UserController {

    private final UserService userService;

    /*
    * 根据用户名查询用户信息
    * */
    // @GetMapping 定路由，告诉 Spring凡是收到 GET 请求、且 URL 长这样的。{username} —— 占位符
    @GetMapping("/api/short-link/v1/user/{username}")
    //@PathVariable("username")——把 URL 占位符里的值赋给方法参数 username
    public Result<UserRespDTO> getUserByUsername(@PathVariable("username") String username){
        return Results.success(userService.getUserByUsername(username));
    }

    //BeanUtil.toBean(源对象, 目标类.class)——Hutool 的对象复印机
    //new 一个 UserActualRespDTO，反射遍历源对象的同名字段，逐个复制过去
    //我的用户信息不需要返回脱敏的手机号码
    @GetMapping("/api/short-link/v1/actual/user/{username}")
    //@PathVariable("username")——把 URL 占位符里的值赋给方法参数 username
    public Result<UserActualRespDTO> getActualUserByUsername(@PathVariable("username") String username){
        return Results.success(BeanUtil.toBean(userService.getUserByUsername(username),UserActualRespDTO.class));
    }

    /**
     *查询用户名是否存在
     */
    @GetMapping("/api/short-link/v1/user/has-username")
    public Result<Boolean> hasUsername(@RequestParam("username") String username){
        return Results.success(userService.hasUsername(username));
    }


    /**
     *注册用户
     */
    //@RequestBody 告诉 Spring——这个参数的值不在 URL 上，而是藏在 HTTP 请求体（Body）里，
    // 请把 Body 里的 JSON 按字段名装进这个 Java 对象。
    //把字节流里的 JSON 变成 UserRegisterReqDTO 对象。
    @PostMapping("/api/short-link/v1/user")
    public Result<Void> register(@RequestBody UserRegisterReqDTO requestParam){
        userService.register(requestParam);
        return Results.success();
    }


    /**
     *修改用户
     */
    @PutMapping("/api/short-link/v1/user")
    //@RequestBody：这个参数的值不在 URL 上，而是藏在 HTTP 请求体（Body）里——请把 Body 里的 JSON 按字段名装进这个 Java 对象
    public Result<Void> update(@RequestBody UserUpdateReqDTO requestParam){
        userService.update(requestParam);
        return Results.success();
    }

}
