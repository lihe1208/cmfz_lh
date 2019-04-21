package com.baizhi.mapper;

import com.baizhi.eitity.Menu;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MenuMapper extends Mapper<Menu>{
    List<Menu> select1();
}
