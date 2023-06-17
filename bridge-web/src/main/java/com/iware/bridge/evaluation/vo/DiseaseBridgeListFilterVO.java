package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: HX
 * @date: 2021-6-30
 * @since 1.0
 */
@ApiModel(value = "DiseaseBridgeListFilterVO", description = "检测记录-桥梁病害查询过滤器")
@Data
public class DiseaseBridgeListFilterVO implements Serializable {
    private static final long serialVersionUID = -4704029503553839703L;

    @ApiModelProperty(value = "检测结构物id")
    private Integer id;

    @ApiModelProperty(value = "关键字")
    private String keyword;
}
