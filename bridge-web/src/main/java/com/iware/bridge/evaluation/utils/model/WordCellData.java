package com.iware.bridge.evaluation.Utils.model;

public class WordCellData {
    private String name;//文本
    private Integer value;//宽度
    private Integer alignment;//对齐方式
    private String fontFamily;//字体
    private int fontSize;//字号
    private int height;//高度
    private int bold;//加粗
    private int is;//换行标识符

    public int getIs() {
        return is;
    }

    public void setIs(int is) {
        this.is = is;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getAlignment() {
        return alignment;
    }

    public void setAlignment(Integer alignment) {
        this.alignment = alignment;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }


    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getBold() {
        return bold;
    }

    public void setBold(int bold) {
        this.bold = bold;
    }

    @Override
    public String toString() {
        return "WordCellData{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", alignment=" + alignment +
                ", fontFamily='" + fontFamily + '\'' +
                ", fontSize=" + fontSize +
                ", height=" + height +
                ", bold=" + bold +
                '}';
    }
}
