package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: HX
 * @date: 2021-6-30
 * @since 1.0
 */
@ApiModel(value = "RecordDetailListFilter", description = "检测记录-桥梁病害-回收站-记录详情过滤器")
@Data
public class RecordDetailListFilter implements Serializable {
    private static final long serialVersionUID = 1640673602251643585L;

    @ApiModelProperty(value = "结构物计划id")
    private Integer id;

    @ApiModelProperty(value = "创建人")
    private String creator;

    @ApiModelProperty(value = "上传时间")
    private Date createTime;
}
