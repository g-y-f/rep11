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

@Data
@TableName(value = "ticket_orders")
public class TicketOrders implements Serializable {
    @TableId(value = "id", type = IdType.ID_WORKER)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /**
     * 用户编码
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 用户名称
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 门票编码
     */
    @TableField(value = "tickets_id")
    private Long ticketsId;

    /**
     * 门票名称
     */
    @TableField(value = "tickets_name")
    private String ticketsName;

    /**
     * 门票生效时间
     */
    @TableField(value = "starting_time")
    private Date startingTime;

    /**
     * 门票失效时间
     */
    @TableField(value = "end_time")
    private Date endTime;

    /**
     * 实付
     */
    @TableField(value = "actually_paid")
    private BigDecimal actuallyPaid;

    /**
     * 状态
     */
    @TableField(value = "status")
    private Integer status;

    private static final long serialVersionUID = 1L;
}