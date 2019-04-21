package com.baizhi.eitity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageHelper {

    private List<Banner> banners;

    private Integer total;

}
