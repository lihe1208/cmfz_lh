package com.baizhi.eitity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "article")
public class Article {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String title;
    private String imgPath;
    private String content;
    @DateTimeFormat(pattern = "yy:MM:dd")
    @JSONField(format = "yy:MM:dd")
    private String publishDate;
    private Integer status;
    private Integer masterId;
}
