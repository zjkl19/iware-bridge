package com.iware.bridge.evaluation.service;

import com.iware.bridge.evaluation.Utils.BCIRecordUtils;
import com.iware.bridge.evaluation.dao.BridgeExpDao;
import com.iware.bridge.evaluation.dao.MonitorPlanExpDao;
import com.iware.bridge.evaluation.dao.MonitorPlanOriginalRecordExpDao;
import com.iware.bridge.evaluation.dao.RecordDetailInfoDao;
import com.iware.bridge.evaluation.enums.BridgeTypeEnum;
import com.iware.bridge.evaluation.enums.RecordConstant;
import com.iware.bridge.evaluation.enums.TableNameEnum;
import com.iware.bridge.evaluation.vo.*;
import com.iware.bridge.model.dao.user.UnitDao;
import com.iware.bridge.model.dao.user.UserDao;
import com.iware.bridge.model.entity.user.Unit;
import com.iware.bridge.model.entity.user.User;
import com.iware.common.data.ThreadLocalMap;
import com.iware.common.exception.BusinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author WJP
 * @date 2021-8-16
 */
@Service
public class EvaluationRecordBciServiceImpl implements EvaluationRecordBciService {

    @Autowired
    private RecordDetailInfoDao recordDetailInfoDao;

    @Autowired
    private MonitorPlanOriginalRecordExpDao monitorPlanOriginalRecordExpDao;

    @Autowired
    private UserDao userDao;
    @Autowired
    private BridgeExpDao bridgeExpDao;
    @Autowired
    private EvaluationRecordService evaluationRecordService;
    @Autowired
    private MonitorPlanExpDao monitorPlanExpDao;
    @Autowired
    private UnitDao unitDao;

    /**
     * 补充桥面系的构件
     *
     * @param list 构件集合
     */
    public void supArtifactsDTO(List<ArtifactsDTO> list) {
        if (list.size() > 0) {
            Long bridgeTypeId = list.get(0).getBridgeTypeId();
            List<String> contains = new ArrayList<>();
            for (ArtifactsDTO artifactsDTO : list) {
                contains.add(artifactsDTO.getArtifactsName());
            }
            List<ElementWeightDTO> allArtifacts;
            if (RecordConstant.footbridge.contains(Integer.valueOf(bridgeTypeId.toString()))) {
                allArtifacts = RecordConstant.PEDESTRIAN_BRIDGE;
            } else {
                allArtifacts = RecordConstant.BRAM_BRIDGE;
            }
            for (ElementWeightDTO weightDTO : allArtifacts) {
                String s = weightDTO.getElementName();
                if (!contains.contains(s)) {
                    ArtifactsDTO artifactsDTO = new ArtifactsDTO();
                    artifactsDTO.setArtifactsName(s);
                    artifactsDTO.setInitialWeight(weightDTO.getWeightNum());
                    artifactsDTO.setActualWeight(0.0);
                    artifactsDTO.setDeduct(0.0);
                    artifactsDTO.setSerious(0);
                    list.add(artifactsDTO);
                }
            }
        }
    }

    /**
     * 补充上部结构的构件
     *
     * @param list 构件集合
     */
    public void supDto(List<AcrossDTO> list) {
        if (list.size() > 0) {
            Integer bridgeType = list.get(0).getBridgeTypeId();
            BridgeTypeEnum bridgeTypeEnum = BridgeTypeEnum.valueOf("UPPER_" + bridgeType);
            for (AcrossDTO acrossDTO : list) {
                List<ArtifactsDTO> artifactsDTOList = acrossDTO.getArtifactsDTOList();
                if (artifactsDTOList.size() == 0) {
                    continue;
                }
                List<String> cons = new ArrayList<>();
                for (ArtifactsDTO artifactsDTO : artifactsDTOList) {
                    cons.add(artifactsDTO.getArtifactsName());
                }
                for (ElementWeightDTO elementWeightDTO : bridgeTypeEnum.getWeightDTO()) {
                    if (!cons.contains(elementWeightDTO.getElementName())) {
                        ArtifactsDTO newArtifactsDTO = new ArtifactsDTO();
                        newArtifactsDTO.setDeduct(0.0);
                        newArtifactsDTO.setInitialWeight(elementWeightDTO.getWeightNum());
                        newArtifactsDTO.setActualWeight(0.0);
                        newArtifactsDTO.setSerious(0);
                        newArtifactsDTO.setArtifactsName(elementWeightDTO.getElementName());
                        acrossDTO.getArtifactsDTOList().add(newArtifactsDTO);
                    }
                }
            }
        }
    }

    /**
     * 补充下部结构的构件
     *
     * @param list 构件集合
     */
    public void subDto(List<AcrossDTO> list) {
        if (list.size() > 0) {
            Integer bridgeType = list.get(0).getBridgeTypeId();
            for (int i = 0; i < list.size(); i++) {
                AcrossDTO acrossDTO = list.get(i);
                if (acrossDTO.getArtifactsDTOList().size() == 0) {
                    continue;
                }
                Integer isCode = list.get(i).getIsCode();
                BridgeTypeEnum bridgeTypeEnum = BridgeTypeEnum.valueOf("LOWER_" + bridgeType + "_" + isCode);
                List<ArtifactsDTO> artifactsDTOList = acrossDTO.getArtifactsDTOList();
                List<String> cons = new ArrayList<>();
                for (ArtifactsDTO artifactsDTO : artifactsDTOList) {
                    cons.add(artifactsDTO.getArtifactsName());
                }
                for (ElementWeightDTO elementWeightDTO : bridgeTypeEnum.getWeightDTO()) {
                    if (!cons.contains(elementWeightDTO.getElementName())) {
                        ArtifactsDTO newArtifactsDTO = new ArtifactsDTO();
                        newArtifactsDTO.setDeduct(0.0);
                        newArtifactsDTO.setInitialWeight(elementWeightDTO.getWeightNum());
                        newArtifactsDTO.setActualWeight(0.0);
                        newArtifactsDTO.setSerious(0);
                        newArtifactsDTO.setArtifactsName(elementWeightDTO.getElementName());
                        acrossDTO.getArtifactsDTOList().add(newArtifactsDTO);
                    }
                }
            }
        }

    }

    /**
     * BCI评价详情
     *
     * @param bciDetailVO 参数
     * @return
     */
    @Override
    public BCIEvaluationDetailDTO selBCIEvaluationDetail(BCIDetailVO bciDetailVO) {
        if (bciDetailVO == null) {
            throw new BusinessException("参数为空！！！");
        }
        BCIEvaluationDetailDTO result = new BCIEvaluationDetailDTO();
        //BCI评价结果
        BCIEvaluationResultDTO bciEvaluationResultDTO = recordDetailInfoDao.selBCIEvaluationResultDTO(bciDetailVO.getRoadId());
        //桥面系
        FloorSystemDTO floorSystemDTO = new FloorSystemDTO();
        List<ArtifactsDTO> artifactsDTOS = recordDetailInfoDao.selArtifactsDTO(bciDetailVO);
        //合并跨下的所有桥面系要素(合并要素中的病害)
        Map<Long, ArtifactsDTO> map = mergeArtifactsDTO(artifactsDTOS);
        List<ArtifactsDTO> list = new ArrayList<>(map.values());
        supArtifactsDTO(list); //补充桥面系的构件
        floorSystemDTO.setArtifactsDTOList(list);
        BCIRecordUtils.couFloorSystem(floorSystemDTO); //计算桥面系BCI
        //上部结构
        UpperStructureDTO upperStructureDTO = new UpperStructureDTO();
        List<AcrossDTO> acrossDTOSUpper = recordDetailInfoDao.selAcrossDTOByUpper(bciDetailVO);
        supDto(acrossDTOSUpper);
        upperStructureDTO.setAcrossDTOList(acrossDTOSUpper);
        //下部结构
        LowerStructureDTO lowerStructureDTO = new LowerStructureDTO();
        List<AcrossDTO> acrossDTOSLower = recordDetailInfoDao.selAcrossDTOByLower(bciDetailVO);
        subDto(acrossDTOSLower);
        lowerStructureDTO.setAcrossDTOList(acrossDTOSLower);

        result.setFloorSystemDTO(floorSystemDTO);
        result.setUpperStructureDTO(upperStructureDTO);
        result.setLowerStructureDTO(lowerStructureDTO);
        result.setBciEvaluationResultDTO(bciEvaluationResultDTO);
        result.setSpanNumber(bciEvaluationResultDTO.getSpanNumber());
        if (bciEvaluationResultDTO != null) {
            result.setBridgeTypeId(bciEvaluationResultDTO.getBridgeTypeId()); //桥梁类型
            BCIRecordUtils.couBCIEvaluationDetail(result); //计算线路BCI
            bciEvaluationResultDTO.setRoadScore(result.getBCIScore()); //线路评分
            bciEvaluationResultDTO.setBridgeBCIm(floorSystemDTO.getBCIm()); //桥面系BCIm
            bciEvaluationResultDTO.setBridgeBSIm(floorSystemDTO.getBSIm()); //桥面系BSIm
            UpperStructureDTO resultUpper = result.getUpperStructureDTO();
            LowerStructureDTO resultLower = result.getLowerStructureDTO();
            if (resultUpper != null) {
                bciEvaluationResultDTO.setUpperStructureBCIs(upperStructureDTO.getBCIs()); //上部结构BCIs
                bciEvaluationResultDTO.setUpperStructureBSIs(upperStructureDTO.getBSIs()); //上部结构BSIs
            }
            if (resultLower != null) {
                bciEvaluationResultDTO.setLowerStructureBCIx(lowerStructureDTO.getBCIx()); //下部结构BCIx
                bciEvaluationResultDTO.setLowerStructureBSIx(lowerStructureDTO.getBSIx()); //下部结构BSIx
            }
//            saveOrUpdateRodeBCI(bciEvaluationResultDTO, result);
        }
        result.setBciEvaluationResultDTO(bciEvaluationResultDTO);
        if (existRecord(bciDetailVO.getRoadId()) > 0) {
            result.setEvaluateFlag(true);
        } else {
            result.setEvaluateFlag(false);
        }

        DecimalFormat df = new DecimalFormat("######0.00");
        if (result.getBciEvaluationResultDTO().getRoadScore() != null) {
            result.getBciEvaluationResultDTO().setRoadScore(Double.parseDouble(df.format(result.getBciEvaluationResultDTO().getRoadScore())));
        }
        if (result.getBCIScore() != null) {
            result.setBCIScore(Double.parseDouble(df.format(result.getBCIScore())));
        }
        if (result.getBSIScore() != null) {
            result.setBSIScore(Double.parseDouble(df.format(result.getBSIScore())));
        }
        if (result.getFloorSystemDTO().getBCIm() != null) {
            result.getFloorSystemDTO().setBCIm(Double.parseDouble(df.format(result.getFloorSystemDTO().getBCIm())));
        }
        if (result.getFloorSystemDTO().getBSIm() != null) {
            result.getFloorSystemDTO().setBSIm(Double.parseDouble(df.format(result.getFloorSystemDTO().getBSIm())));
        }
        if (result.getUpperStructureDTO().getBCIs() != null) {
            result.getUpperStructureDTO().setBCIs(Double.parseDouble(df.format(result.getUpperStructureDTO().getBCIs())));
        }
        if (result.getUpperStructureDTO().getBSIs() != null) {
            result.getUpperStructureDTO().setBSIs(Double.parseDouble(df.format(result.getUpperStructureDTO().getBSIs())));
        }
        if (result.getLowerStructureDTO().getBCIx() != null) {
            result.getLowerStructureDTO().setBCIx(Double.parseDouble(df.format(result.getLowerStructureDTO().getBCIx())));
        }
        if (result.getLowerStructureDTO().getBSIx() != null) {
            result.getLowerStructureDTO().setBSIx(Double.parseDouble(df.format(result.getLowerStructureDTO().getBSIx())));
        }
        if (result.getBciEvaluationResultDTO().getBridgeBCIm() != null) {
            result.getBciEvaluationResultDTO().setBridgeBCIm(Double.parseDouble(df.format(result.getBciEvaluationResultDTO().getBridgeBCIm())));
        }
        if (result.getBciEvaluationResultDTO().getBridgeBSIm() != null) {
            result.getBciEvaluationResultDTO().setBridgeBSIm(Double.parseDouble(df.format(result.getBciEvaluationResultDTO().getBridgeBSIm())));
        }
        if (result.getBciEvaluationResultDTO().getLowerStructureBCIx() != null) {
            result.getBciEvaluationResultDTO().setLowerStructureBCIx(Double.parseDouble(df.format(result.getBciEvaluationResultDTO().getLowerStructureBCIx())));
        }
        if (result.getBciEvaluationResultDTO().getLowerStructureBSIx() != null) {
            result.getBciEvaluationResultDTO().setLowerStructureBSIx(Double.parseDouble(df.format(result.getBciEvaluationResultDTO().getLowerStructureBSIx())));
        }
        if (result.getBciEvaluationResultDTO().getUpperStructureBCIs() != null) {
            result.getBciEvaluationResultDTO().setUpperStructureBCIs(Double.parseDouble(df.format(result.getBciEvaluationResultDTO().getUpperStructureBCIs())));
        }
        if (result.getBciEvaluationResultDTO().getUpperStructureBSIs() != null) {
            result.getBciEvaluationResultDTO().setUpperStructureBSIs(Double.parseDouble(df.format(result.getBciEvaluationResultDTO().getUpperStructureBSIs())));
        }
        componentSort(result);
        return result;
    }

    /**
     * 合并桥面系构件并合并病害（取病害扣分的最大值）
     * 备注：一条线路只有一个桥面系顾需要把跨下的所有桥面系构件合并，并且需要把构件下的病害合并且取最大扣分值
     *
     * @param artifactsDTOS 构件集合
     * @return
     */
    private Map<Long, ArtifactsDTO> mergeArtifactsDTO(List<ArtifactsDTO> artifactsDTOS) {
        Map<Long, ArtifactsDTO> map = new HashMap<>();
        for (ArtifactsDTO artifactsDTO : artifactsDTOS) {
            if (artifactsDTO.getComponentId() != null && map.containsKey(artifactsDTO.getComponentId())) {
                List<DiseaseInstanceDTO> instanceDTOList = artifactsDTO.getInstanceDTOList(); //病害
                ArtifactsDTO mapArtifactsDTO = map.get(artifactsDTO.getComponentId());
                List<DiseaseInstanceDTO> allArtifactsDTO = mapArtifactsDTO.getInstanceDTOList();
                //合并病害并取最大扣分
                for (DiseaseInstanceDTO diseaseInstanceDTO : instanceDTOList) {
                    if (diseaseInstanceDTO.getDiseaseName() == null) {
                        continue;
                    }
                    int i = 0;
                    for (DiseaseInstanceDTO instanceDTO : allArtifactsDTO) {
                        // 设值最大病害扣分值
                        if (diseaseInstanceDTO.getDiseaseName().equals(instanceDTO.getDiseaseName())) {
                            i++;
                            if (diseaseInstanceDTO.getDeduct() != null) {
                                Double aDouble = instanceDTO.getDeduct() != null && diseaseInstanceDTO.getDeduct() > instanceDTO.getDeduct() ? diseaseInstanceDTO.getDeduct() : instanceDTO.getDeduct();
                                instanceDTO.setDeduct(aDouble);
                            }
                        }
                    }
                    if (i == 0) {
                        allArtifactsDTO.add(diseaseInstanceDTO); //合并病害
                    }
                }
//                allArtifactsDTO.addAll(instanceDTOList);
                //选择扣分值最大的
                mapArtifactsDTO.setDeduct(artifactsDTO.getDeduct() != null && artifactsDTO.getDeduct() > mapArtifactsDTO.getDeduct() ? artifactsDTO.getDeduct() : mapArtifactsDTO.getDeduct());
                mapArtifactsDTO.setInstanceDTOList(allArtifactsDTO);
            } else {
                map.put(artifactsDTO.getComponentId(), artifactsDTO);
            }
        }
        return map;
    }

    @Override
    public Integer existRecord(Long roadId) {
        return monitorPlanOriginalRecordExpDao.existRecord(roadId);
    }

    /**
     * 计算BCI评分
     *
     * @param bciEvaluationDetailDTO 线路对象
     * @return
     */
    @Transactional
    @Override
    public BCIEvaluationDetailDTO countBCI(BCIEvaluationDetailDTO bciEvaluationDetailDTO, HttpServletRequest request) {
//        UserVO user = (UserVO) request.getSession().getAttribute(GlobalConstant.SESSION_USER_KEY);
//        if (user == null) {
//            throw new BusinessException("session失效！！！");
//        }
        User query = new User();
        query.setId(ThreadLocalMap.getUserId());
        List<User> userList = userDao.query(query);
        User user = new User();
        if (userList.size() == 0) {
            throw new BusinessException("session失效！！！");
        } else {
            user = userList.get(0);
        }
        Long roadId = bciEvaluationDetailDTO.getRoadId();
        if (roadId == null) {
            throw new BusinessException("线路Id为空！！！");
        }
        //BCI评价结果
        BCIEvaluationResultDTO bciEvaluationResultDTO = recordDetailInfoDao.selBCIEvaluationResultDTO(roadId);
        //计算BCI评分
        BCIEvaluationDetailDTO resultDetail = BCIRecordUtils.couBCIEvaluationDetail(bciEvaluationDetailDTO);
        /*********************保存评分*****************/
        UpperStructureDTO upperStructureDTO = bciEvaluationDetailDTO.getUpperStructureDTO(); //上部结构
        LowerStructureDTO lowerStructureDTO = bciEvaluationDetailDTO.getLowerStructureDTO(); //下部结构
        FloorSystemDTO floorSystemDTO = bciEvaluationDetailDTO.getFloorSystemDTO(); //桥面系
        if (floorSystemDTO != null) { //桥面系
            BCIDetailVO bciDetailVO = new BCIDetailVO();
            bciDetailVO.setRoadId(roadId);
            List<ArtifactsDTO> artifactsDTOS = recordDetailInfoDao.selArtifactsDTO(bciDetailVO); //桥面系构件实例BY线路id
            List<ArtifactsDTO> artifactsDTOList = floorSystemDTO.getArtifactsDTOList(); //重新计算时的构件
            for (ArtifactsDTO dto : artifactsDTOS) {
                for (ArtifactsDTO artifactsDTO : artifactsDTOList) {
                    // 更新每个桥面系的构件实例的实际权重和扣分
                    if (artifactsDTO.getArtifactsId() != null && artifactsDTO.getArtifactsName().equals(dto.getArtifactsName())) {
                        dto.setActualWeight(artifactsDTO.getActualWeight());
                        dto.setDeduct(artifactsDTO.getDeduct());
                    }
                }
            }
            recordDetailInfoDao.updateUpperArtifactsDTO(artifactsDTOS, TableNameEnum.BRIDGE_DECK.getTable()); //更新结构的构件
        }
        List<BridgeScoreDTO> noBridgeScoreDTOs = new ArrayList<>(); //新增集合
        List<BridgeScoreDTO> bridgeScoreDTOs = new ArrayList<>(); //修改集合
        if (upperStructureDTO != null) { //上部结构
            List<AcrossDTO> dtoList = upperStructureDTO.getAcrossDTOList(); //获取跨
            List<ArtifactsDTO> upperArtifactsDTO = new ArrayList<>(); //上部结构的所有构件
            if (dtoList != null) {
                breakAcrossDTO(dtoList, noBridgeScoreDTOs, bridgeScoreDTOs, upperArtifactsDTO, 8, user, roadId);
                recordDetailInfoDao.updateUpperArtifactsDTO(upperArtifactsDTO, TableNameEnum.BRIDGE_UPPER.getTable()); //更新结构的构件
            }
        }
        if (lowerStructureDTO != null) { //下部结构
            List<AcrossDTO> dtoList = lowerStructureDTO.getAcrossDTOList();
            List<ArtifactsDTO> upperArtifactsDTO = new ArrayList<>(); //上部结构的所有构件
            if (dtoList != null) {
                breakAcrossDTO(dtoList, noBridgeScoreDTOs, bridgeScoreDTOs, upperArtifactsDTO, 6, user, roadId);
                recordDetailInfoDao.updateUpperArtifactsDTO(upperArtifactsDTO, TableNameEnum.BRIDGE_LOWER.getTable()); //更新结构的构件
            }
        }
        recordDetailInfoDao.saveAcrossDTOScore(noBridgeScoreDTOs);
        recordDetailInfoDao.updateAcrossDTOScore(bridgeScoreDTOs);
        /************************返回结果***********************/
        for (BridgeScoreDTO noBridgeScoreDTO : noBridgeScoreDTOs) {
            if (lowerStructureDTO != null) {
                //下部结构回值
                List<AcrossDTO> acrossDTOList = lowerStructureDTO.getAcrossDTOList();
                for (AcrossDTO acrossDTO : acrossDTOList) {
                    if (acrossDTO.getSpanId() != null && acrossDTO.getSpanId().equals(noBridgeScoreDTO.getTargetId()) && 6 == noBridgeScoreDTO.getType()) {
                        acrossDTO.setScoreId(noBridgeScoreDTO.getId());
                    }
                }
            }
            if (upperStructureDTO != null) {
                //上部结构回值
                List<AcrossDTO> acrossDTOList = upperStructureDTO.getAcrossDTOList();
                for (AcrossDTO acrossDTO : acrossDTOList) {
                    if (acrossDTO.getSpanId() != null && acrossDTO.getSpanId().equals(noBridgeScoreDTO.getTargetId()) && 8 == noBridgeScoreDTO.getType()) {
                        acrossDTO.setScoreId(noBridgeScoreDTO.getId());
                    }
                }
            }
        }
        // 设置BCI评价结果
        UpperStructureDTO upperResult = resultDetail.getUpperStructureDTO();  //上部结构
        LowerStructureDTO lowerResult = resultDetail.getLowerStructureDTO();  //下部结构
        FloorSystemDTO resultFloor = resultDetail.getFloorSystemDTO();
        if (bciEvaluationResultDTO != null) {
            bciEvaluationResultDTO.setEvaluationTime(new Date());
            bciEvaluationResultDTO.setRatingLevel(resultDetail.getRatingLevel());
            bciEvaluationResultDTO.setEvaluatorId(user.getId());
            bciEvaluationResultDTO.setEvaluator(user.getRealName());
            if (resultFloor != null) {
                bciEvaluationResultDTO.setBridgeBCIm(resultFloor.getBCIm());
                bciEvaluationResultDTO.setBridgeBSIm(resultFloor.getBSIm());
            }
            if (upperResult != null) {
                bciEvaluationResultDTO.setUpperStructureBCIs(upperResult.getBCIs());
                bciEvaluationResultDTO.setUpperStructureBSIs(upperResult.getBSIs());
            }
            if (lowerResult != null) {
                bciEvaluationResultDTO.setLowerStructureBCIx(lowerResult.getBCIx());
                bciEvaluationResultDTO.setLowerStructureBSIx(lowerResult.getBSIx());
            }
            //评价单位
            if (ThreadLocalMap.getRoleId() == 1) {
                Unit unitModel = unitDao.findById(ThreadLocalMap.getUnitId());
                bciEvaluationResultDTO.setEvaluationUnit(unitModel.getName());
            } else if (ThreadLocalMap.getRoleId() == 0) {
                User userModel = userDao.findById(ThreadLocalMap.getUserId());
                User userModel2 = userDao.findById(userModel.getCreateUserId());
                Unit unitModel = unitDao.findById(userModel2.getUnitId());
                bciEvaluationResultDTO.setEvaluationUnit(unitModel.getName());
            } else {
                bciEvaluationResultDTO.setEvaluationUnit(recordDetailInfoDao.selectUnit(bciEvaluationDetailDTO.getRoadId()));
            }
            //保存更改线路BCI开始
            saveOrUpdateRodeBCI(bciEvaluationResultDTO, resultDetail);
            bciEvaluationResultDTO.setRoadScore(resultDetail.getBCIScore());
            resultDetail.setBciEvaluationResultDTO(bciEvaluationResultDTO);
        }
        /**##########修改结构物评定等级############**/
        MonitorStructureDTO monitorStructureDTO = recordDetailInfoDao.selMonitorStructureDTOByRoadId(roadId);
        Double monitorStructureScore = monitorStructureDTO.getScore();
        String monitorStructureLevel = monitorStructureDTO.getRatingLevel();
        RoadScoreDTO roadScoreDTOS = recordDetailInfoDao.sleRoadScoreDTOList(roadId); //该结构物所有线路评分
        if (roadScoreDTOS.getId() != null) {
            if (roadScoreDTOS.getId().equals(roadId)) {
                monitorStructureScore = resultDetail.getBCIScore();
                monitorStructureLevel = resultDetail.getRatingLevel();
            } else {
                if (monitorStructureDTO.getScore() == null || resultDetail.getBCIScore() < monitorStructureDTO.getScore()) {
                    monitorStructureScore = resultDetail.getBCIScore();
                    monitorStructureLevel = resultDetail.getRatingLevel();
                }
            }
        }
        BridgeScoreDTO bridgeScoreDTO = new BridgeScoreDTO();
        bridgeScoreDTO.setTargetId(monitorStructureDTO.getId());
        bridgeScoreDTO.setType(1); //结构物评分
        bridgeScoreDTO.setScore(monitorStructureScore);
        bridgeScoreDTO.setRatingLevel(monitorStructureLevel);
        bridgeScoreDTO.setEvaluatorId(user.getId());
//        bridgeScoreDTO.setEvaluationUnit(user.getRealName());
        bridgeScoreDTO.setEvaluationTime(new Date());
        if (null != monitorStructureDTO.getScoreId()) {
            bridgeScoreDTO.setId(monitorStructureDTO.getScoreId());
            recordDetailInfoDao.updateRodeBCI(bridgeScoreDTO);
        } else {
            recordDetailInfoDao.saveMonitorStructure(bridgeScoreDTO);
        }
        resultDetail.setSpanNumber(bciEvaluationResultDTO.getSpanNumber());


        DecimalFormat df = new DecimalFormat("######0.00");
        if (resultDetail.getBciEvaluationResultDTO().getRoadScore() != null) {
            resultDetail.getBciEvaluationResultDTO().setRoadScore(Double.parseDouble(df.format(resultDetail.getBciEvaluationResultDTO().getRoadScore())));
        }
        if (resultDetail.getBCIScore() != null) {
            resultDetail.setBCIScore(Double.parseDouble(df.format(resultDetail.getBCIScore())));
        }
        if (resultDetail.getBSIScore() != null) {
            resultDetail.setBSIScore(Double.parseDouble(df.format(resultDetail.getBSIScore())));
        }
        if (resultDetail.getFloorSystemDTO().getBCIm() != null) {
            resultDetail.getFloorSystemDTO().setBCIm(Double.parseDouble(df.format(resultDetail.getFloorSystemDTO().getBCIm())));
        }
        if (resultDetail.getFloorSystemDTO().getBSIm() != null) {
            resultDetail.getFloorSystemDTO().setBSIm(Double.parseDouble(df.format(resultDetail.getFloorSystemDTO().getBSIm())));
        }
        if (resultDetail.getUpperStructureDTO().getBCIs() != null) {
            resultDetail.getUpperStructureDTO().setBCIs(Double.parseDouble(df.format(resultDetail.getUpperStructureDTO().getBCIs())));
        }
        if (resultDetail.getUpperStructureDTO().getBSIs() != null) {
            resultDetail.getUpperStructureDTO().setBSIs(Double.parseDouble(df.format(resultDetail.getUpperStructureDTO().getBSIs())));
        }
        if (resultDetail.getLowerStructureDTO().getBCIx() != null) {
            resultDetail.getLowerStructureDTO().setBCIx(Double.parseDouble(df.format(resultDetail.getLowerStructureDTO().getBCIx())));
        }
        if (resultDetail.getLowerStructureDTO().getBSIx() != null) {
            resultDetail.getLowerStructureDTO().setBSIx(Double.parseDouble(df.format(resultDetail.getLowerStructureDTO().getBSIx())));
        }
        if (resultDetail.getBciEvaluationResultDTO().getBridgeBCIm() != null) {
            resultDetail.getBciEvaluationResultDTO().setBridgeBCIm(Double.parseDouble(df.format(resultDetail.getBciEvaluationResultDTO().getBridgeBCIm())));
        }
        if (resultDetail.getBciEvaluationResultDTO().getBridgeBSIm() != null) {
            resultDetail.getBciEvaluationResultDTO().setBridgeBSIm(Double.parseDouble(df.format(resultDetail.getBciEvaluationResultDTO().getBridgeBSIm())));
        }
        if (resultDetail.getBciEvaluationResultDTO().getLowerStructureBCIx() != null) {
            resultDetail.getBciEvaluationResultDTO().setLowerStructureBCIx(Double.parseDouble(df.format(resultDetail.getBciEvaluationResultDTO().getLowerStructureBCIx())));
        }
        if (resultDetail.getBciEvaluationResultDTO().getLowerStructureBSIx() != null) {
            resultDetail.getBciEvaluationResultDTO().setLowerStructureBSIx(Double.parseDouble(df.format(resultDetail.getBciEvaluationResultDTO().getLowerStructureBSIx())));
        }
        if (resultDetail.getBciEvaluationResultDTO().getUpperStructureBCIs() != null) {
            resultDetail.getBciEvaluationResultDTO().setUpperStructureBCIs(Double.parseDouble(df.format(resultDetail.getBciEvaluationResultDTO().getUpperStructureBCIs())));
        }
        if (resultDetail.getBciEvaluationResultDTO().getUpperStructureBSIs() != null) {
            resultDetail.getBciEvaluationResultDTO().setUpperStructureBSIs(Double.parseDouble(df.format(resultDetail.getBciEvaluationResultDTO().getUpperStructureBSIs())));
        }

        isEvaluationAll(monitorStructureDTO.getRoadId());
        componentSort(resultDetail);
        return resultDetail;
    }

    /**
     * 保存或修改线路BCI
     *
     * @param bciEvaluationResultDTO BCI评价结果
     * @param resultDetail           线路对象
     */
    @Transactional
    public void saveOrUpdateRodeBCI(BCIEvaluationResultDTO bciEvaluationResultDTO, BCIEvaluationDetailDTO resultDetail) {
        if (bciEvaluationResultDTO != null) {
            BridgeScoreDTO bridgeScoreDTO = new BridgeScoreDTO(); //线路评分对象
            bridgeScoreDTO.setId(bciEvaluationResultDTO.getRoadScoreId());
            bridgeScoreDTO.setTargetId(resultDetail.getRoadId()); //线路id
            bridgeScoreDTO.setType(2);
            bridgeScoreDTO.setEvaluationUnit(bciEvaluationResultDTO.getEvaluationUnit());//评价单位
            bridgeScoreDTO.setBridgeConditionIndex(resultDetail.getBCIScore()); //BCI评分
            bridgeScoreDTO.setBridgeStructureIndex(resultDetail.getBSIScore()); //BSI评分
            bridgeScoreDTO.setEvaluatorId(bciEvaluationResultDTO.getEvaluatorId()); //评价人员id
            bridgeScoreDTO.setRatingLevel(bciEvaluationResultDTO.getRatingLevel()); //评价等级
            bridgeScoreDTO.setEvaluationTime(bciEvaluationResultDTO.getEvaluationTime()); //评价时间
            if (bciEvaluationResultDTO.getRoadScoreId() == null) {
                recordDetailInfoDao.saveRodeBCI(bridgeScoreDTO);

                BridgeScoreDTO newBridgeScoreDTO = new BridgeScoreDTO();
                BeanUtils.copyProperties(bridgeScoreDTO, newBridgeScoreDTO);
                newBridgeScoreDTO.setType(3);
                newBridgeScoreDTO.setBridgeConditionIndex(bciEvaluationResultDTO.getBridgeBCIm());
                newBridgeScoreDTO.setBridgeStructureIndex(bciEvaluationResultDTO.getBridgeBSIm());
                recordDetailInfoDao.saveRodeBCI(newBridgeScoreDTO);

                newBridgeScoreDTO = new BridgeScoreDTO();
                BeanUtils.copyProperties(bridgeScoreDTO, newBridgeScoreDTO);
                newBridgeScoreDTO.setType(4);
                newBridgeScoreDTO.setBridgeConditionIndex(bciEvaluationResultDTO.getUpperStructureBCIs());
                newBridgeScoreDTO.setBridgeStructureIndex(bciEvaluationResultDTO.getUpperStructureBSIs());
                recordDetailInfoDao.saveRodeBCI(newBridgeScoreDTO);

                newBridgeScoreDTO = new BridgeScoreDTO();
                BeanUtils.copyProperties(bridgeScoreDTO, newBridgeScoreDTO);
                newBridgeScoreDTO.setType(5);
                newBridgeScoreDTO.setBridgeConditionIndex(bciEvaluationResultDTO.getLowerStructureBCIx());
                newBridgeScoreDTO.setBridgeStructureIndex(bciEvaluationResultDTO.getLowerStructureBSIx());
                recordDetailInfoDao.saveRodeBCI(newBridgeScoreDTO);

            } else {
                recordDetailInfoDao.updateRodeBCI(bridgeScoreDTO);


                BridgeScoreDTO newBridgeScoreDTO = new BridgeScoreDTO();
                BeanUtils.copyProperties(bridgeScoreDTO, newBridgeScoreDTO);
                newBridgeScoreDTO.setType(3);
                newBridgeScoreDTO.setBridgeConditionIndex(bciEvaluationResultDTO.getBridgeBCIm());
                newBridgeScoreDTO.setBridgeStructureIndex(bciEvaluationResultDTO.getBridgeBSIm());
                recordDetailInfoDao.updateDKSUBSUPBCI(newBridgeScoreDTO);

                newBridgeScoreDTO = new BridgeScoreDTO();
                BeanUtils.copyProperties(bridgeScoreDTO, newBridgeScoreDTO);
                newBridgeScoreDTO.setType(4);
                newBridgeScoreDTO.setBridgeConditionIndex(bciEvaluationResultDTO.getUpperStructureBCIs());
                newBridgeScoreDTO.setBridgeStructureIndex(bciEvaluationResultDTO.getUpperStructureBSIs());
                recordDetailInfoDao.updateDKSUBSUPBCI(newBridgeScoreDTO);

                newBridgeScoreDTO = new BridgeScoreDTO();
                BeanUtils.copyProperties(bridgeScoreDTO, newBridgeScoreDTO);
                newBridgeScoreDTO.setType(5);
                newBridgeScoreDTO.setBridgeConditionIndex(bciEvaluationResultDTO.getLowerStructureBCIx());
                newBridgeScoreDTO.setBridgeStructureIndex(bciEvaluationResultDTO.getLowerStructureBSIx());
                recordDetailInfoDao.updateDKSUBSUPBCI(newBridgeScoreDTO);
            }
        }
    }

    /**
     * 拆分跨
     *
     * @param dtoList           总和跨
     * @param noBridgeScoreDTOs 新增跨评分
     * @param bridgeScoreDTOs   更新跨评分
     * @param upperArtifactsDTO 构件集合
     * @param type              类型 6：墩 8：跨
     * @param user              用户信息
     */
    private void breakAcrossDTO(List<AcrossDTO> dtoList, List<BridgeScoreDTO> noBridgeScoreDTOs, List<BridgeScoreDTO> bridgeScoreDTOs, List<ArtifactsDTO> upperArtifactsDTO, int type, User user, Long roadId) {


        if (noBridgeScoreDTOs == null || bridgeScoreDTOs == null) {
            throw new BusinessException("跨评分集合为空！！！！！");
        }
        if (dtoList != null) {
            for (AcrossDTO acrossDTO : dtoList) {
                upperArtifactsDTO.addAll(acrossDTO.getArtifactsDTOList());
                BridgeScoreDTO bridgeScoreDTO = new BridgeScoreDTO(); //线路评分对象
                bridgeScoreDTO.setTargetId(acrossDTO.getSpanId());
                bridgeScoreDTO.setType(type);
                if (type == 6) {
                    bridgeScoreDTO.setBridgeStructureIndex(acrossDTO.getBSIxj());
                    bridgeScoreDTO.setBridgeConditionIndex(acrossDTO.getBCIxj());
                } else {
                    bridgeScoreDTO.setBridgeStructureIndex(acrossDTO.getBSIsi());
                    bridgeScoreDTO.setBridgeConditionIndex(acrossDTO.getBCIsi());
                }
                bridgeScoreDTO.setRatingLevel(acrossDTO.getRatingLevel()); //评价等级
                bridgeScoreDTO.setEvaluatorId(user.getId()); //评价人员Id
                bridgeScoreDTO.setEvaluationTime(new Date()); //评价时间
                if (acrossDTO.getScoreId() == null) {
                    noBridgeScoreDTOs.add(bridgeScoreDTO);
                } else {
                    bridgeScoreDTO.setId(acrossDTO.getScoreId());
                    bridgeScoreDTOs.add(bridgeScoreDTO);
                }
            }
        }
    }

    /**
     * 判断计划是否都已经评价完成
     **/
    public void isEvaluationAll(Long roadId) {
        List<Integer> list = bridgeExpDao.selectStructureRelIdByRoadId(roadId);
        for (Integer integer : list) {
            AssessRecordFilter assessRecordFilter = new AssessRecordFilter();
            assessRecordFilter.setStructureInfoId(integer);
            List<BCIEvaluation> bciEvaluationList = evaluationRecordService.getBCIEvaluationList(assessRecordFilter);
            for (BCIEvaluation bciEvaluation : bciEvaluationList) {
                if (bciEvaluation.isIs() == false) {
                    return;
                }
            }

        }
        monitorPlanExpDao.updateStateByRoadId(roadId);

    }

    /**
     * 构件排序
     **/
    public void componentSort(BCIEvaluationDetailDTO bciEvaluationDetailDTO) {
        Integer bridgeTypeId = bciEvaluationDetailDTO.getBridgeTypeId();
        if (bciEvaluationDetailDTO.getFloorSystemDTO().getArtifactsDTOList().size() != 0) {
            List<String> dkList = new ArrayList<>();
            dkList.add("桥面铺装");
            dkList.add("桥头平顺");
            dkList.add("伸缩装置");
            dkList.add("排水系统");
            dkList.add("人行道");
            dkList.add("栏杆或护栏");
            List<ArtifactsDTO> artifactsDTOList = new ArrayList<>();
            for (String s : dkList) {
                for (ArtifactsDTO artifactsDTO : bciEvaluationDetailDTO.getFloorSystemDTO().getArtifactsDTOList()) {
                    if (s.equals(artifactsDTO.getArtifactsName())) {
                        artifactsDTOList.add(artifactsDTO);
                    }
                }
            }
            bciEvaluationDetailDTO.getFloorSystemDTO().setArtifactsDTOList(artifactsDTOList);
        }


        if (bciEvaluationDetailDTO.getUpperStructureDTO().getAcrossDTOList().size() != 0) {
            List<String> supList = new ArrayList<>();
            supList.add("悬臂梁");
            supList.add("挂梁");
            supList.add("挂梁支座");
            supList.add("防落梁装置");
            supList.add("桁片");
            supList.add("主节点");
            supList.add("纵梁");
            supList.add("横梁");
            supList.add("连接件");
            supList.add("主拱圈");
            supList.add("拱上构造");
            supList.add("主梁");
            supList.add("横向联系");
            supList.add("外部装饰板");

            for (AcrossDTO acrossDTO : bciEvaluationDetailDTO.getUpperStructureDTO().getAcrossDTOList()) {
                List<ArtifactsDTO> artifactsDTO = new ArrayList<>();
                if (acrossDTO.getArtifactsDTOList().size() != 0) {
                    for (String s : supList) {
                        for (ArtifactsDTO dto : acrossDTO.getArtifactsDTOList()) {
                            if (s.equals(dto.getArtifactsName())) {
                                artifactsDTO.add(dto);
                            }
                        }
                    }

                }
                acrossDTO.setArtifactsDTOList(artifactsDTO);
            }
        }

        if (bciEvaluationDetailDTO.getLowerStructureDTO().getAcrossDTOList().size() != 0) {
            List<String> subList = new ArrayList<>();
            subList.add("盖梁");
            subList.add("墩身");
            subList.add("台帽");
            subList.add("台身");
            subList.add("基础");
            subList.add("外部装饰板");
            subList.add("耳墙");
            subList.add("支座");
            subList.add("拱脚");

            for (AcrossDTO acrossDTO : bciEvaluationDetailDTO.getLowerStructureDTO().getAcrossDTOList()) {
                List<ArtifactsDTO> artifactsDTO = new ArrayList<>();
                if (acrossDTO.getArtifactsDTOList().size() != 0) {
                    for (String s : subList) {
                        for (ArtifactsDTO dto : acrossDTO.getArtifactsDTOList()) {
                            if (s.equals(dto.getArtifactsName())) {
                                artifactsDTO.add(dto);
                            }
                        }
                    }
                }
                acrossDTO.setArtifactsDTOList(artifactsDTO);
            }
        }

    }
}
