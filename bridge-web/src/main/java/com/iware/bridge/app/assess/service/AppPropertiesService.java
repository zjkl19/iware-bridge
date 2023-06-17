package com.iware.bridge.app.assess.service;

/**
 * App常用属性服务
 * @author admin
 *
 */
public interface AppPropertiesService {
	
	
	public String getPropertyPath();
	
	public void setPropertyPath(String propertyPath);
	
	
	/**
	 * 创建桥梁类型
	 */
	public void createBridgeType();
	/**
	 * 创建桥梁病害
	 */
	public void createBridgeDisease();
	
	/**
	 * 创建桥梁部件
	 */
	public void createComponent();
	
	/**
	 * 创建桥梁类型部件关联树形
	 */
	public void createBridgeTypeComponent();
	/**
	 * 创建桥梁类型部件树形
	 */
	public void createBridgeTypeAndComponent();
	/**
	 * 创建桥梁部件病害关联树形
	 */
	public void createComponentDisease();
	
//	/**
//	 * 创建属性组选项关联树形
//	 */
//	public void createPropertiesGroupOptions();
//	
	
	public void createBridgeTypeProperty();
	
	/**
	 * 创建属性
	 */
	public void createProperties();
	/**
	 * 创建属性组
	 */
	public void createPropertyGroup();
	
	/**
	 * 创建属性组选项关联
	 */
	public void createPropertyGroupOptionRel();
	
	/**
	 * 创建属性选项
	 */
	public void createPropertyOption();
	
	/**
	 * 创建选项属性关联表
	 */
	public void createPropertyOptionRel();
	/**
	 * 创建属性关联表
	 */
	public void createPropertyRel();
	
	/**
	 * 运行创建属性配置压缩文件
	 */
	public String runBuild();
}
