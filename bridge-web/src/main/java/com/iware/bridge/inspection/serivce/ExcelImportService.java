package com.iware.bridge.inspection.serivce;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author LBX
 * @date 2021-10-25
 */

public interface ExcelImportService {

    public Integer importExcelInspectionRecord(MultipartFile excel);

    public Integer importExcelMaintainRecord(MultipartFile excel);

}
