package com.baizhi.service;

import com.baizhi.eitity.Album;
import com.baizhi.eitity.Chapter;

import java.util.List;

public interface ChapterService {
    Chapter selectOne(Integer id);
    void insert(Chapter chapter);
    void delete(Chapter chapter);
    List<Chapter> select();
}
