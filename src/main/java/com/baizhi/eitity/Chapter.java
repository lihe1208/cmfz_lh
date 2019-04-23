package com.baizhi.eitity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "chapter")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelTarget(value = "chapter")
public class Chapter implements Serializable{
    @Id
    @KeySql(useGeneratedKeys = true)
    @Excel(name = "章节编号")
    private Integer id;
    @Excel(name = "章节标题")
    private String title;
    @Excel(name = "章节大小")
    private String size;
    @Excel(name = "章节时长")
    private String duration;//持续时间
    @DateTimeFormat(pattern ="yy:MM:dd")
    @JSONField(format ="yy:MM:dd")
    @Excel(name = "完成日期",format = "yyyy年MM月dd日HH时mm分ss秒",width = 50)
    private Date publishDate;
    @Excel(name = "专辑编号")
    private String albumId;
    @Excel(name = "下载路径")
    @Column(name="download_path")
    private String downLoadPath;


}
