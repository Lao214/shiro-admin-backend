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
 * 
 * </p>
 *
 * @author 劳威锟
 * @since 2024-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@ApiModel(value="BUserRole对象", description="")
public class BUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    //@ApiModelProperty(value = "id")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //@ApiModelProperty(value = "用户id")
    private Long userId;

    //@ApiModelProperty(value = "角色id")
    private Long roleId;


}
