package com.iware.bridge.model.dao.user;

import com.iware.bridge.model.entity.user.Unit;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * UnitDAO
 *
 * @author code-generation
 * @since 2021-10-19 17:00:00
 * @version 1.3.3
 */
@Repository
public interface UnitDao {

	/** 保存 */
	public void save(Unit unit);

	/** 根据ID删除Unit */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(Unit unit);

	/** 根据ID查询Unit */
	Unit findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<Unit> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<Unit> findAll();

    /** 根据条件查询 */
    public List<Unit> query(Unit unit);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<Unit> queryByLike(Unit unit);

}
