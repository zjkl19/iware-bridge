package com.iware.bridge.evaluation.vo;

import com.iware.bridge.model.entity.evaluation.DiseaseInstance;

/**
 * @author: wjp
 * @date: 2021年10月13日14:37:38
 * @since 1.0
 */
public class DiseaseInstanceVO extends DiseaseInstance {

    private Integer levelLimit;

    public Integer getLevelLimit() {
        return levelLimit;
    }

    public void setLevelLimit(Integer levelLimit) {
        this.levelLimit = levelLimit;
    }
}
