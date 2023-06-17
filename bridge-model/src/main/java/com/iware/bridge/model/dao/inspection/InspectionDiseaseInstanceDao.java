package com.iware.bridge.model.dao.inspection;

import com.iware.bridge.model.entity.inspection.InspectionDiseaseInstance;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * InspectionDiseaseInstanceDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface InspectionDiseaseInstanceDao {

	/** 保存 */
	public void save(InspectionDiseaseInstance inspectionDiseaseInstance);

	/** 根据ID删除InspectionDiseaseInstance */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(InspectionDiseaseInstance inspectionDiseaseInstance);

	/** 根据ID查询InspectionDiseaseInstance */
	InspectionDiseaseInstance findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<InspectionDiseaseInstance> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<InspectionDiseaseInstance> findAll();

    /** 根据条件查询 */
    public List<InspectionDiseaseInstance> query(InspectionDiseaseInstance inspectionDiseaseInstance);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<InspectionDiseaseInstance> queryByLike(InspectionDiseaseInstance inspectionDiseaseInstance);

}
