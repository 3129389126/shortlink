package com.zch.shortlink.admin.dto.resp;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登陆接口返回响应
 */

@Data
@NoArgsConstructor //无参构造
@AllArgsConstructor  //全参构造
public class UserLoginRespDTO {

    /**
     * 用户token
     */
    private String token;
}
