package com.baizhi.mapper;

import com.baizhi.eitity.Album;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AlbumMapper extends Mapper<Album> {
    List<Album> select1();
}
