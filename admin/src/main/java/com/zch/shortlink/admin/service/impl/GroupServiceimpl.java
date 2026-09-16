package com.zch.shortlink.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zch.shortlink.admin.dao.entity.GroupDO;
import com.zch.shortlink.admin.dao.mapper.GroupMapper;
import com.zch.shortlink.admin.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * 短链接分组接口实现层
 */
@Slf4j //它是 Lombok 的注解，编译时自动帮你生成一个名叫 log 的日志对象，省掉手写初始化那行代码
@Service
public class GroupServiceimpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {
}
