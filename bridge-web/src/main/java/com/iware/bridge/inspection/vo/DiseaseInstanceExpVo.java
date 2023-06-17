package com.iware.bridge.inspection.vo;

import com.iware.bridge.model.entity.global.Photo;
import com.iware.bridge.model.entity.inspection.InspectionDiseaseInstance;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("病害选项拓展实体类")
public class DiseaseInstanceExpVo extends InspectionDiseaseInstance {

    @ApiModelProperty("选项列表")
    private List<Integer> optionIds;

    @ApiModelProperty(value = "图片列表")
    private List<Photo> photoList;

    public List<Integer> getOptionIds() {
        return optionIds;
    }

    public void setOptionIds(List<Integer> optionIds) {
        this.optionIds = optionIds;
    }

    public List<Photo> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<Photo> photoList) {
        this.photoList = photoList;
    }
}
