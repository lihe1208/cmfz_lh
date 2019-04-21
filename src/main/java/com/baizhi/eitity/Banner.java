package com.baizhi.eitity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "banner")
public class Banner {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String title;
    @Column(name="img_path")
    private String imgPath;
    @Column(name="creat_date")
    @DateTimeFormat(pattern ="yy:MM:dd")
    @JSONField(format ="yy:MM:dd")
    private Date createDate;
    private Integer status;
}
