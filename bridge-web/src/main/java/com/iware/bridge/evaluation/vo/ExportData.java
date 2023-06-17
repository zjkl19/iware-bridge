package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "ExportData", description = "导出记录word信息")
public class ExportData {
    @ApiModelProperty(value = "文件前缀")
    private String filePath;
    @ApiModelProperty(value = "项目前缀")
    private String fileContext;
    @ApiModelProperty(value = "计划结构物检测id")
    private Integer id;
    @ApiModelProperty(value = "文件名")
    private String fileName;
    @ApiModelProperty(value = "原始记录信息")
    private OriginalRecord originalRecord;
    @ApiModelProperty(value = "桥面系照片")
    private List<PhotoUrl> photoUrlsBD;
    @ApiModelProperty(value = "上部结构照片")
    private List<PhotoUrl> photoUrlsSUP;
    @ApiModelProperty(value = "下部结构照片")
    private List<PhotoUrl> photoUrlsSUB;
    @ApiModelProperty(value = "桥面系病害信息")
    private List<DetectionRecord> detectionRecordsBD;
    @ApiModelProperty(value = "上部结构病害信息")
    private List<DetectionRecord> detectionRecordsSUP;
    @ApiModelProperty(value = "下部结构病害信息")
    private List<DetectionRecord> detectionRecordsSUB;

    public String getFileContext() {
        return fileContext;
    }

    public void setFileContext(String fileContext) {
        this.fileContext = fileContext;
    }

    public OriginalRecord getOriginalRecord() {
        return originalRecord;
    }

    public void setOriginalRecord(OriginalRecord originalRecord) {
        this.originalRecord = originalRecord;
    }

    public List<PhotoUrl> getPhotoUrlsBD() {
        return photoUrlsBD;
    }

    public void setPhotoUrlsBD(List<PhotoUrl> photoUrlsBD) {
        this.photoUrlsBD = photoUrlsBD;
    }

    public List<PhotoUrl> getPhotoUrlsSUP() {
        return photoUrlsSUP;
    }

    public void setPhotoUrlsSUP(List<PhotoUrl> photoUrlsSUP) {
        this.photoUrlsSUP = photoUrlsSUP;
    }

    public List<PhotoUrl> getPhotoUrlsSUB() {
        return photoUrlsSUB;
    }

    public void setPhotoUrlsSUB(List<PhotoUrl> photoUrlsSUB) {
        this.photoUrlsSUB = photoUrlsSUB;
    }

    public List<DetectionRecord> getDetectionRecordsBD() {
        return detectionRecordsBD;
    }

    public void setDetectionRecordsBD(List<DetectionRecord> detectionRecordsBD) {
        this.detectionRecordsBD = detectionRecordsBD;
    }

    public List<DetectionRecord> getDetectionRecordsSUP() {
        return detectionRecordsSUP;
    }

    public void setDetectionRecordsSUP(List<DetectionRecord> detectionRecordsSUP) {
        this.detectionRecordsSUP = detectionRecordsSUP;
    }

    public List<DetectionRecord> getDetectionRecordsSUB() {
        return detectionRecordsSUB;
    }

    public void setDetectionRecordsSUB(List<DetectionRecord> detectionRecordsSUB) {
        this.detectionRecordsSUB = detectionRecordsSUB;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
