package com.baizhi.mapper;

import com.baizhi.eitity.UserCount;

import java.util.List;

public interface UserCountMapper {
    List<UserCount> selectChinaMan();

    List<UserCount> selectChinaWoman();
}
