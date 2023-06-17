package com.iware.bridge.model.dao.global;

import com.iware.bridge.model.entity.global.Area;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * AreaDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface AreaDao {

	/** 保存 */
	public void save(Area area);

	/** 根据ID删除Area */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(Area area);

	/** 根据ID查询Area */
	Area findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<Area> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<Area> findAll();

    /** 根据条件查询 */
    public List<Area> query(Area area);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<Area> queryByLike(Area area);

}
