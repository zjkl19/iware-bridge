package com.iware.bridge.model.dao.online;

import com.iware.bridge.model.entity.online.Company;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * CompanyDAO
 *
 * @author code-generation
 * @date 2021-7-21 17:00:00
 * @version 1.3.1
 */
@Repository
public interface CompanyDao {

	/** 保存 */
	public void save(Company company);

	/** 根据ID删除Company */
	public void deleteById(@Param("id") Integer id);

	/** 更新 */
	public void update(Company company);

	/** 根据ID查询Company */
	Company findById(@Param("id") Integer id);

	/** 批量添加 */
	public void batchSave(@Param("list") List<Company> list);

	/** 批量删除 */
	public void batchDelete(@Param("ids") List<Integer> ids);

	/** 查询所有 */
	public List<Company> findAll();

    /** 根据条件查询 */
    public List<Company> query(Company company);

    /** 根据条件模糊查询（id、create_time、modify_time不做模糊查询） */
    public List<Company> queryByLike(Company company);

}
