package com.baizhi.controller;

import com.baizhi.eitity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @ResponseBody
    public Map login(String name, String password) {

        Admin admin = adminService.login(name, password);
        Map map = new HashMap<>();
        if (admin != null) {
            map.put("islogin", true);
        } else {
            map.put("islogin", false);

        }
        return map;
    }
}
