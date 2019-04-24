package com.baizhi.mapper;

import com.baizhi.eitity.Article;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ArticleMapper extends Mapper<Article> {
    List selectMyMaster(Integer id);

    List selectOtherMaster(Integer id);
}
