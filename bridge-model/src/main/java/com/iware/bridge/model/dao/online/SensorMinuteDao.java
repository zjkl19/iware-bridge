package com.iware.bridge.model.dao.online;

import com.iware.bridge.model.entity.online.SensorMinute;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * SensorMinuteDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface SensorMinuteDao {

	/** 保存 */
	public void save(SensorMinute sensorMinute);

	/** 批量添加 */
	public void batchSave(@Param("list") List<SensorMinute> list);

	/** 查询所有 */
	public List<SensorMinute> findAll();

    /** 根据条件查询 */
    public List<SensorMinute> query(SensorMinute sensorMinute);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<SensorMinute> queryByLike(SensorMinute sensorMinute);

}
