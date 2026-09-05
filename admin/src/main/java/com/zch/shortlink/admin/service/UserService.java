package com.zch.shortlink.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zch.shortlink.admin.dao.entity.UserDO;
import com.zch.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.zch.shortlink.admin.dto.req.UserUpdateReqDTO;
import com.zch.shortlink.admin.dto.resp.UserRespDTO;

/*
 * 用户接口层
 * */
//  IService  业务级封装：saveBatch 批量保存、getOne 取单条、lambdaQuery 链式查询、内置事务的批量操作
//  类似于BaseMapper，白嫖继承接口
public interface UserService extends IService<UserDO> {

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名  //说明参数的意义
     * @return 用户返回实体     //说明返回值是什么
     */
    UserRespDTO getUserByUsername(String username);


    /**
     * 查询用户名是否存在
     * @param username 用户名
     * @return  用户名存在返回 true ，不存在 返回 false
     */
    Boolean hasUsername(String username);


    /***
     * 注册用户
     * @param requestParam  注册用户返回参数
     */
    //requestParam 它是 Controller 里 @RequestBody 触发 Jackson 把前端 JSON 反序列化出来的对象
    void register(UserRegisterReqDTO requestParam);

    /***
     * 根据用户名修改用户
     * @param requestParam  修改用户请求参数
     */
    void update(UserUpdateReqDTO requestParam);

}
