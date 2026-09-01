package com.zch.shortlink.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zch.shortlink.admin.common.convention.exception.ClientException;
import com.zch.shortlink.admin.common.enums.UserErrorCodeEnum;
import com.zch.shortlink.admin.dao.entity.UserDO;
import com.zch.shortlink.admin.dao.mapper.UserMapper;
import com.zch.shortlink.admin.dto.resp.UserRespDTO;
import com.zch.shortlink.admin.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/*
 * 用户接口实现层
 * */
//  ServiceImpl 是 MyBatis Plus 提供的通用 Service 实现基类。
// 它内部已经帮你写好了全套 CRUD——save()、getById()、updateById()、removeById()、批量操作、分页查询……
// 泛型中，第一个数据填UserMapper 让MP知道通过哪个 Mapper 操作数据库   第二个数据让MP知道操作的实体是哪个类
@Service
public class UserServiceimpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    // LambdaQueryWrapper<UserDO>  专门给 UserDO 用的条件构造器
    //Wrappers 是 MyBatis-Plus 的工具类，lambdaQuery(...) 是它的静态方法：“给我造一个针对 UserDO 这张表的条件构造器”。
    //UserDO.class 不是字段也不是调用，而是**“UserDO 这个类本身”**
    //Wrappers.lambdaQuery(UserDO.class)
    //此刻数据库还没被碰。这行只是在内存里造了一个空盒子——一个专门服务于 UserDO（也就是 user 表）的条件拼装器。注意它此刻还不代表任何 SQL，什么都没发生。
    //往拼装器里登记一条规则：“username 这一列，要等于 zch"
    //UserDO::getUsername 负责告诉它操作哪一列，username负责给出匹配值。
    @Override
    public UserRespDTO getUserByUsername(String username) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, username);
        UserDO userDO =baseMapper.selectOne(queryWrapper);

        if(userDO == null) {
           throw new ClientException(UserErrorCodeEnum.USER_NULL);
        }

        UserRespDTO result = new UserRespDTO();
        //BeanUtils.copyProperties(源, 目标)：Spring 工具，反射扫描两个类的同名字段
        BeanUtils.copyProperties(userDO,result);//此方法用于判空，不写的话，在我的Result那里会报错
        return result;
    }

    @Override
    public Boolean hasUsername(String username) {

        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, username);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        return userDO != null ;
    }

}
