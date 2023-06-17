package com.iware.common.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.reflect.Method;

@ApiModel(value = "FieldInfo",description = "字段映射信息")
public class FieldInfo {

    private static final long serialVersionUID = -1164166100970511032L;

    @ApiModelProperty(value="字段名")
    private String fieldName;
    @ApiModelProperty(value="表头名")
    private String headerName;
    @ApiModelProperty(value="方法信息")
    private Method method;

    public FieldInfo() {
    }

    public FieldInfo(String fieldName, String headerName) {
        this.fieldName = fieldName;
        this.headerName = headerName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
