package com.iware.bridge.model.dao.inspection;

import com.iware.bridge.model.entity.inspection.MaintainItem;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * MaintainItemDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface MaintainItemDao {

	/** 保存 */
	public void save(MaintainItem maintainItem);

	/** 根据ID删除MaintainItem */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(MaintainItem maintainItem);

	/** 根据ID查询MaintainItem */
	MaintainItem findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<MaintainItem> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<MaintainItem> findAll();

    /** 根据条件查询 */
    public List<MaintainItem> query(MaintainItem maintainItem);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<MaintainItem> queryByLike(MaintainItem maintainItem);

}
