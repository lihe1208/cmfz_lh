package com.baizhi.service.impl;

import com.baizhi.eitity.PageHelper;
import com.baizhi.eitity.User;
import com.baizhi.mapper.UserMapper;
import com.baizhi.service.UserService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Override
    public void insert(User user) {
        userMapper.insert(user);
    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    public PageHelper queryAll(Integer page, Integer rows) {
        Integer offset=rows*(page-1);
        Integer limit=rows;
        RowBounds rowBounds=new RowBounds(offset,limit);
        List list=userMapper.selectByRowBounds(new User(),rowBounds);
        System.err.println(list);
        Integer total=userMapper.selectAll().size();
        System.out.println(total);
        PageHelper pageHelper=new PageHelper(list,total);
        return pageHelper;

    }

    @Override
    public User selectOne(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
