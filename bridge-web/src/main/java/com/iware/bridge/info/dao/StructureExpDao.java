package com.iware.bridge.info.dao;

import com.iware.bridge.info.vo.InfoFilter;
import com.iware.bridge.info.vo.StructureVO;
import com.iware.bridge.model.entity.global.BridgeInfo;
import com.iware.bridge.model.entity.global.Structure;
import com.iware.bridge.model.entity.global.TunnelInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ZRB
 * @date 2021-7-23
 */

@Repository
public interface StructureExpDao {

    /** 获取结构物列表 */
    public List<Structure> getStructureList(@Param("roleId") Integer roleId,
                                            @Param("unitId") Integer unitId,
                                            @Param("filter") InfoFilter filter);

    /** 根据项目id获取结构物 **/
    public List<Structure> getStructureByProjectId(@Param("projectId") Integer projectId);

    /** 获取同名结构物 */
    public String getStructureName(@Param("name") String name,
                                   @Param("id") Integer id);

    /** 判断相同的结构物代码 */
    public String getStructureCode(@Param("code") String code,
                                   @Param("id") Integer id);

    /** 更新结构物数据 */
    public void update(Structure structure);

    /** 更新结构物数据 */
    public void updateBridgeDetail(BridgeInfo bridgeInfo);

    /** 更新结构物数据 */
    public void updateTunnelDetail(TunnelInfo tunnelInfo);

    /** 删除一条桥梁的详细信息 */
    public void deleteBridgeInfo(@Param("structureId") Integer structureId);

    /** 删除一条隧道的详细信息 */
    public void deleteTunnelInfo(@Param("structureId") Integer structureId);

    /** 根据项目列表获取结构物列表 */
    public List<Structure> getStructureByProjectIds(@Param("list") List<Integer> projectIds);

    /** 获取承接单位的结构物项目关联列表 **/
    public List<Structure> getStructureCJ(@Param("unitId") Integer unitId, @Param("powerId") Integer powerId);

    /** 获取业主的结构物项目关联列表 **/
    public List<Structure> getStructureYZ(@Param("unitId") Integer unitId);

    /** 获取结构物信息 */
    public Structure findById(@Param("structureId") Integer structureId);
}
