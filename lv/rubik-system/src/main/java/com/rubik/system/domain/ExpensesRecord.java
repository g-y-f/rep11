package com.rubik.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
    * 用户消费记录
    */
@Data
@TableName(value = "expenses_record")
public class ExpensesRecord implements Serializable {
    @TableId(value = "id", type = IdType.ID_WORKER)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 用户名
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 消费类型
     */
    @TableField(value = "type")
    private String type;

    /**
     * 消费详情
     */
    @TableField(value = "expenses_details")
    private String expensesDetails;

    /**
     * 消费价格
     */
    @TableField(value = "price")
    private BigDecimal price;

    /**
     * 积分
     */
    @TableField(value = "integration")
    private BigDecimal integration;

    /**
     * 消费时间
     */
    @TableField(value = "time_spent")
    private Date timeSpent;

    private static final long serialVersionUID = 1L;
}