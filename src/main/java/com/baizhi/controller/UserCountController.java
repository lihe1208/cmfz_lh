package com.baizhi.controller;

import com.baizhi.eitity.UserCount;
import com.baizhi.service.UserCountService;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("userCount")
public class UserCountController {
    @Autowired
    private UserCountService userCountService;

    @RequestMapping("chinaman")
    @ResponseBody
    public Map selectMan() {
        Map map = new HashMap<>();
        List<UserCount> chinaMan = userCountService.selectChinaMan();
        map.put("data", chinaMan);
        return map;
    }

    @RequestMapping("chinawoman")
    @ResponseBody
    public Map selectWoman() {
        Map map = new HashMap<>();
        List<UserCount> chinaWoMan = userCountService.selectChinaWoman();
        map.put("data", chinaWoMan);
        return map;
    }

    @RequestMapping("activeCount")
    @ResponseBody
    public Map selectCount() {

        Map map = userCountService.selectCount();

        return map;
    }


}
