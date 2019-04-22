package com.baizhi.controller;

import com.baizhi.eitity.PageHelper;
import com.baizhi.eitity.User;
import com.baizhi.service.UserService;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("queryAll")
    @ResponseBody
    public Map<List,Integer> queryAll(Integer page, Integer rows){
        Map map=new HashMap();
        PageHelper pageHelper = userService.queryAll(page,rows);
        map.put("rows",pageHelper.getBanners());
        map.put("total",pageHelper.getTotal());
        System.out.println(map);
        return  map;

    }


    @RequestMapping("insertUser")
    @ResponseBody
    public Map insert(User user, MultipartFile file) throws IOException {
        System.out.println(user);
        String oldName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String newName = uuid+oldName.substring(oldName.lastIndexOf("."));
        String filePath="D://lun//";
        file.transferTo(new File(filePath+newName));
        System.out.println(newName);
        user.setHeadImg(newName);
        Map map=new HashMap();
        user.setId(0);
        Date date=new Date();
        user.setCreateDate(date);
        user.setMasterId(1);
        /*user.setPassword("123456");*/
        System.out.println(user);
        try {
            userService.insert(user);
            map.put("isInsert",true);
            test3();
        }catch(Exception e){
            e.printStackTrace();
            map.put("isInsert",false);
        }

        return  map;

    }

    public void test3() {
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-c07f58e282ac45f493d42641b6a5259e");
        goEasy.publish("lh", "新的动态!");
    }

}
