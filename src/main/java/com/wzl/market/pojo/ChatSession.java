package com.wzl.market.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
@Data
@TableName("t_chat_session")
public class ChatSession implements Serializable {

    private static final long serialVersionUID = -400704641332112866L;
    private int hostId;
    private int guestId;
    private int unreadCount;
    private int lastChatId;
}
