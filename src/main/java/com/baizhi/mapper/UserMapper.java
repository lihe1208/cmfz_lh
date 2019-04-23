package com.baizhi.mapper;

import com.baizhi.eitity.User;
import com.baizhi.eitity.UserCount;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {
}
