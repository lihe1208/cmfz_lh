package com.baizhi.service;

import com.baizhi.eitity.UserCount;

import java.util.List;
import java.util.Map;

public interface UserCountService {
    List<UserCount> selectChinaMan();

    List<UserCount> selectChinaWoman();

    Map selectCount();
}
