package com.iware.bridge.evaluation.enums;


import com.iware.bridge.evaluation.vo.ElementWeightDTO;

import java.util.Arrays;
import java.util.List;

/**
 * @author: CWY
 * @Date: 2021/8/10 9:51
 */
public enum BridgeTypeEnum {
    UPPER_1(Arrays.asList(
            new ElementWeightDTO("主梁", 0.60),
            new ElementWeightDTO("横向联系", 0.40)
    )),
    UPPER_2(Arrays.asList(
            new ElementWeightDTO("悬臂梁", 0.60),
            new ElementWeightDTO("挂梁", 0.20),
            new ElementWeightDTO("挂梁支座", 0.10),
            new ElementWeightDTO("防落梁装置", 0.10)
    )),
    UPPER_3(Arrays.asList(
            new ElementWeightDTO("主梁", 0.80),
            new ElementWeightDTO("横向联系", 0.20)
    )),
    UPPER_4(Arrays.asList(
            new ElementWeightDTO("桁片", 0.50),
            new ElementWeightDTO("主节点", 0.10),
            new ElementWeightDTO("纵梁", 0.20),
            new ElementWeightDTO("横梁", 0.10),
            new ElementWeightDTO("连接件", 0.10)
    )),
    UPPER_5(Arrays.asList(
            new ElementWeightDTO("主拱圈", 0.70),
            new ElementWeightDTO("横向联系", 0.30)
    )),
    UPPER_6(Arrays.asList(
            new ElementWeightDTO("主拱圈", 0.50),
            new ElementWeightDTO("拱上构造", 0.20),
            new ElementWeightDTO("横向联系", 0.30)
    )),
    UPPER_7(Arrays.asList(
            new ElementWeightDTO("主梁", 0.55),
            new ElementWeightDTO("横向联系", 0.35),
            new ElementWeightDTO("外部装饰板", 0.10)
    )),
    UPPER_8(Arrays.asList(
            new ElementWeightDTO("桁片", 0.48),
            new ElementWeightDTO("主节点", 0.08),
            new ElementWeightDTO("纵梁", 0.18),
            new ElementWeightDTO("横梁", 0.08),
            new ElementWeightDTO("连接件", 0.08),
            new ElementWeightDTO("外部装饰板", 0.10)
    )),

    LOWER_1_1(Arrays.asList(
            new ElementWeightDTO("盖梁", 0.15),
            new ElementWeightDTO("墩身", 0.30),
            new ElementWeightDTO("基础", 0.40),
            new ElementWeightDTO("支座", 0.15)
    )),
    LOWER_1_2(Arrays.asList(
            new ElementWeightDTO("台帽", 0.15),
            new ElementWeightDTO("台身", 0.20),
            new ElementWeightDTO("基础", 0.40),
            new ElementWeightDTO("耳墙", 0.10),
             new ElementWeightDTO("支座", 0.15)
    )),

    LOWER_2_1(Arrays.asList(
            new ElementWeightDTO("盖梁", 0.15),
            new ElementWeightDTO("墩身", 0.30),
            new ElementWeightDTO("基础", 0.40),
            new ElementWeightDTO("支座", 0.15)
    )),
    LOWER_2_2(Arrays.asList(
            new ElementWeightDTO("台帽", 0.15),
            new ElementWeightDTO("台身", 0.20),
            new ElementWeightDTO("基础", 0.40),
            new ElementWeightDTO("耳墙", 0.10),
            new ElementWeightDTO("支座", 0.15)
    )),

    LOWER_3_1(Arrays.asList(
            new ElementWeightDTO("盖梁", 0.15),
            new ElementWeightDTO("墩身", 0.30),
            new ElementWeightDTO("基础", 0.40),
            new ElementWeightDTO("支座", 0.15)
    )),
    LOWER_3_2(Arrays.asList(
            new ElementWeightDTO("台帽", 0.15),
            new ElementWeightDTO("台身", 0.20),
            new ElementWeightDTO("基础", 0.40),
            new ElementWeightDTO("耳墙", 0.10),
            new ElementWeightDTO("支座", 0.15)
    )),

    LOWER_4_1(Arrays.asList(
            new ElementWeightDTO("盖梁", 0.15),
            new ElementWeightDTO("墩身", 0.30),
            new ElementWeightDTO("基础", 0.40),
            new ElementWeightDTO("支座", 0.15)
    )),
    LOWER_4_2(Arrays.asList(
            new ElementWeightDTO("台帽", 0.15),
            new ElementWeightDTO("台身", 0.20),
            new ElementWeightDTO("基础", 0.40),
            new ElementWeightDTO("耳墙", 0.10),
            new ElementWeightDTO("支座", 0.15)
    )),

    LOWER_5_1(Arrays.asList(
            new ElementWeightDTO("盖梁", 0.10),
            new ElementWeightDTO("墩身", 0.30),
            new ElementWeightDTO("基础", 0.45),
            new ElementWeightDTO("拱脚", 0.15)
    )),
    LOWER_5_2(Arrays.asList(
            new ElementWeightDTO("台帽", 0.10),
            new ElementWeightDTO("台身", 0.30),
            new ElementWeightDTO("基础", 0.35),
            new ElementWeightDTO("耳墙", 0.10),
            new ElementWeightDTO("拱脚", 0.15)
    )),

    LOWER_6_1(Arrays.asList(
            new ElementWeightDTO("盖梁", 0.10),
            new ElementWeightDTO("墩身", 0.30),
            new ElementWeightDTO("基础", 0.45),
            new ElementWeightDTO("拱脚", 0.15)
    )),
    LOWER_6_2(Arrays.asList(
            new ElementWeightDTO("台帽", 0.10),
            new ElementWeightDTO("台身", 0.30),
            new ElementWeightDTO("基础", 0.35),
            new ElementWeightDTO("耳墙", 0.10),
            new ElementWeightDTO("拱脚", 0.15)
    )),

    LOWER_7_1(Arrays.asList(
            new ElementWeightDTO("盖梁", 0.18),
            new ElementWeightDTO("墩身", 0.34),
            new ElementWeightDTO("基础", 0.20),
            new ElementWeightDTO("外部装饰板", 0.10),
            new ElementWeightDTO("支座", 0.18)
    )),
    LOWER_7_2(Arrays.asList(
            new ElementWeightDTO("台帽", 0.20),
            new ElementWeightDTO("台身", 0.40),
            new ElementWeightDTO("基础", 0.20),
            new ElementWeightDTO("支座", 0.20)
    )),

    LOWER_8_1(Arrays.asList(
            new ElementWeightDTO("盖梁", 0.18),
            new ElementWeightDTO("墩身", 0.34),
            new ElementWeightDTO("基础", 0.20),
            new ElementWeightDTO("外部装饰板", 0.10),
            new ElementWeightDTO("支座", 0.18)
    )),
    LOWER_8_2(Arrays.asList(
            new ElementWeightDTO("台帽", 0.20),
            new ElementWeightDTO("台身", 0.40),
            new ElementWeightDTO("基础", 0.20),
            new ElementWeightDTO("支座", 0.20)
    )),


    ;

    private List<ElementWeightDTO> weightDTO;

    BridgeTypeEnum(List<ElementWeightDTO> weightDTO) {
        this.weightDTO = weightDTO;
    }

    public List<ElementWeightDTO> getWeightDTO() {
        return weightDTO;
    }

}
