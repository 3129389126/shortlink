package com.zch.shortlink.admin.controller;

import com.zch.shortlink.admin.common.convention.result.Result;
import com.zch.shortlink.admin.common.convention.result.Results;
import com.zch.shortlink.admin.common.enums.UserErrorCodeEnum;
import com.zch.shortlink.admin.dto.resp.UserRespDTO;
import com.zch.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
        UserRespDTO result = userService.getUserByUsername(username);
        if (result==null) {
            return new Result<UserRespDTO>().setCode(UserErrorCodeEnum.USER_NULL.code()).setMessage(UserErrorCodeEnum.USER_NULL.message());
        }else{
             return Results.success(result);
        }

    }
}
