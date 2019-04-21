package com.baizhi.service;

import com.baizhi.eitity.Album;
import com.baizhi.eitity.Chapter;


import java.util.List;

public interface AlbumService {
    List<Album> select1();
    Album selectOne(Integer id);
    void insert(Album album);
    void delete(Album album);
    public void redo();//导入
    public List<Album> undo();//导出
}
