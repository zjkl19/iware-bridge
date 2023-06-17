package com.iware.bridge.evaluation.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: HX
 * @date: 2021-7-1
 * @since 1.0
 */
@ApiModel(value = "MonitorAnalysisFilterVO", description = "检测分析查询过滤类")
@Data
public class MonitorAnalysisFilterVO implements Serializable {
    private static final long serialVersionUID = 8266098223172919338L;

    @ApiModelProperty(value = "结构物计划id")
    private Integer id;

    @ApiModelProperty(value = "时间")
    private String time;

    @ApiModelProperty(value = "项目id")
    private Integer projectId;

    @ApiModelProperty(value = "结构物id")
    private Integer structureId;

    @ApiModelProperty(value = "构建病害类型统计查询所选择的条件")
    private String typeName;

    @ApiModelProperty(value = "构件病害类型统计对查询条件拆解-结构类型")
    @JsonIgnore
    private String componentType;

    @ApiModelProperty(value = "构件病害类型统计对查询条件拆解-结构名称")
    @JsonIgnore
    private String componentName;

    @ApiModelProperty(value = "查询最新项目")
    @JsonIgnore
    private List<AppointProjectVO> list;

}
