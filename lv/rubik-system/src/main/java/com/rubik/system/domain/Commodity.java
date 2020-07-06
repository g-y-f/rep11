package com.rubik.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
@TableName(value = "commodity")
public class Commodity implements Serializable {
    @TableId(value = "id", type = IdType.ID_WORKER)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /**
     * 名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 商品介绍
     */
    @TableField(value = "introduction")
    private String introduction;

    /**
     * 兑换所需积分
     */
    @TableField(value = "integration")
    private BigDecimal integration;

    /**
     * 数量
     */
    @TableField(value = "qty")
    private Integer qty;

    private static final long serialVersionUID = 1L;
}