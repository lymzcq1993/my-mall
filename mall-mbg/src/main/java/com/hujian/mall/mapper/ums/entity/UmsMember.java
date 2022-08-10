package com.hujian.mall.mapper.ums.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 会员表
 * </p>
 *
 * @author hujian
 * @since 2022-08-10
 */
@Data
@TableName("ums_member")
@ApiModel(value = "UmsMember对象", description = "会员表")
public class UmsMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    @TableField("member_level_id")
    @ApiModelProperty("会员等级id")
    private Long memberLevelId;

    @ApiModelProperty("用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty("密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("昵称")
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty("手机号码")
    @TableField("phone")
    private String phone;

    @ApiModelProperty("帐号启用状态:0->禁用；1->启用")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("注册时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("头像")
    @TableField("icon")
    private String icon;

    @ApiModelProperty("性别：0->未知；1->男；2->女")
    @TableField("gender")
    private Integer gender;

    @ApiModelProperty("生日")
    @TableField("birthday")
    private LocalDate birthday;

    @ApiModelProperty("所做城市")
    @TableField("city")
    private String city;

    @ApiModelProperty("职业")
    @TableField("job")
    private String job;

    @ApiModelProperty("个性签名")
    @TableField("personalized_signature")
    private String personalizedSignature;

    @ApiModelProperty("用户来源")
    @TableField("source_type")
    private Integer sourceType;

    @ApiModelProperty("积分")
    @TableField("integration")
    private Integer integration;

    @ApiModelProperty("成长值")
    @TableField("growth")
    private Integer growth;

    @ApiModelProperty("剩余抽奖次数")
    @TableField("luckey_count")
    private Integer luckeyCount;

    @ApiModelProperty("历史积分数量")
    @TableField("history_integration")
    private Integer historyIntegration;


}
