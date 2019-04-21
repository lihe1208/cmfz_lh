package com.baizhi.eitity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String name;
    private String dharma;
    private Integer sex;
    private String province;
    private String city;
    private String sign;
    private Integer status;
    private String phone;
    private  String password;
    private Integer salt;
    private String headImg;
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
    private  Integer masterId;


    private List<UserCount> userCounts;

}
