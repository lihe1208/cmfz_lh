package com.baizhi.service.impl;

import com.baizhi.eitity.Album;
import com.baizhi.eitity.Chapter;
import com.baizhi.mapper.AlbumMapper;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("albumService")
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    AlbumMapper albumMapper;

    @Override
    public List<Album> select1() {
        List<Album> albums = albumMapper.select1();
        return albums;
    }

    @Override
    public Album selectOne(Integer id) {
        Album album = albumMapper.selectByPrimaryKey(id);
        return album;
    }

    @Override
    public void insert(Album album) {
        albumMapper.insert(album);

    }

    @Override
    public void delete(Album album) {
        albumMapper.delete(album);
    }

    @Override
    public void redo() {

    }

    @Override
    public List<Album> undo() {
        List<Album> albums = albumMapper.selectAll();
        return  albums;
}


}
