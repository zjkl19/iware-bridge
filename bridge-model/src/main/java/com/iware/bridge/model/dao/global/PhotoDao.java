package com.iware.bridge.model.dao.global;

import com.iware.bridge.model.entity.global.Photo;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * PhotoDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface PhotoDao {

	/** 保存 */
	public void save(Photo photo);

	/** 根据ID删除Photo */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(Photo photo);

	/** 根据ID查询Photo */
	Photo findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<Photo> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<Photo> findAll();

    /** 根据条件查询 */
    public List<Photo> query(Photo photo);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<Photo> queryByLike(Photo photo);

}
