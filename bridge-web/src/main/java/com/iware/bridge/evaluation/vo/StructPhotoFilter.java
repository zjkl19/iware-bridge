package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value="StructPhotoFilter",description="桥梁结构图片查询过滤器")
public class StructPhotoFilter implements Serializable{
	
	private static final long serialVersionUID = 2153095425411779724L;
	
	
	@ApiModelProperty(value="状态(这是一个数组，1：正常 0：删除 2：选中)",notes="这是一个数组，1：正常 0：删除 2：选中" )
	private List<Byte> status;
	@ApiModelProperty(value="类型( 1：线路图片 2：桥垮图片 3：部位图片 4：构件图片 5：病害图片)")
	private Integer type;
	@ApiModelProperty(value="部位类型(1：桥面系 2：上部结构 3：下部结构)")
	private Integer partType;
	@ApiModelProperty(value="目标id")
	private Integer targetId;
	@ApiModelProperty(value="检测计划桥梁关联id")
	private Integer moniPlanStructId;
	public Integer getPartType() {
		return partType;
	}
	public void setPartType(Integer partType) {
		this.partType = partType;
	}
	public Integer getTargetId() {
		return targetId;
	}
	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}
	
	public List<Byte> getStatus() {
		return status;
	}
	public void setStatus(List<Byte> status2) {
		this.status = status2;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getMoniPlanStructId() {
		return moniPlanStructId;
	}

	public void setMoniPlanStructId(Integer moniPlanStructId) {
		this.moniPlanStructId = moniPlanStructId;
	}

	@Override
    public String toString() {
        return "StructPhoto{" +
               
                "status=" + status.toString() +
                ", type=" + type +
                ", targetId="+ targetId+
                ", partType="+ partType+
                '}';
    }
}
