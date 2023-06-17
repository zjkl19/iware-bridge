package com.iware.bridge.evaluation.enums;

/**
 * 表名称
 *
 * @author: CWY
 * @Date: 2021/5/20 11:32
 */
public enum TableNameEnum {

    BRIDGE_UPPER("桥梁上部结构实例", "tb_bridge_supcomponent"),
    BRIDGE_LOWER("桥梁下部结构实例", "tb_bridge_subcomponent"),
    BRIDGE_DECK("桥梁桥面系实例", "tb_bridge_deck_component");

    private String name;
    private String table;

    TableNameEnum(String name, String table) {
        this.name = name;
        this.table = table;
    }

    public String getName() {
        return name;
    }


    public String getTable() {
        return table;
    }

}
