package com.baizhi.service.impl;

import com.baizhi.eitity.Article;
import com.baizhi.mapper.ArticleMapper;
import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Override
    public List selectAll() {
        return articleMapper.selectAll();
    }

    @Override
    public Article selectOne(Integer id) {
        return articleMapper.selectByPrimaryKey(id);
    }

    @Override
    public List selectMyMaster(Integer id) {
        return articleMapper.selectMyMaster(id);
    }

    @Override
    public List selectOtherMaster(Integer id) {
        return articleMapper.selectOtherMaster(id);
    }
}
