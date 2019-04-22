package com.baizhi.service.impl;

import com.baizhi.eitity.UserCount;
import com.baizhi.mapper.UserCountMapper;
import com.baizhi.service.UserCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userCountService")
public class UserCountServiceImpl implements UserCountService {

    @Autowired
    UserCountMapper userCountMapper;


    @Override
    public List<UserCount> selectChinaMan() {

        List<UserCount> chinaMan = userCountMapper.selectChinaMan();
        return chinaMan;
    }

    @Override
    public List<UserCount> selectChinaWoman() {
        List<UserCount> chinaWoMan = userCountMapper.selectChinaWoman();
        return chinaWoMan;
    }

    @Override
    public Map selectCount() {
        Map map = new HashMap<>();
        String xAxisData[] = {"近一周", "近两周", "近三周", "近四周", "近五周", "近六周", "近七周"};
        List seriesData = new ArrayList();
        map.put("xAxisData", xAxisData);
        for (int i = 0; i <= 6; i++) {
            int count = userCountMapper.selectCount(i + 1);
            seriesData.add(count);
        }

        map.put("seriesData", seriesData);
        return map;
    }
}
