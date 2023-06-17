package com.iware.bridge.model.dao.user;

import com.iware.bridge.model.entity.user.AppRole;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * AppRoleDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface AppRoleDao {

	/** 保存 */
	public void save(AppRole appRole);

	/** 根据ID删除AppRole */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(AppRole appRole);

	/** 根据ID查询AppRole */
	AppRole findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<AppRole> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<AppRole> findAll();

    /** 根据条件查询 */
    public List<AppRole> query(AppRole appRole);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<AppRole> queryByLike(AppRole appRole);

}
