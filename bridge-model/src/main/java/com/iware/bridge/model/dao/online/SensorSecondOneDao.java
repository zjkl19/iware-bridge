package com.iware.bridge.model.dao.online;

import com.iware.bridge.model.entity.online.SensorSecondOne;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * SensorSecondOneDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface SensorSecondOneDao {

	/** 保存 */
	public void save(SensorSecondOne sensorSecondOne);

	/** 批量添加 */
	public void batchSave(@Param("list") List<SensorSecondOne> list);

	/** 查询所有 */
	public List<SensorSecondOne> findAll();

    /** 根据条件查询 */
    public List<SensorSecondOne> query(SensorSecondOne sensorSecondOne);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<SensorSecondOne> queryByLike(SensorSecondOne sensorSecondOne);

}
