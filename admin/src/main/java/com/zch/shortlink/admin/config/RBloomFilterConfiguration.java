package com.zch.shortlink.admin.config;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 布隆过滤器配置
 */
//把配置类自己变成 Bean
@Configuration
public class RBloomFilterConfiguration {

    /**
     * 防止用户注册查询数据库的布隆过滤器
     */
    //调用这个方法，把方法的返回值也变成 Bean
    @Bean
    //RBloomFilter 是 Redisson 提供的第三方类，没法跑到人家源码里加 @Service，所以只能在配置类里用 @Bean 方法手动注册。
    //RedissonClient redissonClient 方法参数的注入，这个在我的依赖注入里面写好了，不是自己添加到
    public RBloomFilter<String> userRegisterCachePenetrationBloomFilter(RedissonClient redissonClient) {
        //按名字从 Redis 拿布隆过滤器。这个名字就是 Redis 里的 key——同名即同一个过滤器，跨服务也能共享。
        RBloomFilter<String> cachePenetrationBloomFilter = redissonClient.getBloomFilter("userRegisterCachePenetrationBloomFilter");
        //tryInit只有第一次调用会真正初始化
        cachePenetrationBloomFilter.tryInit(100000000L, 0.001);
        return cachePenetrationBloomFilter;
    }
}