package com.iware.bridge.info.service;

import com.iware.bridge.info.dao.ProjectExpDao;
import com.iware.bridge.info.dao.StructureExpDao;
import com.iware.bridge.model.dao.global.VideoDao;
import com.iware.bridge.model.dao.online.MeansPointDao;
import com.iware.bridge.model.entity.global.Project;
import com.iware.bridge.model.entity.global.Structure;
import com.iware.bridge.model.entity.global.Video;
import com.iware.bridge.model.entity.online.MeansPoint;
import com.iware.common.enums.GlobalResultEnum;
import com.iware.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class StructureServiceImpl implements StructureService {

    @Autowired
    private StructureExpDao structureExpDao;
    @Autowired
    private ProjectExpDao projectExpDao;
    @Autowired
    private MeansPointDao meansPointDao;
    @Autowired
    private VideoDao videoDao;

    public void checkStructureRel(Integer structureId) {

        List<Project> projectList = projectExpDao.getProjectByStructureId(structureId);
        if(!CollectionUtils.isEmpty(projectList)) {
            throw new BusinessException("该结构物有所属的项目,请先移出项目！");
        }

        MeansPoint meansPointQry = new MeansPoint();
        meansPointQry.setStructureId(structureId);
        List<MeansPoint> meansPointList = meansPointDao.query(meansPointQry);

        if (!CollectionUtils.isEmpty(meansPointList)) {
            throw new BusinessException("该结构物下存在测点信息,请先移除测点！");
        }

        Video videoQry = new Video();
        videoQry.setStructureId(structureId);
        List<Video> videoList = videoDao.query(videoQry);
        if (!CollectionUtils.isEmpty(videoList)) {
            throw new BusinessException("该结构物下存在视频设备,请先移除视频设备！");
        }
    }

    /** 检查是否存在同名、同编号结构物 */
    public void checkStructureExist(Structure structure) {
        String structureName = structureExpDao.getStructureName(structure.getName(),structure.getId());
        if(!StringUtils.isEmpty(structureName)) {
            throw new BusinessException(GlobalResultEnum.STRUCTURE_NAME_EXIST_ERROR);
        }
        String structureCode = structureExpDao.getStructureCode(structure.getCode(),structure.getId());
        if(!StringUtils.isEmpty(structureCode)) {
            throw new BusinessException(GlobalResultEnum.STRUCTURE_CODE_EXIST_ERROR);
        }
    }
}
