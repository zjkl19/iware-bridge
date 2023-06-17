package com.iware.bridge.model.dao.online;

import com.iware.bridge.model.entity.online.SensorPrinciple;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * SensorPrincipleDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface SensorPrincipleDao {

	/** 保存 */
	public void save(SensorPrinciple sensorPrinciple);

	/** 根据ID删除SensorPrinciple */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(SensorPrinciple sensorPrinciple);

	/** 根据ID查询SensorPrinciple */
	SensorPrinciple findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<SensorPrinciple> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<SensorPrinciple> findAll();

    /** 根据条件查询 */
    public List<SensorPrinciple> query(SensorPrinciple sensorPrinciple);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<SensorPrinciple> queryByLike(SensorPrinciple sensorPrinciple);

}
