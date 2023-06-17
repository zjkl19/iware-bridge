package com.iware.bridge.model.entity.global;


import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-10-19 17:00:00
 * @version 1.3.3
 */

@ApiModel(value="ProjectStructureRel", description="项目结构物关联表")
public class ProjectStructureRel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "项目id")
    private Integer projectId;

    @ApiModelProperty(value = "结构物id")
    private Integer structureId;

    public Integer getId() {
		return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
		return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getStructureId() {
		return structureId;
    }

    public void setStructureId(Integer structureId) {
        this.structureId = structureId;
    }


    @Override
    public String toString() {
        return "ProjectStructureRel{" +
            "  id=" + id +
            ", projectId=" + projectId +
            ", structureId=" + structureId +
        "}";
    }
}
