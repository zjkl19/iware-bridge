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

@ApiModel(value="BridgeSupstructure", description="桥梁上部结构")
public class BridgeSupstructure implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "桥跨id")
    private Integer bridgeSpanId;

    @ApiModelProperty(value = "梁类型")
    private Integer beamType;

    @ApiModelProperty(value = "梁数量")
    private Integer beamNumber;

    @ApiModelProperty(value = "挂梁数")
    private Integer hangingBeam;

    @ApiModelProperty(value = "挂梁支座数")
    private Integer hangingBeamSupport;

    @ApiModelProperty(value = "防落梁装置数")
    private Integer antiFallingBeam;

    @ApiModelProperty(value = "桁片数")
    private Integer truss;

    @ApiModelProperty(value = "主节点数")
    private Integer primaryNode;

    @ApiModelProperty(value = "纵梁数")
    private Integer stringer;

    @ApiModelProperty(value = "横梁数")
    private Integer crossBeam;

    @ApiModelProperty(value = "连接件数")
    private Integer connector;

    @ApiModelProperty(value = "主拱圈数")
    private Integer archRing;

    @ApiModelProperty(value = "横向联系数")
    private Integer horizontaConnection;

    @ApiModelProperty(value = "拱上构造数")
    private Integer archStructure;

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

    public Integer getBeamType() {
		return beamType;
    }

    public void setBeamType(Integer beamType) {
        this.beamType = beamType;
    }

    public Integer getBeamNumber() {
		return beamNumber;
    }

    public void setBeamNumber(Integer beamNumber) {
        this.beamNumber = beamNumber;
    }

    public Integer getHangingBeam() {
		return hangingBeam;
    }

    public void setHangingBeam(Integer hangingBeam) {
        this.hangingBeam = hangingBeam;
    }

    public Integer getHangingBeamSupport() {
		return hangingBeamSupport;
    }

    public void setHangingBeamSupport(Integer hangingBeamSupport) {
        this.hangingBeamSupport = hangingBeamSupport;
    }

    public Integer getAntiFallingBeam() {
		return antiFallingBeam;
    }

    public void setAntiFallingBeam(Integer antiFallingBeam) {
        this.antiFallingBeam = antiFallingBeam;
    }

    public Integer getTruss() {
		return truss;
    }

    public void setTruss(Integer truss) {
        this.truss = truss;
    }

    public Integer getPrimaryNode() {
		return primaryNode;
    }

    public void setPrimaryNode(Integer primaryNode) {
        this.primaryNode = primaryNode;
    }

    public Integer getStringer() {
		return stringer;
    }

    public void setStringer(Integer stringer) {
        this.stringer = stringer;
    }

    public Integer getCrossBeam() {
		return crossBeam;
    }

    public void setCrossBeam(Integer crossBeam) {
        this.crossBeam = crossBeam;
    }

    public Integer getConnector() {
		return connector;
    }

    public void setConnector(Integer connector) {
        this.connector = connector;
    }

    public Integer getArchRing() {
		return archRing;
    }

    public void setArchRing(Integer archRing) {
        this.archRing = archRing;
    }

    public Integer getHorizontaConnection() {
		return horizontaConnection;
    }

    public void setHorizontaConnection(Integer horizontaConnection) {
        this.horizontaConnection = horizontaConnection;
    }

    public Integer getArchStructure() {
		return archStructure;
    }

    public void setArchStructure(Integer archStructure) {
        this.archStructure = archStructure;
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
        return "BridgeSupstructure{" +
            "  id=" + id +
            ", bridgeSpanId=" + bridgeSpanId +
            ", beamType=" + beamType +
            ", beamNumber=" + beamNumber +
            ", hangingBeam=" + hangingBeam +
            ", hangingBeamSupport=" + hangingBeamSupport +
            ", antiFallingBeam=" + antiFallingBeam +
            ", truss=" + truss +
            ", primaryNode=" + primaryNode +
            ", stringer=" + stringer +
            ", crossBeam=" + crossBeam +
            ", connector=" + connector +
            ", archRing=" + archRing +
            ", horizontaConnection=" + horizontaConnection +
            ", archStructure=" + archStructure +
            ", exteriorTrimPanel=" + exteriorTrimPanel +
            ", monitorDiagram=" + monitorDiagram +
            ", creator=" + creator +
            ", createTime=" + createTime +
            ", modifyTime=" + modifyTime +
            ", status=" + status +
        "}";
    }
}
