package com.zch.shortlink.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //Spring Boot的启动类  负责启动时自动扫描、装配所有组件
//扫描这个包下的所有接口，每个都生成动态代理实现，注册进容器
@MapperScan("com.zch.shortlink.admin.dao.mapper")  //指定扫描 mapper 包，让 UserMapper 能被生成和使用。
public class ShortLinkAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortLinkAdminApplication.class, args);
    }
}
