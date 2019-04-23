package com.baizhi.mapper;

import com.baizhi.eitity.Admin;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface AdminMapper extends Mapper<Admin> {
    public Admin login(@Param("name") String name, @Param("password") String password);
}
