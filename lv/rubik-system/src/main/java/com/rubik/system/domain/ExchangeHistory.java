package com.rubik.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
@TableName(value = "exchange_history")
public class ExchangeHistory implements Serializable {
    @TableId(value = "id", type = IdType.ID_WORKER)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /**
     * 商品id
     */
    @TableField(value = "commodity_id")
    private Long commodityId;

    /**
     * 商品名称
     */
    @TableField(value = "commodity_name")
    private String commodityName;

    /**
     * 兑换时间
     */
    @TableField(value = "redemption_time")
    private Date redemptionTime;

    /**
     * 兑换数量
     */
    @TableField(value = "exchange_quantity")
    private Integer exchangeQuantity;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "user_name")
    private String userName;

    private static final long serialVersionUID = 1L;
}