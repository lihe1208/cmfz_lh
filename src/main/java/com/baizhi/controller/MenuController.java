package com.baizhi.controller;

import com.baizhi.eitity.Menu;
import com.baizhi.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("menu")
public class MenuController {
    @Autowired
    MenuService menuService;
    @RequestMapping("queryAll")
    @ResponseBody
    public  List<Menu> queryAll(){
        List<Menu> menus = menuService.select1();
        System.err.println(menus);
        return  menus;

    }

}
