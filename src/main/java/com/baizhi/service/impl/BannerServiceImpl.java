package com.baizhi.service.impl;

import com.baizhi.eitity.Banner;
import com.baizhi.eitity.PageHelper;
import com.baizhi.mapper.BannerMapper;
import com.baizhi.service.BannerService;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
@Service("bannerService")
public class BannerServiceImpl implements BannerService {

   @Autowired
    BannerMapper bannerMapper;

    @Override
    public void insert(Banner banner) {
        bannerMapper.insert(banner);
    }



    @Override
    public void update(Banner banner) {
        bannerMapper.updateByPrimaryKey(banner);
    }


    @Override
    public PageHelper queryAll(Integer page,Integer rows) {

        Integer offset=rows*(page-1);
        Integer limit=rows;
        RowBounds rowBounds=new RowBounds(offset,limit);
        List list=bannerMapper.selectByRowBounds(new Banner(),rowBounds);
        System.err.println(list);
        Integer total=bannerMapper.selectAll().size();
        System.out.println(total);
        PageHelper pageHelper=new PageHelper(list,total);
        return pageHelper;
    }

    @Override
    public Banner selectOne(Integer id) {

        Banner banner=bannerMapper.selectByPrimaryKey(id);
        return banner;
    }

    @Override
    public void delete(Integer id) {
        bannerMapper.deleteByPrimaryKey(id);

    }

    @Override
    public List<Banner> selectAll() {
        return bannerMapper.selectAll();
    }

    @Override
    public void redo() {

    }

    @Override
    public List<Banner> undo() {
        List<Banner> banners = bannerMapper.selectAll();
        return  banners;
    }
}
