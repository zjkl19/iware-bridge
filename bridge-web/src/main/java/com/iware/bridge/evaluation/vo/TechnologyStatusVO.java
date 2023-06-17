package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: HX
 * @date: 2021-7-28
 * @since 1.0
 */
@Data
@ApiModel(value = "TechnologyStatusVO" , description = "城市/公路统计")
public class TechnologyStatusVO implements Serializable {
    private static final long serialVersionUID = 9000663347727146924L;

    @ApiModelProperty(value = "评分/类别")
    private String grade;

    @ApiModelProperty(value = "数量")
    private Integer count;

    @ApiModelProperty(value = "技术类型")
    private String technology;

}
