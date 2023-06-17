package com.iware.bridge.model.entity.evaluation;


import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="BridgeOtherStructure", description="")
public class BridgeOtherStructure implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id主键")
    private Integer id;

    @ApiModelProperty(value = "桥跨id")
    private Integer bridgeSpanId;

    @ApiModelProperty(value = "描述")
    private String descript;

    @ApiModelProperty(value = "创建者")
    private String creator;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "1：正常 0：回收")
    private Integer status;

    public Integer getId() {
		return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBridgeSpanId() {
		return bridgeSpanId;
    }

    public void setBridgeSpanId(Integer bridgeSpanId) {
        this.bridgeSpanId = bridgeSpanId;
    }

    public String getDescript() {
		return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getCreator() {
		return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
		return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
		return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getStatus() {
		return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "BridgeOtherStructure{" +
            "  id=" + id +
            ", bridgeSpanId=" + bridgeSpanId +
            ", descript=" + descript +
            ", creator=" + creator +
            ", createTime=" + createTime +
            ", modifyTime=" + modifyTime +
            ", status=" + status +
        "}";
    }
}
