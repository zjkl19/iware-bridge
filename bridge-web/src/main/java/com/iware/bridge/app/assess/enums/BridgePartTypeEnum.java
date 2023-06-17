package com.iware.bridge.app.assess.enums;

public enum BridgePartTypeEnum {
	
	BridgeDeckType((byte)1,"桥面系"),
	SupStructType((byte)2,"上部结构"),
	SubStructType((byte)3,"下部结构")
	;
	
	private Byte code;
    private String msg;
    
	BridgePartTypeEnum(Byte code,String msg) {
		this.code=code;
		this.msg=msg;
	}

	public Byte getCode() {
		return code;
	}

	public void setCode(Byte code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
