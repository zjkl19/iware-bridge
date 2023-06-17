package com.iware.bridge.model.dao.inspection;

import com.iware.bridge.model.entity.inspection.InspectionDiseaseOption;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * InspectionDiseaseOptionDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface InspectionDiseaseOptionDao {

	/** 保存 */
	public void save(InspectionDiseaseOption inspectionDiseaseOption);

	/** 根据ID删除InspectionDiseaseOption */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(InspectionDiseaseOption inspectionDiseaseOption);

	/** 根据ID查询InspectionDiseaseOption */
	InspectionDiseaseOption findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<InspectionDiseaseOption> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<InspectionDiseaseOption> findAll();

    /** 根据条件查询 */
    public List<InspectionDiseaseOption> query(InspectionDiseaseOption inspectionDiseaseOption);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<InspectionDiseaseOption> queryByLike(InspectionDiseaseOption inspectionDiseaseOption);

}
