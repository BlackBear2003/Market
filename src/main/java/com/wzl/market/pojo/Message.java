package com.wzl.market.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Date;

import static com.baomidou.mybatisplus.annotation.IdType.AUTO;

@TableName("t_message")
@Data
@RequiredArgsConstructor
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type=AUTO)
    private int messageId;
    private String messageType;
    private String text;
    private String url;
    private Date createDate;
    private int senderId;
    private int receiverId;
    private int messageStatus;
}
