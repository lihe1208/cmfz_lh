package com.baizhi.service.impl;

import com.baizhi.eitity.Menu;
import com.baizhi.mapper.MenuMapper;
import com.baizhi.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("menuService")
public class MenuServiceImpl implements MenuService {
   @Autowired
    MenuMapper menuMapper;

    @Override
    public List<Menu> select1() {
        List<Menu> menus = menuMapper.select1();

        return menus;
    }
}
