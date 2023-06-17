package com.iware.bridge.model.dao.global;

import com.iware.bridge.model.entity.global.ProjectStructureRel;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * ProjectStructureRelDAO
 *
 * @author code-generation
 * @since 2021-10-19 17:00:00
 * @version 1.3.3
 */
@Repository
public interface ProjectStructureRelDao {

	/** 保存 */
	public void save(ProjectStructureRel projectStructureRel);

	/** 根据ID删除ProjectStructureRel */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(ProjectStructureRel projectStructureRel);

	/** 根据ID查询ProjectStructureRel */
	ProjectStructureRel findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<ProjectStructureRel> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<ProjectStructureRel> findAll();

    /** 根据条件查询 */
    public List<ProjectStructureRel> query(ProjectStructureRel projectStructureRel);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<ProjectStructureRel> queryByLike(ProjectStructureRel projectStructureRel);

}
