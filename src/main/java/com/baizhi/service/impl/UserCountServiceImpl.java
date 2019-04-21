package com.baizhi.service.impl;

import com.baizhi.mapper.UserCountMapper;
import com.baizhi.service.UserCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userCountService")
public class UserCountServiceImpl implements UserCountService {
    @Autowired
    UserCountMapper userCountMapper;

}
