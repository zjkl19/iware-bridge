package com.iware.bridge.evaluation.Utils;


import com.iware.bridge.evaluation.enums.DiseaseCodeEnum;
import com.iware.bridge.evaluation.enums.DiseaseEnum;
import com.iware.bridge.evaluation.enums.RecordConstant;
import com.iware.bridge.evaluation.vo.*;
import com.iware.common.exception.BusinessException;
import com.iware.common.utils.ArithmeticUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.*;

/**
 * @author: CWY
 * @Date: 2021/5/12 17:05
 */
@Slf4j
public class BCIRecordUtils {

    /**
     * 线路算法
     *
     * @param bciEvaluationDetailDTO
     * @return
     */
    public static BCIEvaluationDetailDTO couBCIEvaluationDetail(BCIEvaluationDetailDTO bciEvaluationDetailDTO) {
        FloorSystemDTO floorSystemDTO = bciEvaluationDetailDTO.getFloorSystemDTO(); //桥面系
        UpperStructureDTO upperStructureDTO = bciEvaluationDetailDTO.getUpperStructureDTO(); //上部结构
        LowerStructureDTO lowerStructureDTO = bciEvaluationDetailDTO.getLowerStructureDTO(); //下部结构
        Double BCI = 0.0;
        Double floor = null; //桥面系权重
        Double upper = null; //上部结构权重
        Double lower = null; //下部结构权重
        String specialLevel = null; //特殊病害等级
        if (bciEvaluationDetailDTO.getBridgeTypeId() == null) {
            throw new BusinessException("桥梁类型为空");
        }
        if (7 == bciEvaluationDetailDTO.getBridgeTypeId()) {
            floor = DiseaseEnum.PEDESTRIAN_BRIDGE_FLOOR.getNum();
            upper = DiseaseEnum.PEDESTRIAN_BRIDGE_UPPER.getNum();
            lower = DiseaseEnum.PEDESTRIAN_BRIDGE_LOWER.getNum();
        } else if (5 == bciEvaluationDetailDTO.getBridgeTypeId() || 6 == bciEvaluationDetailDTO.getBridgeTypeId()) {
            floor = DiseaseEnum.ARCH_BRIDGE_FLOOR.getNum();
            upper = DiseaseEnum.ARCH_BRIDGE_UPPER.getNum();
            lower = DiseaseEnum.ARCH_BRIDGE_LOWER.getNum();
        } else {
            floor = DiseaseEnum.BEAM_BRIDGE_FLOOR.getNum();
            upper = DiseaseEnum.BEAM_BRIDGE_UPPER.getNum();
            lower = DiseaseEnum.BEAM_BRIDGE_LOWER.getNum();
        }
        if (floorSystemDTO != null) {
            couFloorSystem(floorSystemDTO); //计算桥面系BCI
            if (floorSystemDTO.getBCIm() != null) {
                BCI += ArithmeticUtils.mul(floorSystemDTO.getBCIm(), floor);
            }
            if (floorSystemDTO.getSpecialLevel() != null) {
                specialLevel = floorSystemDTO.getSpecialLevel();
            }
        } else {
            BCI += ArithmeticUtils.mul(100, floor);
        }

        if (upperStructureDTO != null) {
            couUpperStructure(upperStructureDTO); //计算上部结构BCI
            if (upperStructureDTO.getBCIs() != null) {
                BCI += ArithmeticUtils.mul(upperStructureDTO.getBCIs(), upper);
            }
            if (upperStructureDTO.getSpecialLevel() != null) {
                specialLevel = upperStructureDTO.getSpecialLevel();
            }
        } else {
            BCI += ArithmeticUtils.mul(100, upper);
        }

        if (lowerStructureDTO != null) {
            couLowerStructure(lowerStructureDTO); //计算下部结构BCI
            if (lowerStructureDTO.getBCIx() != null) {
                BCI += ArithmeticUtils.mul(lowerStructureDTO.getBCIx(), lower);
            }
            if (lowerStructureDTO.getSpecialLevel() != null) {
                specialLevel = lowerStructureDTO.getSpecialLevel();
            }
        } else {
            BCI += ArithmeticUtils.mul(100, lower);
        }
        bciEvaluationDetailDTO.setBCIScore(BCI);
        BCIEvaluationResultDTO bciEvaluationResultDTO = bciEvaluationDetailDTO.getBciEvaluationResultDTO();
        String rating = rating(BCI, specialLevel); //评定等级
        bciEvaluationDetailDTO.setRatingLevel(rating);
        if (bciEvaluationResultDTO != null) {
            bciEvaluationResultDTO.setRatingLevel(rating);
        }
        log.info("#####BCI评分值{}：" + BCI);
        return bciEvaluationDetailDTO;
    }

    /**
     * 桥面系算法
     *
     * @param floorSystemDTO 桥面系
     * @return
     */
    public static FloorSystemDTO couFloorSystem(FloorSystemDTO floorSystemDTO) {
        List<ArtifactsDTO> artifactsDTOList = floorSystemDTO.getArtifactsDTOList(); //构件集合
        Map<String, Double> map = couArtifact(artifactsDTOList);
        floorSystemDTO.setBCIm(map.get("BCI") == null ? 100.0 : map.get("BCI"));
        floorSystemDTO.setBSIm(map.get("BSI") == null ? 100.0 : map.get("BSI"));
        Double specialLevel = map.get("specialLevel");
        if (specialLevel != null && 1.0 == specialLevel) { //特殊评定等级
            floorSystemDTO.setSpecialLevel("D");
        }
        log.info("==========桥面系BSI值{}：" + map.get("BSI"));
        log.info("==========桥面系BCI值{}：" + map.get("BCI"));
        return floorSystemDTO;
    }

    /**
     * 上部结构算法
     *
     * @param upperStructureDTO 上部结构
     * @return
     */
    public static UpperStructureDTO couUpperStructure(UpperStructureDTO upperStructureDTO) {
        List<Double> acrossBCIs = new ArrayList<>();
        List<Double> acrossBSIs = new ArrayList<>();
        List<AcrossDTO> acrossDTOList = upperStructureDTO.getAcrossDTOList(); //获取跨集合
        String specialLevel = couAcrossDTO(acrossDTOList, acrossBCIs, acrossBSIs, 0);
        upperStructureDTO.setSpecialLevel(specialLevel); //评定等级
        double BSIs = 100.0; //上部结构的BSI
        double BCIs = 100.0;
        if (acrossBCIs.size() > 0) {
            BSIs = Collections.min(acrossBCIs);
            BCIs = ArithmeticUtils.div(sum(acrossBCIs), acrossBCIs.size());
        }
        log.info("=========上部结构 BSIs{}:" + BSIs);
        log.info("=========上部结构 BCIs{}:" + BCIs);
        upperStructureDTO.setBSIs(BSIs);
        upperStructureDTO.setBCIs(BCIs);
        return upperStructureDTO;
    }

    /**
     * 求和
     *
     * @param doubles
     * @return
     */
    private static double sum(List<Double> doubles) {
        double d = 0.0;
        for (Double aDouble : doubles) {
            if (aDouble != null) {
                d += aDouble;
            }
        }
        return d;
    }

    /**
     * 下部结构算法
     *
     * @param lowerStructureDTO 下部结构
     * @return
     */
    public static LowerStructureDTO couLowerStructure(LowerStructureDTO lowerStructureDTO) {
        List<Double> acrossBCIx = new ArrayList<>();
        List<Double> acrossBSIx = new ArrayList<>();
        List<AcrossDTO> acrossDTOList = lowerStructureDTO.getAcrossDTOList(); //获取跨集合
        String specialLevel = couAcrossDTO(acrossDTOList, acrossBCIx, acrossBSIx, 1);
        lowerStructureDTO.setSpecialLevel(specialLevel); //评定等级
        Double BSIx = 100.0; //下部结构的BSI 无情况下默认为100
        double BCIx = 100.0;
        log.info("=========下部结构 BSIx{}:" + BSIx);
        if (acrossBCIx.size() > 0) {
            BSIx = Collections.min(acrossBCIx);
            double BCIxSum = sum(acrossBCIx); //获取跨的BCI总和
            //acrossBCIx.size()本身就是桥墩数，顾不需要加1，公式中得b是桥跨，而加1等于桥墩
            BCIx = ArithmeticUtils.div(BCIxSum, acrossBCIx.size());
            log.info("=========下部结构 BCIx{}:" + BCIx);
        }
        lowerStructureDTO.setBCIx(BCIx);
        lowerStructureDTO.setBSIx(BSIx);
        return lowerStructureDTO;
    }

    /**
     * 构件计算公式
     *
     * @param artifactsDTOList 构件
     */
    public static Map<String, Double> couArtifact(List<ArtifactsDTO> artifactsDTOList) {
        Map<String, Double> map = new HashMap<>();
        List<Double> MDPhs = new ArrayList<>(); //病害综合扣分值 集合
        for (ArtifactsDTO artifactsDTO : artifactsDTOList) {
            Double deductTotal = 0.0; //病害扣分值总和
            if (artifactsDTO.getInstanceDTOList() == null && artifactsDTO.getDeduct() == null) {
                //无病害，且无扣分时跳过
                continue;
            }
            //求出病害扣分值总和 and 判断特殊病害是否超过不合格评定
            if (artifactsDTO.getInstanceDTOList() != null) {
                for (DiseaseInstanceDTO diseaseInstanceDTO : artifactsDTO.getInstanceDTOList()) {
                    Double specialLevel = specialRating(diseaseInstanceDTO); //特殊评定等级
                    map.put("specialLevel", specialLevel);
                    if (diseaseInstanceDTO.getDeduct() != null) {
                        deductTotal += diseaseInstanceDTO.getDeduct(); //病害扣分值
                    }
                }
            }

            Double MDPh = 0.0; // 病害综合扣分值
            if (artifactsDTO.getDeduct() == null && artifactsDTO.getInstanceDTOList() != null) { //构件扣分值为空时才需要计算扣分值
                //求出每项病害扣分值得比例及权重
                for (DiseaseInstanceDTO diseaseInstanceDTO : artifactsDTO.getInstanceDTOList()) {
                    Double deduct = diseaseInstanceDTO.getDeduct(); //病害扣分值
                    if (deduct != null && null != deductTotal && deductTotal > 0) {
                        double uhi = ArithmeticUtils.div(deduct, deductTotal); //损坏值比例
                        double mul3 = ArithmeticUtils.mul(ArithmeticUtils.mul(ArithmeticUtils.mul(uhi, uhi), uhi), 3.0);
                        double mul2 = ArithmeticUtils.mul(ArithmeticUtils.mul(uhi, uhi), 5.5);
                        double mul1 = ArithmeticUtils.mul(uhi, 3.5);
                        double whi = ArithmeticUtils.add(ArithmeticUtils.sub(mul3, mul2), mul1); //权重
                        MDPh += ArithmeticUtils.mul(deduct, whi); //病害扣分值*病害损害得权重
                    }
                }
                //求出每项得最大综合值
                for (DiseaseInstanceDTO diseaseInstanceDTO : artifactsDTO.getInstanceDTOList()) {
                    if (MDPh != null && diseaseInstanceDTO.getDeduct() != null) {
                        //当扣分值小于单项得扣分值时取最大那项得扣分值
                        MDPh = MDPh > diseaseInstanceDTO.getDeduct() ? MDPh : diseaseInstanceDTO.getDeduct();
                    }
                }
            } else {
                MDPh = artifactsDTO.getDeduct();
            }
            if (null != MDPh) {
                DecimalFormat df = new DecimalFormat("######0.00");
                MDPh = Double.parseDouble(df.format(MDPh));
                artifactsDTO.setDeduct(MDPh); //构件扣分
                Double score = 100 - MDPh;
                score = Double.parseDouble(df.format(score));
                artifactsDTO.setScore(score); //构件评分
                artifactsDTO.setMDPh(MDPh);
                MDPhs.add(MDPh > 100 ? 100 : MDPh); //当值大于100时取100
            }
        }
        Double maxMDPh = MDPhs.size() > 0 ? Collections.max(MDPhs) : null; //要素中最大综合值
        Double BSI = maxMDPh != null ? 100 - maxMDPh : null;
        Double BCI = null;
        //求BCI评分
        for (ArtifactsDTO artifactsDTO : artifactsDTOList) {
            if (artifactsDTO.getActualWeight() == null) {
                log.error("########" + artifactsDTO.getArtifactsName() + "构件的权重值为" + artifactsDTO.getActualWeight());
            }
            if (artifactsDTO.getMDPh() != null && artifactsDTO.getActualWeight() != null) {
                if(artifactsDTO.getMDPh() >= 80){ //扣分大于80的评定等级不能大于D
                    map.put("specialLevel", 1.0);
                }
                // (100 - 综合扣分值) * 实际权重
                double mul = ArithmeticUtils.mul(ArithmeticUtils.sub(100, artifactsDTO.getMDPh()), artifactsDTO.getActualWeight());
                BCI = BCI != null ? BCI + mul : mul;
            }
        }
        map.put("BCI", BCI);
        map.put("BSI", BSI);
//        log.info("==========BSI值{}：" + BSI);
//        log.info("==========BCI值{}：" + BCI);
        return map;
    }

    /**
     * 跨的评分
     *
     * @param acrossDTOList 跨集合
     * @param acrossBCI     BCI集合
     * @param acrossBSI     BSI集合
     * @param type          0: 上部结构；1: 下部结构
     */
    private static String couAcrossDTO(List<AcrossDTO> acrossDTOList, List<Double> acrossBCI, List<Double> acrossBSI, int type) {
        String specialLevel = null;
        for (AcrossDTO acrossDTO : acrossDTOList) {
            List<ArtifactsDTO> artifactsDTOList = acrossDTO.getArtifactsDTOList(); //获取构件集合
            Map<String, Double> basisMap = couArtifact(artifactsDTOList);
            if (basisMap.get("specialLevel") != null) { //特殊评定等级
                specialLevel = basisMap.get("specialLevel") == 1.0 ? "D" : specialLevel;
            }
            //将跨的构件得出的BCI评分存入跨对象中
            acrossDTO.setRatingLevel(rating(basisMap.get("BCI"), null)); //评定等级
            if (0 == type) {
                acrossDTO.setBCIsi(basisMap.get("BCI"));
                acrossDTO.setBSIsi(basisMap.get("BSI"));
            } else {
                acrossDTO.setBCIxj(basisMap.get("BCI"));
                acrossDTO.setBSIxj(basisMap.get("BSI"));
            }
            if (acrossBCI != null && acrossBSI != null && basisMap.get("BCI") != null && basisMap.get("BSI") != null) {
                acrossBCI.add(basisMap.get("BCI"));
                acrossBSI.add(basisMap.get("BSI"));
            }
        }
        return specialLevel;
    }

    /**
     * 评价等级
     *
     * @param num          分数
     * @param specialLevel 特殊评定等级
     * @return
     */
    public static String rating(Double num, String specialLevel) {
        String rat = null;
        if (num != null) {
            if (num >= 90 && specialLevel == null) {
                rat = "A";
            } else if (num >= 80 && specialLevel == null) {
                rat = "B";
            } else if (num >= 66 && specialLevel == null) {
                rat = "C";
            } else if ((num >= 50 && specialLevel == null) || (specialLevel != null && num >= 50)) {
                rat = "D";
            } else {
                rat = "E";
            }
        }
        return rat;
    }

    /**
     * 特殊评价等级
     * 特殊病害超过时评定为不合格桥D级桥
     *
     * @param diseaseInstanceDTO 病害对象
     * @return 1.0 ：表示为不合格桥D级桥
     */
    public static Double specialRating(DiseaseInstanceDTO diseaseInstanceDTO) {
        Double level = null;
        if (diseaseInstanceDTO == null) {
            return null;
        }
        Long diseaseId = diseaseInstanceDTO.getDiseaseId();
        if (diseaseId != null) {
            if (RecordConstant.DISEASE_IDS.contains(Integer.valueOf(diseaseId.toString()))) {
                if (diseaseInstanceDTO.getDeduct() != null && diseaseInstanceDTO.getDeduct() > 80) {
                    level = 1.0;
                    log.warn("存在特殊病害桥等级评定为D级以下！！");
                }
            }
        }
        return level;
    }

    /**
     * 权重分配
     *
     * @param weightDTOList    原始权重分配
     * @param artifactsDTOList 要素集合
     * @return
     */
    public List<ElementWeightDTO> weightAllocation(List<ElementWeightDTO> weightDTOList, List<ArtifactsDTO> artifactsDTOList) {
        List<ElementWeightDTO> list = new ArrayList<>();
        double notWeightNum = 0; //未出现的权重
        double weightNum = 0; //出现的权重总和
        //找出未出现的权重值相加
        for (ElementWeightDTO elementWeightDTO : weightDTOList) {
            String elementName = elementWeightDTO.getElementName();
            int sign = 0;
            for (ArtifactsDTO artifactsDTO : artifactsDTOList) {
                String artifactsName = artifactsDTO.getArtifactsName();
                if (elementName != null && elementName.equals(artifactsName)) {
                    sign++;
                    //累加出现的权重
                    weightNum += elementWeightDTO.getWeightNum() != null ? elementWeightDTO.getWeightNum() : 0;

                }
            }
            if (sign == 0) {
                //累加未出现的权重
                notWeightNum += elementWeightDTO.getWeightNum() != null ? elementWeightDTO.getWeightNum() : 0;
            }
        }
        return list;
    }

    /**
     * 计算病害扣分值
     *
     * @param list       病害
     * @param bridgeArea 桥面面积
     * @return
     */
    public List<DiseaseInstanceDTO> couDiseaseInstanceDTO(List<DiseaseInstanceDTO> list, Double bridgeArea) {
        if (bridgeArea == null) {
            throw new BusinessException("桥面面积为空");
        }
        for (DiseaseInstanceDTO diseaseInstanceDTO : list) {
            Double deduct = null;
            String diseaseCode = diseaseInstanceDTO.getDiseaseCode();
            Double seamLength = diseaseInstanceDTO.getSeamLength();  //裂缝长度
            Double seamWidth = diseaseInstanceDTO.getSeamWidth();    //裂缝宽度
            Double number = diseaseInstanceDTO.getNumber(); //数量
            String degree = diseaseInstanceDTO.getDegree(); //程度
            //百分制
            if (diseaseInstanceDTO.getSeamLength() != null && diseaseInstanceDTO.getSeamWidth() != null) {
                if (DiseaseCodeEnum.WL.getCode().equals(diseaseCode)) {
                    deduct = couDeductByNum(DiseaseCodeEnum.WL, ArithmeticUtils.sub(seamLength, seamWidth), bridgeArea);
                } else if (DiseaseCodeEnum.CZ.getCode().equals(diseaseCode)) {
                    deduct = couDeductByNum(DiseaseCodeEnum.CZ, ArithmeticUtils.sub(seamLength, seamWidth), bridgeArea);
                }
            }

            //数字
            if (null != number) {
                if (DiseaseCodeEnum.KT.getCode().equals(diseaseCode)) {
                    deduct = couDeductByNum(DiseaseCodeEnum.KT, number);
                }
            }

            //文字
            if (!StringUtils.isEmpty(degree)) {
                if (DiseaseCodeEnum.HF.getCode().equals(diseaseCode)) {
                    deduct = couDeductByNum(DiseaseCodeEnum.HF, degree);
                }
            }
            diseaseInstanceDTO.setDeduct(deduct);
        }
        return list;
    }

    /**
     * 通过病害条件得到扣分值（百分制）
     *
     * @param codeEnum   病害枚举
     * @param area       面积
     * @param bridgeArea 桥面面积
     * @return
     */
    public Double couDeductByNum(DiseaseCodeEnum codeEnum, Double area, Double bridgeArea) {
        Double one = codeEnum.getOne();
        Double two = codeEnum.getTwo();
        double[] deducts = codeEnum.getDeducts();
        Double deduct = null;
        if (null == one) {
            throw new BusinessException("等级值为空");
        }
        if (null == two) {
            throw new BusinessException("等级值为空");
        }
        if (null == area) {
            throw new BusinessException("面积为空");
        }
        if (null == bridgeArea) {
            throw new BusinessException("桥面面积为空");
        }
        if (deducts.length != 3) {
            throw new BusinessException("扣分值长度错误");
        }
        double div = ArithmeticUtils.div(area, bridgeArea);
        if (div < one) {
            deduct = deducts[0];
        } else if (div >= one && div <= two) {
            deduct = deducts[1];
        } else {
            deduct = deducts[2];
        }
        return deduct;
    }

    /**
     * 通过病害条件得到扣分值（坑洞数字类型）
     *
     * @param codeEnum 病害枚举
     * @param num      数量
     * @return
     */
    public Double couDeductByNum(DiseaseCodeEnum codeEnum, Double num) {
        Double one = codeEnum.getOne();
        Double two = codeEnum.getTwo();
        double[] deducts = codeEnum.getDeducts();
        Double deduct = null;
        if (null == one) {
            throw new BusinessException("等级值为空");
        }
        if (null == two) {
            throw new BusinessException("等级值为空");
        }
        if (deducts.length != 3) {
            throw new BusinessException("扣分值长度错误");
        }
        if (null == num) {
            throw new BusinessException("值为空");
        }

        if (num.equals(one)) {
            deduct = deducts[0];
        } else if (num >= two) {
            deduct = deducts[2];
        } else {
            deduct = deducts[1];
        }
        return deduct;
    }

    /**
     * 通过病害条件得到扣分值（文字类型）
     *
     * @param codeEnum 病害枚举
     * @param name     文字名称
     * @return
     */
    public Double couDeductByNum(DiseaseCodeEnum codeEnum, String name) {
        Double one = codeEnum.getOne();
        Double two = codeEnum.getTwo();
        double[] deducts = codeEnum.getDeducts();
        Double deduct = null;
        if (null == one) {
            throw new BusinessException("等级值为空");
        }
        if (null == two) {
            throw new BusinessException("等级值为空");
        }
        if (deducts.length != 3) {
            throw new BusinessException("扣分值长度错误");
        }
        DiseaseEnum[] values = DiseaseEnum.values();
        for (DiseaseEnum value : values) {
            String name1 = value.getName();
            if (StringUtils.isEmpty(name1) && name1.equals(name)) {
                Double num = value.getNum();
                if (one.equals(num)) {
                    deduct = deducts[0];
                } else if (two.equals(num)) {
                    deduct = deducts[2];
                } else {
                    deduct = deducts[1];
                }
            }
        }
        return deduct;
    }


}





























