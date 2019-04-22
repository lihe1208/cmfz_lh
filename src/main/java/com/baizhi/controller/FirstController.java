package com.baizhi.controller;

import com.baizhi.service.AlbumService;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("first")
public class FirstController {

    @Autowired
    BannerService bannerService;
    @Autowired
    AlbumService albumService;

    @RequestMapping("queryAll")
    public Object queryAll(Integer uid, String type, String subtype) {
        if (uid == null || type == null) {
            return new Error("参数不能为空");
        } else {
            if (type.equals("all")) {
                Map<String, Object> map = new HashMap<>();
                map.put("banner", bannerService.selectAll());
                map.put("album", albumService.select1());
                map.put("article", null);
                return map;
            } else if (type.equals("wen")) {
                Map<String, Object> map = new HashMap<>();
                map.put("album", albumService.select1());
                return map;
            } else {
                if (subtype == null) {
                    return new Error("思类型为空");
                } else {
                    if (subtype.equals("ssyj")) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("article", "当前用户的上师文章");
                        return map;
                    } else {
                        Map<String, Object> map = new HashMap<>();
                        map.put("article", "其他上师的文章");
                        return map;
                    }
                }
            }
        }
    }
}
