package com.iware.bridge.model.dao.evaluation;

import com.iware.bridge.model.entity.evaluation.MonitorPlan;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * MonitorPlanDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface MonitorPlanDao {

	/** 保存 */
	public void save(MonitorPlan monitorPlan);

	/** 根据ID删除MonitorPlan */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(MonitorPlan monitorPlan);

	/** 根据ID查询MonitorPlan */
	MonitorPlan findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<MonitorPlan> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<MonitorPlan> findAll();

    /** 根据条件查询 */
    public List<MonitorPlan> query(MonitorPlan monitorPlan);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<MonitorPlan> queryByLike(MonitorPlan monitorPlan);

}
