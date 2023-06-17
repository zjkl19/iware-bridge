package com.iware.bridge.permission.enums;

public enum ActionTypeEnum {

	ACTION_UDY(0, null),
	ACTION_QRY(1, "查看"),
	ACTION_ADD(2, "新增"),
	ACTION_UPD(3, "修改"),
	ACTION_DEL(4, "删除"),
	ACTION_APT(5, "指派");

	ActionTypeEnum(Integer type, String name){
		this.type = type;
		this.name= name;
	}

	private Integer type;
	private String name;
	public Integer getType() {
		return type;
	}
	private void setType(Integer type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	private void setName(String name) {
		this.name = name;
	}

}
