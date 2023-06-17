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
@ApiModel(value = "RecyclingListFilter", description = "检测记录-桥梁病害-回收站分页过滤器")
@Data
public class RecyclingListFilter implements Serializable {
    private static final long serialVersionUID = 820440473755686228L;

    @ApiModelProperty(value = "结构物计划id")
    private Integer id;

    @ApiModelProperty(value = "页码")
    private Integer currentPage;

    @ApiModelProperty(value = "每页数量")
    private Integer pageSize;
}
