package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: HX
 * @date: 2021-7-1
 * @since 1.0
 */
@ApiModel(value = "GenerateReportVO", description = "生成报告")
@Data
public class GenerateReportVO implements Serializable {
    private static final long serialVersionUID = -7809918731427373654L;

    @ApiModelProperty(value = "线路id")
    private Integer id;

    @ApiModelProperty(value = "结构物名称")
    private String structureName;

    @ApiModelProperty(value = "线路名称")
    private String roadName;

    @ApiModelProperty(value = "检测依据")
    private String detectionBase;

    @ApiModelProperty(value = "检测类型")
    private String detectionType;

    @ApiModelProperty(value = "养护级别")
    private String bridgeLevel;
}
