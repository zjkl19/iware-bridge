package com.iware.bridge.model.entity.evaluation;


import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="PierAbutment", description="墩台表")
public class PierAbutment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id主键")
    private Integer id;

    @ApiModelProperty(value = "所属下部结构id")
    private Integer bridgeSubstructureId;

    @ApiModelProperty(value = "墩台号")
    private String code;

    public Integer getId() {
		return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBridgeSubstructureId() {
		return bridgeSubstructureId;
    }

    public void setBridgeSubstructureId(Integer bridgeSubstructureId) {
        this.bridgeSubstructureId = bridgeSubstructureId;
    }

    public String getCode() {
		return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @Override
    public String toString() {
        return "PierAbutment{" +
            "  id=" + id +
            ", bridgeSubstructureId=" + bridgeSubstructureId +
            ", code=" + code +
        "}";
    }
}
