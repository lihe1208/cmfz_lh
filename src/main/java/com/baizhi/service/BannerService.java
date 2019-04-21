package com.baizhi.service;

import com.baizhi.eitity.Banner;
import com.baizhi.eitity.PageHelper;

import java.util.List;

public interface BannerService {
    public void insert(Banner banner);
    public void update(Banner banner);
    public PageHelper queryAll(Integer page, Integer rows);
    public Banner selectOne(Integer id);
    public void delete(Integer id);
    public List<Banner> selectAll();
    public void redo();//导入
    public List<Banner> undo();//导出
}
