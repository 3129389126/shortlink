package com.zch.shortlink.admin.controller;

import com.zch.shortlink.admin.commom.convention.result.Result;
import com.zch.shortlink.admin.commom.enums.UserErrorCodeEnum;
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
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /*
    * 根据用户名查询用户信息
    * */
    @GetMapping("/api/shortlink/v1/user/{username}")
    public Result<UserRespDTO> getUserByUsername(@PathVariable("username") String username){
        UserRespDTO result = userService.getUserByUsername(username);
        if (result==null) {
            return new Result<UserRespDTO>().setCode(UserErrorCodeEnum.USER_NULL.code()).setMessage(UserErrorCodeEnum.USER_NULL.message());
        }else{
            return new Result<UserRespDTO>().setCode("0").setData(result);
        }

    }
}
