package com.iware.bridge.app.assess.service;

import com.alibaba.fastjson.JSONArray;
import com.iware.bridge.app.assess.dao.AppPropertiesDao;
import com.iware.bridge.app.assess.vo.offline.property.BridgeTypeComponentVo;
import com.iware.bridge.app.assess.vo.offline.property.BridgeTypeVo;
import com.iware.bridge.app.assess.vo.offline.property.ComponentDiseaseVo;
import com.iware.bridge.model.dao.evaluation.*;
import com.iware.bridge.model.entity.evaluation.*;
import com.iware.common.exception.BusinessException;
import com.iware.common.utils.FileUtil;
import com.iware.common.utils.InZipUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * App常用属性服务
 * @author admin
 *
 */
@Service
public class AppPropertiesServiceImpl implements AppPropertiesService{
	
	@Autowired
	private BridgeTypeDao bridgeTypeDao;
	@Autowired
	private BridgeDiseaseDao bridgeDiseaseDao;
	@Autowired
	private ComponentDao componentDao;
	@Autowired
	private AppPropertiesDao appPropertiesDao;
	@Autowired
	private PropertyDao propertyDao;
	@Autowired
	private PropertyGroupDao propertyGroupDao;
	@Autowired
	private PropertyGroupOptionRelDao propertyGroupOptionRelDao;
	@Autowired
	private PropertyOptionDao  propertyOptionDao;
	@Autowired
	private PropertyOptionRelDao propertyOptionRelDao;
	@Autowired
	private PropertyRelDao propertyRelDao;
	
	
	private String propertyPath;
	/**
	 * 创建桥梁类型
	 */
	
	public String getPropertyPath() {
		return propertyPath;
	}
	
	public void setPropertyPath(String propertyPath) {
		this.propertyPath = propertyPath;
	}
	
	public void createBridgeType() {
		List<BridgeType> bridgeTypeList = bridgeTypeDao.findAll();
		if(!bridgeTypeList.isEmpty()) {
			String json = JSONArray.toJSONString(bridgeTypeList);
			if(this.propertyPath==null || this.propertyPath.equals("")) {
				throw new BusinessException("请设置文件路径");
			}
			FileUtil.fileWrite(this.propertyPath+"/bridge_type.json", json);
		}
	}
	
	/**
	 * 创建桥梁病害
	 */
	public void createBridgeDisease() {
		List<BridgeDisease> bridgeDiseaseList = bridgeDiseaseDao.findAll();
		if(!bridgeDiseaseList.isEmpty()) {
			String json = JSONArray.toJSONString(bridgeDiseaseList);
			if(this.propertyPath==null || this.propertyPath.equals("")) {
				throw new BusinessException("请设置文件路径");
			}
			FileUtil.fileWrite(this.propertyPath+"/bridge_disease.json", json);
		}
	}
	
	/**
	 * 创建桥梁部件
	 */
	public void createComponent() {
		List<Component> componentList = componentDao.findAll();
		if(!componentList.isEmpty()) {
			String json = JSONArray.toJSONString(componentList);
			if(this.propertyPath==null || this.propertyPath.equals("")) {
				throw new BusinessException("请设置文件路径");
			}
			FileUtil.fileWrite(this.propertyPath+"/component.json", json);
		}
	}
	
	/**
	 * 创建桥梁类型部件关联树形
	 */
	public void createBridgeTypeComponent() {
		List<BridgeTypeComponentVo> list = appPropertiesDao.getBridgeTypeComponentAllList();
		if(!list.isEmpty()) {
			String json = JSONArray.toJSONString(list);
			if(this.propertyPath==null || this.propertyPath.equals("")) {
				throw new BusinessException("请设置文件路径");
			}
			FileUtil.fileWrite(this.propertyPath+"/bridge_type_component.json", json);
		}
	}
	
	/**
	 * 创建桥梁部件病害关联树形
	 */
	public void createComponentDisease() {
		 List<ComponentDiseaseVo> list = appPropertiesDao.getComponentDiseaseAllList();
		if(!list.isEmpty()) {
			String json = JSONArray.toJSONString(list);
			if(this.propertyPath==null || this.propertyPath.equals("")) {
				throw new BusinessException("请设置文件路径");
			}
			FileUtil.fileWrite(this.propertyPath+"/bridge_component_disease.json", json);
		}
	}
	
//	/**
//	 * 创建属性组选项关联树形
//	 */
//	public void createPropertiesGroupOptions() {
//		
//	}
	
	
	/**
	 * 创建属性
	 */
	public void createProperties() {
		List<Property> list = propertyDao.findAll();
		if(!list.isEmpty()) {
			String json = JSONArray.toJSONString(list);
			if(this.propertyPath==null || this.propertyPath.equals("")) {
				throw new BusinessException("请设置文件路径");
			}
			FileUtil.fileWrite(this.propertyPath+"/property.json", json);
		}
	}
	/**
	 * 创建属性组
	 */
	public void createPropertyGroup() {
		List<PropertyGroup> list = propertyGroupDao.findAll();
		if(!list.isEmpty()) {
			String json = JSONArray.toJSONString(list);
			if(this.propertyPath==null || this.propertyPath.equals("")) {
				throw new BusinessException("请设置文件路径");
			}
			FileUtil.fileWrite(this.propertyPath+"/property_group.json", json);
		}
		
	}
	
	/**
	 * 创建属性组选项关联
	 */
	public void createPropertyGroupOptionRel() {
		
		List<PropertyGroupOptionRel> list = propertyGroupOptionRelDao.findAll();
		if(!list.isEmpty()) {
			String json = JSONArray.toJSONString(list);
			if(this.propertyPath==null || this.propertyPath.equals("")) {
				throw new BusinessException("请设置文件路径");
			}
			FileUtil.fileWrite(this.propertyPath+"/property_group_option_rel.json", json);
		}
	}
	
	/**
	 * 创建属性选项
	 */
	public void createPropertyOption() {
		List<PropertyOption> list = propertyOptionDao.findAll();
		if(!list.isEmpty()) {
			String json = JSONArray.toJSONString(list);
			if(this.propertyPath==null || this.propertyPath.equals("")) {
				throw new BusinessException("请设置文件路径");
			}
			FileUtil.fileWrite(this.propertyPath+"/property_option.json", json);
		}
	}
	
	/**
	 * 创建选项属性关联表
	 */
	public void createPropertyOptionRel() {
		List<PropertyOptionRel> list = propertyOptionRelDao.findAll();
		if(!list.isEmpty()) {
			String json = JSONArray.toJSONString(list);
			if(this.propertyPath==null || this.propertyPath.equals("")) {
				throw new BusinessException("请设置文件路径");
			}
			FileUtil.fileWrite(this.propertyPath+"/property_option_rel.json", json);
		}
	}
	/**
	 * 创建属性关联表
	 */
	public void createPropertyRel() {
		List<PropertyRel> list = propertyRelDao.findAll();
		if(!list.isEmpty()) {
			String json = JSONArray.toJSONString(list);
			if(this.propertyPath==null || this.propertyPath.equals("")) {
				throw new BusinessException("请设置文件路径");
			}
			FileUtil.fileWrite(this.propertyPath+"/property_rel.json", json);
		}
	}

	@Override
	public void createBridgeTypeProperty() {
		// TODO Auto-generated method stub
		 List<BridgeTypeVo> list = appPropertiesDao.getBridgeTypeOptionsList();
		if(!list.isEmpty()) {
			String json = JSONArray.toJSONString(list);
			if(this.propertyPath==null || this.propertyPath.equals("")) {
				throw new BusinessException("请设置文件路径");
			}
			FileUtil.fileWrite(this.propertyPath+"/bridge_type_property.json", json);
		}
	}

	@Override
	public void createBridgeTypeAndComponent() {
		// TODO Auto-generated method stub
		List<BridgeTypeVo> list = appPropertiesDao.getBridgeTypeAndComponentAllList();
		if(!list.isEmpty()) {
			String json = JSONArray.toJSONString(list);
			if(this.propertyPath==null || this.propertyPath.equals("")) {
				throw new BusinessException("请设置文件路径");
			}
			FileUtil.fileWrite(this.propertyPath+"/bridge_type_and_component.json", json);
		}
	}

	@Override
	public String runBuild() {
		// TODO Auto-generated method stub
		deleteDir(new File(this.propertyPath));
		this.createBridgeDisease();
		this.createBridgeType();
		this.createBridgeTypeAndComponent();
		this.createBridgeTypeComponent();
		this.createBridgeTypeProperty();
		this.createComponent();
		this.createComponentDisease();
		this.createProperties();
		this.createPropertyGroup();
		this.createPropertyGroupOptionRel();
		this.createPropertyOption();
		this.createPropertyOptionRel();
		this.createPropertyRel();
		if(this.propertyPath==null || this.propertyPath.equals("")) {
			throw new BusinessException("请设置文件路径");
		}
		InZipUntil ziputil = new InZipUntil();
		String zipPath = this.propertyPath.substring(0,this.propertyPath.length()-1)+".zip";
		File zipFile = new File(zipPath);
		if(zipFile.exists()) {
			zipFile.delete();
		}
		try {
			ziputil.inZip(this.propertyPath,zipFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return zipPath;
	}
	/**
	 * 删除目录
	 * @param dirFile
	 */
	private void deleteDir(File dirFile) {
		if(dirFile.exists()) {
			if(dirFile.isDirectory()) {
				File[] files = dirFile.listFiles();
				for(File file :files) {
					deleteDir(file);
				}
				dirFile.delete();
			}else {
				dirFile.delete();
			}
		}
		
	}
}
