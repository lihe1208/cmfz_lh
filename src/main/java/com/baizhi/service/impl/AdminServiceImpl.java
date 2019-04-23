package com.baizhi.service.impl;

import com.baizhi.eitity.Admin;
import com.baizhi.mapper.AdminMapper;
import com.baizhi.mapper.AlbumMapper;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;

    @Override
    public Admin login(String name, String password) {
        Admin admin = adminMapper.login(name, password);
        return admin;
    }
}
