package com.iware.bridge.model.dao.global;

import com.iware.bridge.model.entity.global.Report;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * ReportDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface ReportDao {

	/** 保存 */
	public void save(Report report);

	/** 根据ID删除Report */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(Report report);

	/** 根据ID查询Report */
	Report findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<Report> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<Report> findAll();

    /** 根据条件查询 */
    public List<Report> query(Report report);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<Report> queryByLike(Report report);

}
