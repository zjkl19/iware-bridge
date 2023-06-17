package com.iware.bridge.model.dao.online;

import com.iware.bridge.model.entity.online.MeansPoint;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * MeansPointDAO
 *
 * @author code-generation
 * @since 2021-10-19 17:00:00
 * @version 1.3.3
 */
@Repository
public interface MeansPointDao {

	/** 保存 */
	public void save(MeansPoint meansPoint);

	/** 根据ID删除MeansPoint */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(MeansPoint meansPoint);

	/** 根据ID查询MeansPoint */
	MeansPoint findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<MeansPoint> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<MeansPoint> findAll();

    /** 根据条件查询 */
    public List<MeansPoint> query(MeansPoint meansPoint);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<MeansPoint> queryByLike(MeansPoint meansPoint);

}
