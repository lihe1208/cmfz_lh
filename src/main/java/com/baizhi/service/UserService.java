package com.baizhi.service;

import com.baizhi.eitity.PageHelper;
import com.baizhi.eitity.User;

import java.util.List;

public interface UserService {
    public void insert(User user);
    public void update(User user);
    public PageHelper queryAll(Integer page, Integer rows);
    /*public User selectOne(Integer id);*/
    /*public void delete(Integer id);
    public List<User> selectAll();
    public void redo();//导入
    public List<User> undo();//导出*/
}
