package com.iware.bridge.evaluation.enums;

public enum AttachStatusEnum {
	
	NORMAL((byte)1, "正常"),
    DELETED((byte)0, "删除"),
    SELECTED((byte)2, "选中")
    ;
	private Byte code;
    private String msg;
    
    AttachStatusEnum(Byte code, String msg) {
    	this.code=code;
    	this.msg=msg;
    }
    
    AttachStatusEnum(String msg) {
        this.msg = msg;
    }

    public Byte getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
