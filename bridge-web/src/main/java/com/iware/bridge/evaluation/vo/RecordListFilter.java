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
@ApiModel(value = "RecordListFilter", description = "检测记录-病害修改记录分页过滤器")
@Data
public class RecordListFilter implements Serializable {
    private static final long serialVersionUID = 3908165741882695335L;

    @ApiModelProperty(value = "当前页码")
    private Integer currentPage;

    @ApiModelProperty(value = "每页数量")
    private Integer pageSize;

    @ApiModelProperty(value = "病害实例id")
    private Integer id;


    @ApiModelProperty(value = "创建人")
    private String creator;
}
