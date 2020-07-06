package com.rubik.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName(value = "tickets")
public class Tickets implements Serializable {
    @TableId(value = "id", type = IdType.ID_WORKER)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /**
     * 门票名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 门票介绍
     */
    @TableField(value = "contentr")
    private String contentr;

    /**
     * 积分
     */
    @TableField(value = "integration")
    private BigDecimal integration;

    private static final long serialVersionUID = 1L;
}