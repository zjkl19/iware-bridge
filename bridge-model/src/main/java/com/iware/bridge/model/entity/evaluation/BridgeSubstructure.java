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

@ApiModel(value="BridgeSubstructure", description="桥梁下部结构")
public class BridgeSubstructure implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "桥跨id")
    private Integer bridgeSpanId;

    @ApiModelProperty(value = "单排敦数")
    private Integer singleRowDun;

    @ApiModelProperty(value = "单排支座数")
    private Integer singleRowSupport;

    @ApiModelProperty(value = "拱脚数")
    private Integer archFoot;

    @ApiModelProperty(value = "外部装饰板数")
    private Integer exteriorTrimPanel;

    @ApiModelProperty(value = "模型地址")
    private String monitorDiagram;

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

    public Integer getSingleRowDun() {
		return singleRowDun;
    }

    public void setSingleRowDun(Integer singleRowDun) {
        this.singleRowDun = singleRowDun;
    }

    public Integer getSingleRowSupport() {
		return singleRowSupport;
    }

    public void setSingleRowSupport(Integer singleRowSupport) {
        this.singleRowSupport = singleRowSupport;
    }

    public Integer getArchFoot() {
		return archFoot;
    }

    public void setArchFoot(Integer archFoot) {
        this.archFoot = archFoot;
    }

    public Integer getExteriorTrimPanel() {
		return exteriorTrimPanel;
    }

    public void setExteriorTrimPanel(Integer exteriorTrimPanel) {
        this.exteriorTrimPanel = exteriorTrimPanel;
    }

    public String getMonitorDiagram() {
		return monitorDiagram;
    }

    public void setMonitorDiagram(String monitorDiagram) {
        this.monitorDiagram = monitorDiagram;
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
        return "BridgeSubstructure{" +
            "  id=" + id +
            ", bridgeSpanId=" + bridgeSpanId +
            ", singleRowDun=" + singleRowDun +
            ", singleRowSupport=" + singleRowSupport +
            ", archFoot=" + archFoot +
            ", exteriorTrimPanel=" + exteriorTrimPanel +
            ", monitorDiagram=" + monitorDiagram +
            ", creator=" + creator +
            ", createTime=" + createTime +
            ", modifyTime=" + modifyTime +
            ", status=" + status +
        "}";
    }
}
