package com.example.shiro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户权限连接表
 * </p>
 *
 * @author 劳威锟
 * @since 2024-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@ApiModel(value="BRolePer对象", description="用户权限连接表")
public class BRolePer implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //@ApiModelProperty(value = "角色id")
    private Long roleId;

    //@ApiModelProperty(value = "权限ID")
    private Long perId;


}
