package com.iware.bridge.evaluation.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author WJP
 * @date 2021-8-13
 */
@Repository
public interface DeductionExpDao {

    /**数字型的判断扣分值**/
    public Integer selectDeductByInteger(@Param("diseaseId") Integer diseaseId, @Param("degree") String degree,
                                         @Param("partType") Integer partType);

    /**字符型/百分比判断扣分值**/
    public Integer selectDeductByString(@Param("diseaseId") Integer diseaseId, @Param("degree") String degree,
                                        @Param("partType") Integer partType);
}
