package com.zch.shortlink.admin.test;

public class UserTableShardingTest {

    public static final String SQL="CREATE TABLE `t_user_%d` (\n" +
            "  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',\n" +
            "  `username` varchar(255) DEFAULT NULL COMMENT '用户名',\n" +
            "  `password` varchar(512) DEFAULT NULL COMMENT '密码',\n" +
            "  `real_name` varchar(255) DEFAULT NULL COMMENT '真实姓名',\n" +
            "  `phone` varchar(128) DEFAULT NULL COMMENT '手机号',\n" +
            "  `mail` varchar(512) DEFAULT NULL COMMENT '邮箱',\n" +
            "  `deletion_time` bigint DEFAULT NULL COMMENT '注销时间戳',\n" +
            "  `create_time` datetime DEFAULT NULL COMMENT '创建时间',\n" +
            "  `update_time` datetime DEFAULT NULL COMMENT '修改时间',\n" +
            "  `del_flag` tinyint DEFAULT NULL COMMENT '删除标识0：未删除1：已删除',\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  UNIQUE KEY `uk_username` (`username`)\n" +
            ") ENGINE = InnoDB AUTO_INCREMENT = 2096087258243727362 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;";
    public static void main(String[] args){
        for(int i=0;i<16;i++){
            System.out.printf((SQL) + "%n", i);
        }
    }



}
