package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(value="StructPhoto",description="桥梁结构图片")
public class StructPhoto implements Serializable{
	
	private static final long serialVersionUID = 2153095425411779724L;
	
	
	@ApiModelProperty(value="图片名称")
	private String name;
	@ApiModelProperty(value="图片地址")
	private String path;
	@ApiModelProperty(value="创建时间")
	private Date createTime;
	@ApiModelProperty(value="状态")
	private Byte status;
	//附件类型 1：线路图片 2：桥垮图片 3：部位图片 4：构件图片 5：病害图片
	@ApiModelProperty(value="类型")
	private Byte type;
	//1：桥面系 2：上部结构 3：下部结构
	@ApiModelProperty(value="部位类型")
	private Byte partType;
	@ApiModelProperty(value="目标id")
	private Integer targetId;
	@ApiModelProperty(value="id")
	private Integer id;
	@ApiModelProperty(value="状态筛选")
	private List<Byte> statusFilter;
	@ApiModelProperty(value="是否可删除 1:可删除 0:不可删除")
	private Byte isCanDel;

	public Byte getIsCanDel() {
		return isCanDel;
	}

	public void setIsCanDel(Byte isCanDel) {
		this.isCanDel = isCanDel;
	}

	public List<Byte> getStatusFilter() {
		return statusFilter;
	}
	public void setStatusFilter(List<Byte> statusFilter) {
		this.statusFilter = statusFilter;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTargetId() {
		return targetId;
	}
	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}
	public Byte getPartType() {
		return partType;
	}
	public void setPartType(Byte partType) {
		this.partType = partType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public Byte getType() {
		return type;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	
	public String getTypeStr() {
		switch(type) {
		case 1:
			return "线路图片";
		case 2:
			return "桥垮图片";
		case 3:
			switch(partType) {
				case 1:return "桥面系部位图片";
				case 2:return "上部结构部位图片";
				case 3:return "下部结构部位图片";
			}
		case 4:
			switch(partType) {
				case 1:return "桥面系构件图片";
				case 2:return "上部结构构件图片";
				case 3:return "下部结构构件图片";
			}
			
		}
		return "";
		
	}
	
	@Override
    public String toString() {
        return "StructPhoto{" +
                "name=" + name +
                ", path='" + path + '\'' +
                ", createTime=" + createTime +
                ", status=" + status +
                ", type=" + type +
                ", partType="+partType+
                ",targetId="+targetId+
                '}';
    }
}
