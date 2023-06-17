package com.iware.bridge.model.dao.online;

import com.iware.bridge.model.entity.online.SensorHour;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * SensorHourDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface SensorHourDao {

	/** 保存 */
	public void save(SensorHour sensorHour);

	/** 批量添加 */
	public void batchSave(@Param("list") List<SensorHour> list);

	/** 查询所有 */
	public List<SensorHour> findAll();

    /** 根据条件查询 */
    public List<SensorHour> query(SensorHour sensorHour);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<SensorHour> queryByLike(SensorHour sensorHour);

}
