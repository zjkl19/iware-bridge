package com.iware.bridge.evaluation.enums;

/**
 * 病害名称及编号
 *
 * @author: CWY
 * @Date: 2021/5/13 14:41
 */
public enum DiseaseCodeEnum {

    WL("网裂或龟裂", "WL", 3.0, 10.0, new double[]{5.0, 15.0, 40.0}),
    CZ("波浪及车辙", "CZ", 3.0, 10.0, new double[]{5.0, 15.0, 40.0}),
    KT("坑洞", "KT", 1.0, 3.0, new double[]{50.0, 65.0, 80.0}),
    HF("桥面贯通横缝", "HF", DiseaseEnum.NONE.getNum(), DiseaseEnum.THROUGH.getNum(), new double[]{0.0, 5.0, 15.0})
    ;

    private String name; //病害名称
    private String code; //病害编号
    private Double one;
    private Double two;
    private double[] deducts;

    DiseaseCodeEnum(String name, String code, Double one, Double two, double[] deducts) {
        this.name = name;
        this.code = code;
        this.one = one;
        this.two = two;
        this.deducts = deducts;
    }

    public String getName() {
        return name;
    }


    public String getCode() {
        return code;
    }


    public Double getOne() {
        return one;
    }


    public Double getTwo() {
        return two;
    }


    public double[] getDeducts() {
        return deducts;
    }

}
