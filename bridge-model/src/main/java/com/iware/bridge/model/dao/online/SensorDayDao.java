package com.iware.bridge.model.dao.online;

import com.iware.bridge.model.entity.online.SensorDay;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * SensorDayDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface SensorDayDao {

	/** 保存 */
	public void save(SensorDay sensorDay);

	/** 批量添加 */
	public void batchSave(@Param("list") List<SensorDay> list);

	/** 查询所有 */
	public List<SensorDay> findAll();

    /** 根据条件查询 */
    public List<SensorDay> query(SensorDay sensorDay);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<SensorDay> queryByLike(SensorDay sensorDay);

}
