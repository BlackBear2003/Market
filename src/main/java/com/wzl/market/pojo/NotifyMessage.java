package com.wzl.market.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import static com.baomidou.mybatisplus.annotation.IdType.AUTO;

@Data
@TableName("t_notify_message")
public class NotifyMessage implements Serializable {

    private static final long serialVersionUID = 4324451814173869437L;
    @TableId(type=AUTO)
    private int notifyMessageId;
    private String text;
    private String url;
    private Date createDate;
    private int receiverId;
    private int notifyMessageStatus;
}
