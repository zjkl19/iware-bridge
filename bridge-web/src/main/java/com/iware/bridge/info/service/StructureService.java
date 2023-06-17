package com.iware.bridge.info.service;

import com.iware.bridge.model.entity.global.Structure;

public interface StructureService {

    /** 检查结构物是否存在关联 **/
    public void checkStructureRel(Integer structureId);

    /** 检查结构物是否存在关联 **/
    public void checkStructureExist(Structure structure);
}
