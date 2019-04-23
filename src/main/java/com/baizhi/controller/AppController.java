package com.baizhi.controller;

import com.baizhi.eitity.Error;
import com.baizhi.eitity.User;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ArticleService;
import com.baizhi.service.BannerService;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("first")
public class AppController {

    @Autowired
    BannerService bannerService;
    @Autowired
    AlbumService albumService;
    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;

    @RequestMapping("queryAll")
    public Object queryAll(Integer uid, String type, String subtype) {
        if (uid == null || type == null) {
            return new Error("参数不能为空");
        } else {
            if (type.equals("all")) {
                Map<String, Object> map = new HashMap<>();
                map.put("banner", bannerService.selectAll());
                map.put("album", albumService.select1());
                map.put("article", articleService.selectAll());
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
                        User user = userService.selectOne(uid);
                        Integer mid = user.getMasterId();

                        map.put("article", "当前用户的上师文章");
                        return map;
                    } else {
                        Map<String, Object> map = new HashMap<>();
                        User user = userService.selectOne(uid);
                        Integer mid = user.getMasterId();
                        map.put("article", "其他上师的文章");
                        return map;
                    }
                }
            }
        }
    }

    @RequestMapping("onearticle")
    @ResponseBody
    public Object onearticle(Integer id, Integer uid) {
        if (uid == null) {
            return new Error("请先登录");
        } else if (id == null) {
            return new Error("选择文章编号");
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("article", articleService.selectOne(id));
            return map;
        }
    }

    @RequestMapping("onealbum")
    @ResponseBody
    public Object onealbum(String id, Integer uid) {
        if (uid == null) {
            return new Error("请先登录");
        } else if (id == null) {
            return new Error("选择一个专辑");
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("album", albumService.selectOne(id));
            return map;
        }

    }
}
