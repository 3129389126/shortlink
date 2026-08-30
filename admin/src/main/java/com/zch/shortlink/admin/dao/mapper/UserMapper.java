package com.zch.shortlink.admin.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zch.shortlink.admin.dao.entity.UserDO;

/*
* 用户持久层
* */
//BaseMapper 里预声明了十几个方法  靠接口继承 + 泛型 + 框架运行时动态代理，零 SQL 白嫖整表 CRUD
public interface UserMapper extends BaseMapper<UserDO> {
}
