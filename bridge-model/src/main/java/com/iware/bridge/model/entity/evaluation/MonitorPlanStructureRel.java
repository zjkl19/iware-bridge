package com.iware.bridge.model.entity.evaluation;


import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="MonitorPlanStructureRel", description="监测计划结构物关联表")
public class MonitorPlanStructureRel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id主键")
    private Integer id;

    @ApiModelProperty(value = "计划id")
    private Integer monitorPlanId;

    @ApiModelProperty(value = "结构物id")
    private Integer structureInfoId;

    @ApiModelProperty(value = "结构物名称")
    private String structureName;

    @ApiModelProperty(value = "方案名称")
    private String fileName;

    @ApiModelProperty(value = "方案url")
    private String fileUrl;

    @ApiModelProperty(value = "0:否1:是 (为空也是否)")
    private Integer isDelete;

    public Integer getId() {
		return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMonitorPlanId() {
		return monitorPlanId;
    }

    public void setMonitorPlanId(Integer monitorPlanId) {
        this.monitorPlanId = monitorPlanId;
    }

    public Integer getStructureInfoId() {
		return structureInfoId;
    }

    public void setStructureInfoId(Integer structureInfoId) {
        this.structureInfoId = structureInfoId;
    }

    public String getStructureName() {
		return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public String getFileName() {
		return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
		return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Integer getIsDelete() {
		return isDelete;
    }

    public void setIsDelete(Integer delete) {
        this.isDelete = delete;
    }


    @Override
    public String toString() {
        return "MonitorPlanStructureRel{" +
            "  id=" + id +
            ", monitorPlanId=" + monitorPlanId +
            ", structureInfoId=" + structureInfoId +
            ", structureName=" + structureName +
            ", fileName=" + fileName +
            ", fileUrl=" + fileUrl +
            ", delete=" + isDelete +
        "}";
    }
}
