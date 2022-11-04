package com.wzl.market.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Date;

import static com.baomidou.mybatisplus.annotation.IdType.AUTO;

@Data
@RequiredArgsConstructor
@TableName("t_good_comment")
public class GoodComment implements Serializable {

    private static final long serialVersionUID = -2687743024921980062L;
    @TableId(type = AUTO)
    @NonNull
    private int commentId;
    @NonNull
    private int goodId;
    @NonNull
    private int orderId;
    @NonNull
    private int userId;
    @NonNull
    private String title;
    @NonNull
    private String content;
    @NonNull
    private int auditStatus;
    @NonNull
    private Date commentTime;
}
