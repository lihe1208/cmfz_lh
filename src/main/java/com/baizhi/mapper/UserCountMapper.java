package com.baizhi.mapper;

import com.baizhi.eitity.UserCount;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserCountMapper {
    List<UserCount> selectChinaMan();

    List<UserCount> selectChinaWoman();

    int selectCount(int day);
}
