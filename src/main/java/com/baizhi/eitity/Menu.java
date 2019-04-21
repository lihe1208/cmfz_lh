package com.baizhi.eitity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "menu")
public class Menu implements Serializable {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String title;
    @Column(name="icon_cls")
    private  String iconCls;
    @Column(name="parent_id")
    private Integer parentId;
    @Column(name="jsp_url")
    private String jspUrl;
    @Transient
    private List<Menu> children=new ArrayList<>();


}
