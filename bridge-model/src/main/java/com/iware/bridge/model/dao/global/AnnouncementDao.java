package com.iware.bridge.model.dao.global;

import com.iware.bridge.model.entity.global.Announcement;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * AnnouncementDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface AnnouncementDao {

	/** 保存 */
	public void save(Announcement announcement);

	/** 根据ID删除Announcement */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(Announcement announcement);

	/** 根据ID查询Announcement */
	Announcement findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<Announcement> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<Announcement> findAll();

    /** 根据条件查询 */
    public List<Announcement> query(Announcement announcement);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<Announcement> queryByLike(Announcement announcement);

}
