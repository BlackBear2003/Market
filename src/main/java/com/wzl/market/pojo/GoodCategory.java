package com.wzl.market.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_good_category")
public class GoodCategory implements Serializable {

    private static final long serialVersionUID = -257554461365396173L;
    @TableId
    private int category_id;
    private String categoryName;
    private String categoryLevel;
    private String parentId;

}
