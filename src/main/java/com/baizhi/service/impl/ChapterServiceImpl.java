package com.baizhi.service.impl;


import com.baizhi.eitity.Album;
import com.baizhi.eitity.Chapter;
import com.baizhi.mapper.ChapterMapper;
import com.baizhi.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("chapterService")
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    ChapterMapper chapterMapper;
    @Override
    public Chapter selectOne(Integer id) {
        Chapter chapter = chapterMapper.selectByPrimaryKey(id);
        return chapter;
    }

    @Override
    public void insert(Chapter chapter) {

        chapterMapper.insert(chapter);
    }

    @Override
    public void delete(Chapter chapter) {
        chapterMapper.delete(chapter);
    }

    @Override
    public List<Chapter> select() {

        List<Chapter> chapters = chapterMapper.selectAll();
        return chapters;
    }
}
