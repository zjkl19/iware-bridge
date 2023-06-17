package com.iware.bridge.app.inspection.service;

import com.iware.bridge.inspection.vo.MaintainItemVO;
import com.iware.bridge.model.entity.inspection.MaintainItem;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public interface AppMaintainService {

    public void addMaintainItem(MaintainItem maintainItem);

    public void updMaintainItem(MaintainItemVO maintainItem, ArrayList<MultipartFile> files);
}
