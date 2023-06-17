package com.iware.bridge.model.dao.evaluation;

import com.iware.bridge.model.entity.evaluation.DiseaseInstanceRecord;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * DiseaseInstanceRecordDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface DiseaseInstanceRecordDao {

	/** 保存 */
	public void save(DiseaseInstanceRecord diseaseInstanceRecord);

	/** 根据ID删除DiseaseInstanceRecord */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(DiseaseInstanceRecord diseaseInstanceRecord);

	/** 根据ID查询DiseaseInstanceRecord */
	DiseaseInstanceRecord findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<DiseaseInstanceRecord> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<DiseaseInstanceRecord> findAll();

    /** 根据条件查询 */
    public List<DiseaseInstanceRecord> query(DiseaseInstanceRecord diseaseInstanceRecord);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<DiseaseInstanceRecord> queryByLike(DiseaseInstanceRecord diseaseInstanceRecord);

}
