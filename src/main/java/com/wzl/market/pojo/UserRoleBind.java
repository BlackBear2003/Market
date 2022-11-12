package com.wzl.market.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_user_role")
public class UserRoleBind {
    Integer userId;
    Integer roleId;
}
