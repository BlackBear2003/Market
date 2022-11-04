package com.wzl.market.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

import static com.baomidou.mybatisplus.annotation.IdType.AUTO;

@Data
@RequiredArgsConstructor
@TableName("t_good_picture")
public class GoodPicture implements Serializable {

    private static final long serialVersionUID = 3416704066925043705L;
    @TableId(type = AUTO)
    @NonNull
    private int pictureId;
    private String url;
    private String pictureDescription;
    private int goodId;
    private int isMaster;

}
