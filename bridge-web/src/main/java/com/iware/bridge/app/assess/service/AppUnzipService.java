package com.iware.bridge.app.assess.service;


import com.iware.bridge.model.entity.evaluation.MonitorPlanStructureRel;

import java.util.List;

public interface AppUnzipService {

    public List<MonitorPlanStructureRel> correctDatabase(String filePath, String fileName, String lastFilePath, String ImageFilePath);

}
