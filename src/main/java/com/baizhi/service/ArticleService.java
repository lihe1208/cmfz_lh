package com.baizhi.service;

import com.baizhi.eitity.Article;
import com.baizhi.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ArticleService {
    public List selectAll();

    public Article selectOne(Integer id);

    public List selectMyMaster(Integer id);

    public List selectOtherMaster(Integer id);

}
