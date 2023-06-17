package com.iware.bridge.evaluation.enums;


import com.iware.bridge.evaluation.vo.ElementWeightDTO;

import java.util.Arrays;
import java.util.List;

/**
 * @author: CWY
 * @Date: 2021/5/14 10:21
 */
public class RecordConstant {

    //梁式桥
    public static final List<ElementWeightDTO> BRAM_BRIDGE = Arrays.asList(
            new ElementWeightDTO("桥面铺装", 0.30),
            new ElementWeightDTO("桥头平顺", 0.15),
            new ElementWeightDTO("伸缩装置", 0.25),
            new ElementWeightDTO("排水系统", 0.10),
            new ElementWeightDTO("人行道", 0.10),
            new ElementWeightDTO("栏杆或护栏", 0.10)
    );

    //人行天桥
    public static final List<ElementWeightDTO> PEDESTRIAN_BRIDGE = Arrays.asList(
            new ElementWeightDTO("桥面铺装", 0.40),
            new ElementWeightDTO("伸缩装置", 0.15),
            new ElementWeightDTO("排水系统", 0.20),
            new ElementWeightDTO("栏杆或护栏", 0.25)
    );

    public static final List<Integer> noFootbridge = Arrays.asList(1, 2, 4, 5, 6, 3);

    public static final List<Integer> footbridge = Arrays.asList(7,8);

    public static final List<String> BRAM_BRIDGE_NAME = Arrays.asList("桥面铺装", "桥头平顺", "伸缩装置", "排水系统", "人行道", "栏杆或护栏");

    public static final List<String> PEDESTRIAN_BRIDGE_NAME = Arrays.asList("桥面铺装", "伸缩装置", "排水系统", "栏杆或护栏");

    //特殊病害评定桥状态id
    public static final List<Integer> DISEASE_IDS = Arrays.asList(
            10, 30, 31, 35, 36, 37, 38, 39, 40, 41, 29, 44, 45, 47, 52, 72,
            56, 62, 69, 71);
}
