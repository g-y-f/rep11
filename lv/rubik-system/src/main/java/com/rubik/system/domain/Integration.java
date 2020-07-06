package com.rubik.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
@TableName(value = "integration")
public class Integration implements Serializable {
    @TableId(value = "user_id", type = IdType.INPUT)
    private Long userId;

    @TableField(value = "integration")
    private BigDecimal integration;

    private static final long serialVersionUID = 1L;
}