package com.wzl.market.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Date;

import static com.baomidou.mybatisplus.annotation.IdType.AUTO;

@TableName("t_chat_message")
@Data
@RequiredArgsConstructor
public class ChatMessage implements Serializable {

    private static final long serialVersionUID = 7023518932970885705L;
    @TableId(type=AUTO)
    private int chatMessageId;
    private String chatMessageType;
    private String text;
    private String url;
    private Date createDate;
    private int senderId;
    private int receiverId;
    private int chatMessageStatus;
}
