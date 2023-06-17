package com.iware.bridge.model.entity.global;


import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="BridgeInfo", description="桥梁详情信息表")
public class BridgeInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "结构物id")
    private Integer structureId;

    @ApiModelProperty(value = "总造价")
    private BigDecimal cost;

    @ApiModelProperty(value = "道路等级")
    private String roadGrade;

    @ApiModelProperty(value = "限载标准")
    private String postingStandard;

    @ApiModelProperty(value = "抗震烈度")
    private String quakeIntensity;

    @ApiModelProperty(value = "正斜交角")
    private String skewAngle;

    @ApiModelProperty(value = "桥梁跨度")
    private Integer spanNum;

    @ApiModelProperty(value = "跨径组合")
    private String spanCombination;

    @ApiModelProperty(value = "桥梁面积")
    private Float area;

    @ApiModelProperty(value = "桥梁总长")
    private Float totalLength;

    @ApiModelProperty(value = "桥梁总宽")
    private Float totalWidth;

    @ApiModelProperty(value = "车行道净宽")
    private Float roadwayWidth;

    @ApiModelProperty(value = "人行道净宽")
    private Float sidewalkWidth;

    @ApiModelProperty(value = "河道等级")
    private String streamwayGrade;

    @ApiModelProperty(value = "最高水位")
    private Float highestStage;

    @ApiModelProperty(value = "常水位")
    private Float usualStage;

    @ApiModelProperty(value = "上部结构_主梁型式")
    private String upMainBeamForm;

    @ApiModelProperty(value = "上部结构_主梁尺寸")
    private String upMainBeamSize;

    @ApiModelProperty(value = "上部结构_主梁数量")
    private Integer upMainBeamQuantity;

    @ApiModelProperty(value = "上部结构_横梁型式")
    private String upCrossBeamForm;

    @ApiModelProperty(value = "上部结构_主跨桥下净空")
    private Float clearanceSpan;

    @ApiModelProperty(value = "上部结构_桥下限高")
    private Float lowerLimit;

    @ApiModelProperty(value = "上部结构_跨桥矢跨比")
    private String upRiseSpan;

    @ApiModelProperty(value = "上部结构_支座型式")
    private String upBearingForm;

    @ApiModelProperty(value = "上部结构_支座数量")
    private Integer upBearingNum;

    @ApiModelProperty(value = "上部结构_桥面结构")
    private String upDeckComposition;

    @ApiModelProperty(value = "上部结构_桥面铺装厚度")
    private Float upPavementLand;

    @ApiModelProperty(value = "上部结构_伸缩缝型式")
    private String upExpansionForm;

    @ApiModelProperty(value = "上部结构_伸缩缝数量")
    private Integer upExpansionQuantity;

    @ApiModelProperty(value = "上部结构_主桥纵坡")
    private Float upMainLongitudinalSlope;

    @ApiModelProperty(value = "上部结构_主桥横坡")
    private Float upMainCrossSlope;

    @ApiModelProperty(value = "上部结构_引桥纵坡")
    private Float upApproachLongitudinalSlope;

    @ApiModelProperty(value = "上部结构_引桥横坡")
    private Float upApproachCrossSlope;

    @ApiModelProperty(value = "桥墩_型式")
    private String pierForm;

    @ApiModelProperty(value = "桥墩_数量")
    private Integer pierNum;

    @ApiModelProperty(value = "桥墩_标高")
    private Float pierElevation;

    @ApiModelProperty(value = "桥墩_盖梁尺寸")
    private String pierCapSize;

    @ApiModelProperty(value = "桥墩_基底标高（m）")
    private Float pierBaseElevation;

    @ApiModelProperty(value = "桥墩_底板尺寸")
    private String pierFloorSize;

    @ApiModelProperty(value = "桥墩_基桩尺寸")
    private String pierPileSize;

    @ApiModelProperty(value = "桥墩_基桩根数")
    private Integer pierPileNum;

    @ApiModelProperty(value = "桥台_型式")
    private String abutmentForm;

    @ApiModelProperty(value = "桥台_数量")
    private Integer abutmentNum;

    @ApiModelProperty(value = "桥台_标高")
    private Float abutmentElevation;

    @ApiModelProperty(value = "桥台_基底标高（m）")
    private Float abutmentBaseElevation;

    @ApiModelProperty(value = "桥台_台帽尺寸")
    private String abutmentCapSize;

    @ApiModelProperty(value = "桥台_底板尺寸")
    private String abutmentBaseboardSize;

    @ApiModelProperty(value = "桥台_基桩尺寸")
    private String abutmentPileSize;

    @ApiModelProperty(value = "桥台_基桩根数")
    private Integer abutmentPileNum;

    @ApiModelProperty(value = "桥台_挡土板厚度")
    private Float abutmentRetainThick;

    @ApiModelProperty(value = "桥台_翼墙型式")
    private String abutmentWingWallForm;

    @ApiModelProperty(value = "桥台_翼墙长度")
    private Float abutmentWingWallLength;

    @ApiModelProperty(value = "附属工程_集水口尺寸")
    private String auxiliaryGulleySize;

    @ApiModelProperty(value = "附属工程_集水口数量")
    private Integer auxiliaryGulleyNum;

    @ApiModelProperty(value = "附属工程-泄水管尺寸")
    private String waterDrainPipeSize;

    @ApiModelProperty(value = "附属工程-泄水管长度")
    private Float waterDrainPipeLength;

    @ApiModelProperty(value = "附属工程_栏杆总长")
    private Float auxiliaryRailLength;

    @ApiModelProperty(value = "附属工程_栏杆结构")
    private String auxiliaryRailStruction;

    @ApiModelProperty(value = "附属工程_端柱尺寸")
    private String auxiliaryBoundarySize;

    @ApiModelProperty(value = "附属工程_护岸类型")
    private String auxiliaryRevetmentType;

    @ApiModelProperty(value = "附属工程-引坡挡墙类型")
    private String auxiliaryBarricadeType;

    @ApiModelProperty(value = "附挂管线_给水管")
    private String pipelineWater;

    @ApiModelProperty(value = "附挂管线_燃气管")
    private String pipelineGas;

    @ApiModelProperty(value = "附挂管线_电力缆")
    private String pipelineElectricity;

    @ApiModelProperty(value = "附挂管线_通讯电缆")
    private String pipelineCable;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStructureId() {
        return structureId;
    }

    public void setStructureId(Integer structureId) {
        this.structureId = structureId;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getRoadGrade() {
        return roadGrade;
    }

    public void setRoadGrade(String roadGrade) {
        this.roadGrade = roadGrade;
    }

    public String getPostingStandard() {
        return postingStandard;
    }

    public void setPostingStandard(String postingStandard) {
        this.postingStandard = postingStandard;
    }

    public String getQuakeIntensity() {
        return quakeIntensity;
    }

    public void setQuakeIntensity(String quakeIntensity) {
        this.quakeIntensity = quakeIntensity;
    }

    public String getSkewAngle() {
        return skewAngle;
    }

    public void setSkewAngle(String skewAngle) {
        this.skewAngle = skewAngle;
    }

    public Integer getSpanNum() {
        return spanNum;
    }

    public void setSpanNum(Integer spanNum) {
        this.spanNum = spanNum;
    }

    public String getSpanCombination() {
        return spanCombination;
    }

    public void setSpanCombination(String spanCombination) {
        this.spanCombination = spanCombination;
    }

    public Float getArea() {
        return area;
    }

    public void setArea(Float area) {
        this.area = area;
    }

    public Float getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(Float totalLength) {
        this.totalLength = totalLength;
    }

    public Float getTotalWidth() {
        return totalWidth;
    }

    public void setTotalWidth(Float totalWidth) {
        this.totalWidth = totalWidth;
    }

    public Float getRoadwayWidth() {
        return roadwayWidth;
    }

    public void setRoadwayWidth(Float roadwayWidth) {
        this.roadwayWidth = roadwayWidth;
    }

    public Float getSidewalkWidth() {
        return sidewalkWidth;
    }

    public void setSidewalkWidth(Float sidewalkWidth) {
        this.sidewalkWidth = sidewalkWidth;
    }

    public String getStreamwayGrade() {
        return streamwayGrade;
    }

    public void setStreamwayGrade(String streamwayGrade) {
        this.streamwayGrade = streamwayGrade;
    }

    public Float getHighestStage() {
        return highestStage;
    }

    public void setHighestStage(Float highestStage) {
        this.highestStage = highestStage;
    }

    public Float getUsualStage() {
        return usualStage;
    }

    public void setUsualStage(Float usualStage) {
        this.usualStage = usualStage;
    }

    public String getUpMainBeamForm() {
        return upMainBeamForm;
    }

    public void setUpMainBeamForm(String upMainBeamForm) {
        this.upMainBeamForm = upMainBeamForm;
    }

    public String getUpMainBeamSize() {
        return upMainBeamSize;
    }

    public void setUpMainBeamSize(String upMainBeamSize) {
        this.upMainBeamSize = upMainBeamSize;
    }

    public Integer getUpMainBeamQuantity() {
        return upMainBeamQuantity;
    }

    public void setUpMainBeamQuantity(Integer upMainBeamQuantity) {
        this.upMainBeamQuantity = upMainBeamQuantity;
    }

    public String getUpCrossBeamForm() {
        return upCrossBeamForm;
    }

    public void setUpCrossBeamForm(String upCrossBeamForm) {
        this.upCrossBeamForm = upCrossBeamForm;
    }

    public Float getClearanceSpan() {
        return clearanceSpan;
    }

    public void setClearanceSpan(Float clearanceSpan) {
        this.clearanceSpan = clearanceSpan;
    }

    public Float getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(Float lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public String getUpRiseSpan() {
        return upRiseSpan;
    }

    public void setUpRiseSpan(String upRiseSpan) {
        this.upRiseSpan = upRiseSpan;
    }

    public String getUpBearingForm() {
        return upBearingForm;
    }

    public void setUpBearingForm(String upBearingForm) {
        this.upBearingForm = upBearingForm;
    }

    public Integer getUpBearingNum() {
        return upBearingNum;
    }

    public void setUpBearingNum(Integer upBearingNum) {
        this.upBearingNum = upBearingNum;
    }

    public String getUpDeckComposition() {
        return upDeckComposition;
    }

    public void setUpDeckComposition(String upDeckComposition) {
        this.upDeckComposition = upDeckComposition;
    }

    public Float getUpPavementLand() {
        return upPavementLand;
    }

    public void setUpPavementLand(Float upPavementLand) {
        this.upPavementLand = upPavementLand;
    }

    public String getUpExpansionForm() {
        return upExpansionForm;
    }

    public void setUpExpansionForm(String upExpansionForm) {
        this.upExpansionForm = upExpansionForm;
    }

    public Integer getUpExpansionQuantity() {
        return upExpansionQuantity;
    }

    public void setUpExpansionQuantity(Integer upExpansionQuantity) {
        this.upExpansionQuantity = upExpansionQuantity;
    }

    public Float getUpMainLongitudinalSlope() {
        return upMainLongitudinalSlope;
    }

    public void setUpMainLongitudinalSlope(Float upMainLongitudinalSlope) {
        this.upMainLongitudinalSlope = upMainLongitudinalSlope;
    }

    public Float getUpMainCrossSlope() {
        return upMainCrossSlope;
    }

    public void setUpMainCrossSlope(Float upMainCrossSlope) {
        this.upMainCrossSlope = upMainCrossSlope;
    }

    public Float getUpApproachLongitudinalSlope() {
        return upApproachLongitudinalSlope;
    }

    public void setUpApproachLongitudinalSlope(Float upApproachLongitudinalSlope) {
        this.upApproachLongitudinalSlope = upApproachLongitudinalSlope;
    }

    public Float getUpApproachCrossSlope() {
        return upApproachCrossSlope;
    }

    public void setUpApproachCrossSlope(Float upApproachCrossSlope) {
        this.upApproachCrossSlope = upApproachCrossSlope;
    }

    public String getPierForm() {
        return pierForm;
    }

    public void setPierForm(String pierForm) {
        this.pierForm = pierForm;
    }

    public Integer getPierNum() {
        return pierNum;
    }

    public void setPierNum(Integer pierNum) {
        this.pierNum = pierNum;
    }

    public Float getPierElevation() {
        return pierElevation;
    }

    public void setPierElevation(Float pierElevation) {
        this.pierElevation = pierElevation;
    }

    public String getPierCapSize() {
        return pierCapSize;
    }

    public void setPierCapSize(String pierCapSize) {
        this.pierCapSize = pierCapSize;
    }

    public Float getPierBaseElevation() {
        return pierBaseElevation;
    }

    public void setPierBaseElevation(Float pierBaseElevation) {
        this.pierBaseElevation = pierBaseElevation;
    }

    public String getPierFloorSize() {
        return pierFloorSize;
    }

    public void setPierFloorSize(String pierFloorSize) {
        this.pierFloorSize = pierFloorSize;
    }

    public String getPierPileSize() {
        return pierPileSize;
    }

    public void setPierPileSize(String pierPileSize) {
        this.pierPileSize = pierPileSize;
    }

    public Integer getPierPileNum() {
        return pierPileNum;
    }

    public void setPierPileNum(Integer pierPileNum) {
        this.pierPileNum = pierPileNum;
    }

    public String getAbutmentForm() {
        return abutmentForm;
    }

    public void setAbutmentForm(String abutmentForm) {
        this.abutmentForm = abutmentForm;
    }

    public Integer getAbutmentNum() {
        return abutmentNum;
    }

    public void setAbutmentNum(Integer abutmentNum) {
        this.abutmentNum = abutmentNum;
    }

    public Float getAbutmentElevation() {
        return abutmentElevation;
    }

    public void setAbutmentElevation(Float abutmentElevation) {
        this.abutmentElevation = abutmentElevation;
    }

    public Float getAbutmentBaseElevation() {
        return abutmentBaseElevation;
    }

    public void setAbutmentBaseElevation(Float abutmentBaseElevation) {
        this.abutmentBaseElevation = abutmentBaseElevation;
    }

    public String getAbutmentCapSize() {
        return abutmentCapSize;
    }

    public void setAbutmentCapSize(String abutmentCapSize) {
        this.abutmentCapSize = abutmentCapSize;
    }

    public String getAbutmentBaseboardSize() {
        return abutmentBaseboardSize;
    }

    public void setAbutmentBaseboardSize(String abutmentBaseboardSize) {
        this.abutmentBaseboardSize = abutmentBaseboardSize;
    }

    public String getAbutmentPileSize() {
        return abutmentPileSize;
    }

    public void setAbutmentPileSize(String abutmentPileSize) {
        this.abutmentPileSize = abutmentPileSize;
    }

    public Integer getAbutmentPileNum() {
        return abutmentPileNum;
    }

    public void setAbutmentPileNum(Integer abutmentPileNum) {
        this.abutmentPileNum = abutmentPileNum;
    }

    public Float getAbutmentRetainThick() {
        return abutmentRetainThick;
    }

    public void setAbutmentRetainThick(Float abutmentRetainThick) {
        this.abutmentRetainThick = abutmentRetainThick;
    }

    public String getAbutmentWingWallForm() {
        return abutmentWingWallForm;
    }

    public void setAbutmentWingWallForm(String abutmentWingWallForm) {
        this.abutmentWingWallForm = abutmentWingWallForm;
    }

    public Float getAbutmentWingWallLength() {
        return abutmentWingWallLength;
    }

    public void setAbutmentWingWallLength(Float abutmentWingWallLength) {
        this.abutmentWingWallLength = abutmentWingWallLength;
    }

    public String getAuxiliaryGulleySize() {
        return auxiliaryGulleySize;
    }

    public void setAuxiliaryGulleySize(String auxiliaryGulleySize) {
        this.auxiliaryGulleySize = auxiliaryGulleySize;
    }

    public Integer getAuxiliaryGulleyNum() {
        return auxiliaryGulleyNum;
    }

    public void setAuxiliaryGulleyNum(Integer auxiliaryGulleyNum) {
        this.auxiliaryGulleyNum = auxiliaryGulleyNum;
    }

    public String getWaterDrainPipeSize() {
        return waterDrainPipeSize;
    }

    public void setWaterDrainPipeSize(String waterDrainPipeSize) {
        this.waterDrainPipeSize = waterDrainPipeSize;
    }

    public Float getWaterDrainPipeLength() {
        return waterDrainPipeLength;
    }

    public void setWaterDrainPipeLength(Float waterDrainPipeLength) {
        this.waterDrainPipeLength = waterDrainPipeLength;
    }

    public Float getAuxiliaryRailLength() {
        return auxiliaryRailLength;
    }

    public void setAuxiliaryRailLength(Float auxiliaryRailLength) {
        this.auxiliaryRailLength = auxiliaryRailLength;
    }

    public String getAuxiliaryRailStruction() {
        return auxiliaryRailStruction;
    }

    public void setAuxiliaryRailStruction(String auxiliaryRailStruction) {
        this.auxiliaryRailStruction = auxiliaryRailStruction;
    }

    public String getAuxiliaryBoundarySize() {
        return auxiliaryBoundarySize;
    }

    public void setAuxiliaryBoundarySize(String auxiliaryBoundarySize) {
        this.auxiliaryBoundarySize = auxiliaryBoundarySize;
    }

    public String getAuxiliaryRevetmentType() {
        return auxiliaryRevetmentType;
    }

    public void setAuxiliaryRevetmentType(String auxiliaryRevetmentType) {
        this.auxiliaryRevetmentType = auxiliaryRevetmentType;
    }

    public String getAuxiliaryBarricadeType() {
        return auxiliaryBarricadeType;
    }

    public void setAuxiliaryBarricadeType(String auxiliaryBarricadeType) {
        this.auxiliaryBarricadeType = auxiliaryBarricadeType;
    }

    public String getPipelineWater() {
        return pipelineWater;
    }

    public void setPipelineWater(String pipelineWater) {
        this.pipelineWater = pipelineWater;
    }

    public String getPipelineGas() {
        return pipelineGas;
    }

    public void setPipelineGas(String pipelineGas) {
        this.pipelineGas = pipelineGas;
    }

    public String getPipelineElectricity() {
        return pipelineElectricity;
    }

    public void setPipelineElectricity(String pipelineElectricity) {
        this.pipelineElectricity = pipelineElectricity;
    }

    public String getPipelineCable() {
        return pipelineCable;
    }

    public void setPipelineCable(String pipelineCable) {
        this.pipelineCable = pipelineCable;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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


    @Override
    public String toString() {
        return "BridgeInfo{" +
                "  id=" + id +
                ", structureId=" + structureId +
                ", cost=" + cost +
                ", roadGrade=" + roadGrade +
                ", postingStandard=" + postingStandard +
                ", quakeIntensity=" + quakeIntensity +
                ", skewAngle=" + skewAngle +
                ", spanNum=" + spanNum +
                ", spanCombination=" + spanCombination +
                ", area=" + area +
                ", totalLength=" + totalLength +
                ", totalWidth=" + totalWidth +
                ", roadwayWidth=" + roadwayWidth +
                ", sidewalkWidth=" + sidewalkWidth +
                ", streamwayGrade=" + streamwayGrade +
                ", highestStage=" + highestStage +
                ", usualStage=" + usualStage +
                ", upMainBeamForm=" + upMainBeamForm +
                ", upMainBeamSize=" + upMainBeamSize +
                ", upMainBeamQuantity=" + upMainBeamQuantity +
                ", upCrossBeamForm=" + upCrossBeamForm +
                ", clearanceSpan=" + clearanceSpan +
                ", lowerLimit=" + lowerLimit +
                ", upRiseSpan=" + upRiseSpan +
                ", upBearingForm=" + upBearingForm +
                ", upBearingNum=" + upBearingNum +
                ", upDeckComposition=" + upDeckComposition +
                ", upPavementLand=" + upPavementLand +
                ", upExpansionForm=" + upExpansionForm +
                ", upExpansionQuantity=" + upExpansionQuantity +
                ", upMainLongitudinalSlope=" + upMainLongitudinalSlope +
                ", upMainCrossSlope=" + upMainCrossSlope +
                ", upApproachLongitudinalSlope=" + upApproachLongitudinalSlope +
                ", upApproachCrossSlope=" + upApproachCrossSlope +
                ", pierForm=" + pierForm +
                ", pierNum=" + pierNum +
                ", pierElevation=" + pierElevation +
                ", pierCapSize=" + pierCapSize +
                ", pierBaseElevation=" + pierBaseElevation +
                ", pierFloorSize=" + pierFloorSize +
                ", pierPileSize=" + pierPileSize +
                ", pierPileNum=" + pierPileNum +
                ", abutmentForm=" + abutmentForm +
                ", abutmentNum=" + abutmentNum +
                ", abutmentElevation=" + abutmentElevation +
                ", abutmentBaseElevation=" + abutmentBaseElevation +
                ", abutmentCapSize=" + abutmentCapSize +
                ", abutmentBaseboardSize=" + abutmentBaseboardSize +
                ", abutmentPileSize=" + abutmentPileSize +
                ", abutmentPileNum=" + abutmentPileNum +
                ", abutmentRetainThick=" + abutmentRetainThick +
                ", abutmentWingWallForm=" + abutmentWingWallForm +
                ", abutmentWingWallLength=" + abutmentWingWallLength +
                ", auxiliaryGulleySize=" + auxiliaryGulleySize +
                ", auxiliaryGulleyNum=" + auxiliaryGulleyNum +
                ", waterDrainPipeSize=" + waterDrainPipeSize +
                ", waterDrainPipeLength=" + waterDrainPipeLength +
                ", auxiliaryRailLength=" + auxiliaryRailLength +
                ", auxiliaryRailStruction=" + auxiliaryRailStruction +
                ", auxiliaryBoundarySize=" + auxiliaryBoundarySize +
                ", auxiliaryRevetmentType=" + auxiliaryRevetmentType +
                ", auxiliaryBarricadeType=" + auxiliaryBarricadeType +
                ", pipelineWater=" + pipelineWater +
                ", pipelineGas=" + pipelineGas +
                ", pipelineElectricity=" + pipelineElectricity +
                ", pipelineCable=" + pipelineCable +
                ", status=" + status +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                "}";
    }
}
