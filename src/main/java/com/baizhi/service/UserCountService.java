package com.baizhi.service;

import com.baizhi.eitity.UserCount;

import java.util.List;

public interface UserCountService {
    List<UserCount> selectChinaMan();

    List<UserCount> selectChinaWoman();
}
