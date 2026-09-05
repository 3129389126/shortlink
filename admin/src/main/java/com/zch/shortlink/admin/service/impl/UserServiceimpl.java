package com.zch.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zch.shortlink.admin.common.convention.exception.ClientException;
import com.zch.shortlink.admin.common.enums.UserErrorCodeEnum;
import com.zch.shortlink.admin.dao.entity.UserDO;
import com.zch.shortlink.admin.dao.mapper.UserMapper;
import com.zch.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.zch.shortlink.admin.dto.resp.UserRespDTO;
import com.zch.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import static com.zch.shortlink.admin.common.constant.RedisCacheConstant.LOG_USER_REGISTER_KEY;
import static com.zch.shortlink.admin.common.enums.UserErrorCodeEnum.USER_NAME_EXIST;
import static com.zch.shortlink.admin.common.enums.UserErrorCodeEnum.USER_SAVE_ERROR;

/*
 * 用户接口实现层
 * */
//  ServiceImpl 是 MyBatis Plus 提供的通用 Service 实现基类。
// 它内部已经帮你写好了全套 CRUD——save()、getById()、updateById()、removeById()、批量操作、分页查询……
// 泛型中，第一个数据填UserMapper 让MP知道通过哪个 Mapper 操作数据库   第二个数据让MP知道操作的实体是哪个类
//@Service  把 UserServiceImpl 注册成 Bean，交给 IoC 容器
@Service
@RequiredArgsConstructor
public class UserServiceimpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    private  final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;
    private  final RedissonClient redissonClient;

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

        return !userRegisterCachePenetrationBloomFilter.contains(username);
    }

    //分布式锁的形式，去防止恶意请求毫秒级触发大量请求一个未注册的用户名
    @Override
    public void register(UserRegisterReqDTO requestParam) {
        if(!hasUsername(requestParam.getUsername())){
            throw new ClientException(USER_NAME_EXIST);
        }
        RLock lock = redissonClient.getLock(LOG_USER_REGISTER_KEY + requestParam.getUsername());

        try{
            if(lock.tryLock())
            {
                // BeanUtil.toBean(requestParam, UserDO.class) 反射 new 一个空 UserDO → 按同名字段逐个拷贝 → 返回装好数据的 DO
                int inserted = baseMapper.insert(BeanUtil.toBean(requestParam, UserDO.class));
                if(inserted<1){
                    throw new ClientException(USER_SAVE_ERROR);
                }
                //把刚注册成功的用户名，登记进布隆过滤器的“占用名单”里
                userRegisterCachePenetrationBloomFilter.add(requestParam.getUsername());
                return;
            }
            throw new ClientException(USER_NAME_EXIST);
        } finally{
            lock.unlock();
        }
    }
}
