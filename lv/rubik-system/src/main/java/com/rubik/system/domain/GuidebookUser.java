package com.rubik.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

@Data
@TableName(value = "guidebook_user")
public class GuidebookUser implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "user_name")
    private String userName;

    /**
     * 导游编号
     */
    @TableField(value = "touristGuide_id")
    private Long touristguideId;

    /**
     * 导游名称
     */
    @TableField(value = "touristGuide_name")
    private String touristguideName;

    /**
     * 评分
     */
    @TableField(value = "score")
    private Integer score;

    /**
     * 评价
     */
    @TableField(value = "reviews")
    private String reviews;

    private static final long serialVersionUID = 1L;
}