package com.baizhi.eitity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*@Table(name = "album")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ExcelTarget(value = "album")*/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "album")
@ExcelTarget(value = "album")
public class Album implements Serializable{
    @Id
    @Excel(name = "编号")

    private String id;
    @Excel(name = "专辑名",needMerge = true)
    private String title;
    @Excel(name = "数量")
    private Integer amount;
    @Excel(name = "图片路径",needMerge = true/*,imageType = 1,type = 2,width = 10,height = 10*/)
    private String imgPath;
    @Excel(name = "星级")
    private Integer score;
    @Excel(name = "作者",needMerge = true)
    private String author;
    @Excel(name = "播音师傅")
    private String boardcast;

    @DateTimeFormat(pattern ="yyyy:MM:dd")
    @JSONField(format ="yyyy:MM:dd")
    @Excel(name = "完成日期",format = "yyyy年MM月dd日HH时mm分ss秒",width = 50)
    private Date publishDate;
    @Excel(name = "简介",needMerge = true)
    private String brief;

    @Transient
    @ExcelCollection(name = "章节")
    private List<Chapter> children=new ArrayList<>();


}
