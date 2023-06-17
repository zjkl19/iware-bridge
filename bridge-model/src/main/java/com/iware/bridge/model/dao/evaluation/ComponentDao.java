package com.iware.bridge.model.dao.evaluation;

import com.iware.bridge.model.entity.evaluation.Component;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * ComponentDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface ComponentDao {

	/** 保存 */
	public void save(Component component);

	/** 根据ID删除Component */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(Component component);

	/** 根据ID查询Component */
	Component findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<Component> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<Component> findAll();

    /** 根据条件查询 */
    public List<Component> query(Component component);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<Component> queryByLike(Component component);

}
