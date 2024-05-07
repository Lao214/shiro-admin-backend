package com.example.shiro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author 劳威锟
 * @since 2024-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@ApiModel(value="BPer对象", description="权限表")
public class BPer implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //@ApiModelProperty(value = "权限名称")
    private String perName;

    //@ApiModelProperty(value = "权限标识")
    private String perCode;

    //@ApiModelProperty(value = "(1表示per 普通权限，2表示menu 菜单权限)")
    private Integer perType;

    //@ApiModelProperty(value = "创建时间")
    private Date createTime;

    //@ApiModelProperty(value = "更新时间")
    private Date updateTime;

    //@ApiModelProperty(value = "菜单标签")
    private String perIcon;

    //@ApiModelProperty(value = "路径")
    private String perPath;

    private Long parentId;

    @TableField(exist = false)
    private List<BPer> children;

}
