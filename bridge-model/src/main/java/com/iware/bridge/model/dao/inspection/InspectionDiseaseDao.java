package com.iware.bridge.model.dao.inspection;

import com.iware.bridge.model.entity.inspection.InspectionDisease;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * InspectionDiseaseDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface InspectionDiseaseDao {

	/** 保存 */
	public void save(InspectionDisease inspectionDisease);

	/** 根据ID删除InspectionDisease */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(InspectionDisease inspectionDisease);

	/** 根据ID查询InspectionDisease */
	InspectionDisease findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<InspectionDisease> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<InspectionDisease> findAll();

    /** 根据条件查询 */
    public List<InspectionDisease> query(InspectionDisease inspectionDisease);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<InspectionDisease> queryByLike(InspectionDisease inspectionDisease);

}
