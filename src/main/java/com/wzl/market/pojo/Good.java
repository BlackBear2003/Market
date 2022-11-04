package com.wzl.market.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Date;

import static com.baomidou.mybatisplus.annotation.IdType.AUTO;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@TableName("t_good")
public class Good implements Serializable {

    private static final long serialVersionUID = -5474070412887279676L;
    @TableId(type=AUTO)
    private int good_id;
    @NonNull
    private String goodName;
    @NonNull
    private int categoryId;
    @NonNull
    private double price;
    @NonNull
    private String introduction;
    @NonNull
    private int publishStatus;
    @NonNull
    private int auditStatus;
    @NonNull
    private Date publishDate;
}
