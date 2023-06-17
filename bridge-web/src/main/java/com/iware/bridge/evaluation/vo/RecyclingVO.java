package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Recycling", description = "回收站信息")
public class RecyclingVO {

    @ApiModelProperty(value = "创建人")
    private String creator;
    @ApiModelProperty(value = "上传时间")
    private String createTime;
    @ApiModelProperty(value = "是否是删除的数据：0：删除")
    private String isDelete;

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
