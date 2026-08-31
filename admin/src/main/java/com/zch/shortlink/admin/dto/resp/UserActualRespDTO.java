package com.zch.shortlink.admin.dto.resp;

import lombok.Data;

/*
 * 用户返回参数响应
 * */
//不需要我返回脱敏的用户信息，比方说手机号码，需要信息从我的后端传入到我的前端
@Data
public class UserActualRespDTO {
    /**
     * ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String mail;
}
