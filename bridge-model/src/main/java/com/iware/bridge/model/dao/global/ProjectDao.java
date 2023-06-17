package com.iware.bridge.model.dao.global;

import com.iware.bridge.model.entity.global.Project;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * ProjectDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface ProjectDao {

	/** 保存 */
	public void save(Project project);

	/** 根据ID删除Project */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(Project project);

	/** 根据ID查询Project */
	Project findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<Project> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<Project> findAll();

    /** 根据条件查询 */
    public List<Project> query(Project project);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<Project> queryByLike(Project project);

}
