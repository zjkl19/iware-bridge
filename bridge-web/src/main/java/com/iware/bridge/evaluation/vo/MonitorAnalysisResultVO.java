package com.iware.bridge.evaluation.vo;

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
@ApiModel(value = "MonitorAnalysisResultVO", description = "检测数据分析统一返回")
@Data
public class MonitorAnalysisResultVO implements Serializable {
    private static final long serialVersionUID = -4237332485851581113L;

    @ApiModelProperty(value = "检测次数")
    private int detectionNumber;

    @ApiModelProperty(value = "检测病害数")
    private int detectionDiseaseNumber;

    @ApiModelProperty(value = "结构病害数量统计")
    private List<DiseaseNumber> diseaseStatistics;

    @ApiModelProperty(value = "构件病害数量统计")
    private List<ComponentDiseaseNumber> componentDiseaseNumber;


}
