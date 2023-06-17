package com.iware.bridge.app.assess.vo.offline.offlineData;


public class StructureInfoVo {
    private Integer id;
    private Integer structureType;
    private String structureCode;
    private String structureName;
    private String structureNumber;
    private String technology;
    private Boolean idReal;
    private Integer projectId;
    private Integer status;

    private Integer unitId;
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getStructureType() {
        return structureType;
    }

    public void setStructureType(Integer structureType) {
        this.structureType = structureType;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Boolean isIdReal() {
        return idReal;
    }

    public void setIdReal(Boolean idReal) {
        this.idReal = idReal;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStructureCode() {
        return structureCode;
    }

    public void setStructureCode(String structureCode) {
        this.structureCode = structureCode;
    }

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public String getStructureNumber() {
        return structureNumber;
    }

    public void setStructureNumber(String structureNumber) {
        this.structureNumber = structureNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
