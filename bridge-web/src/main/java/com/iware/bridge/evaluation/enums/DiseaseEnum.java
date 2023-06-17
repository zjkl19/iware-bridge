package com.iware.bridge.evaluation.enums;

/**
 * 病害等级枚举
 *
 * @author: CWY
 * @Date: 2021/5/13 14:19
 */
public enum DiseaseEnum {

    NONE("无", 1.0),
    HALF_THROUGH("半贯通", 2.0),
    THROUGH("贯通", 3.0),
    //梁桥式
    BEAM_BRIDGE_FLOOR("梁桥式——桥面系", 0.15),
    BEAM_BRIDGE_UPPER("梁桥式——上部结构", 0.40),
    BEAM_BRIDGE_LOWER("梁桥式——下部结构", 0.45),
    //拱桥
    ARCH_BRIDGE_FLOOR("拱桥——桥面系", 0.10),
    ARCH_BRIDGE_UPPER("拱桥——上部结构", 0.45),
    ARCH_BRIDGE_LOWER("拱桥——下部结构", 0.45),
    //人行天桥
    PEDESTRIAN_BRIDGE_FLOOR("人行天桥——桥面系", 0.15),
    PEDESTRIAN_BRIDGE_UPPER("人行天桥——上部结构", 0.45),
    PEDESTRIAN_BRIDGE_LOWER("人行天桥——下部结构", 0.40)
    ;

    private String name;
    private Double num;

    DiseaseEnum(String name, Double num) {
        this.name = name;
        this.num = num;
    }

    public String getName() {
        return name;
    }


    public Double getNum() {
        return num;
    }

}












