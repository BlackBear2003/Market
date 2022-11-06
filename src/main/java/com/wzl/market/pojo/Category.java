package com.wzl.market.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
@Data
@TableName("t_good_category")
public class Category implements Serializable {
    private static final long serialVersionUID = 5992234469418852717L;
    @TableId
    private Integer categoryId;
    private String categoryName;
    private String level;
    private Integer parentId;
}
