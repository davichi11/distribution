package com.distribution.modules.integral.entity

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

import java.io.Serializable

/**
 * Created by Mybatis Generator 2018/12/04
 */
@ApiModel(value = "com.distribution.modules.integral.entity.IntegralTutorial")
data class IntegralTutorial(
        @ApiModelProperty(value = "")
        var id: Int? = null,
        /* 产品类型ID*/
        @ApiModelProperty(value = "产品类型ID")
        var typeId: String? = null,

        /* 步骤*/
        @ApiModelProperty(value = "步骤")
        var step: Int? = null,

        /* 文字描述*/
        @ApiModelProperty(value = "文字描述")
        var textDescribe: String? = null,

        /* 图片描述*/
        @ApiModelProperty(value = "图片描述")
        var imgDescribe: String? = null
) : Serializable