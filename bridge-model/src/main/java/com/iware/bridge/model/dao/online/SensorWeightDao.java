package com.iware.bridge.model.dao.online;

import com.iware.bridge.model.entity.online.SensorWeight;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * SensorWeightDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface SensorWeightDao {

	/** 保存 */
	public void save(SensorWeight sensorWeight);

	/** 批量添加 */
	public void batchSave(@Param("list") List<SensorWeight> list);

	/** 查询所有 */
	public List<SensorWeight> findAll();

    /** 根据条件查询 */
    public List<SensorWeight> query(SensorWeight sensorWeight);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<SensorWeight> queryByLike(SensorWeight sensorWeight);

}
