package com.iware.bridge.app.inspection.service;

import com.iware.bridge.app.assess.service.LoginUserService;
import com.iware.bridge.inspection.dao.PlanDetailExpDao;
import com.iware.bridge.inspection.vo.MaintainItemVO;
import com.iware.bridge.model.dao.global.PhotoDao;
import com.iware.bridge.model.dao.inspection.InspectionDiseaseInstanceDao;
import com.iware.bridge.model.dao.inspection.MaintainItemDao;
import com.iware.bridge.model.dao.inspection.PlanInfoDao;
import com.iware.bridge.model.dao.user.UnitDao;
import com.iware.bridge.model.entity.global.Photo;
import com.iware.bridge.model.entity.inspection.InspectionDiseaseInstance;
import com.iware.bridge.model.entity.inspection.MaintainItem;
import com.iware.bridge.model.entity.inspection.PlanInfo;
import com.iware.common.enums.FileTypeEnum;
import com.iware.common.enums.GlobalResultEnum;
import com.iware.common.exception.BusinessException;
import com.iware.common.pojo.FileData;
import com.iware.common.utils.FileUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class AppMaintainServiceImpl implements AppMaintainService{

    @Resource
    private MaintainItemDao maintainItemDao;

    @Resource
    private PlanInfoDao planInfoDao;

    @Resource
    private PhotoDao photoDao;

    @Resource
    private LoginUserService loginUserService;

    @Resource
    private UnitDao unitDao;

    @Resource
    private PlanDetailExpDao planDetailExpDao;

    @Resource
    private InspectionDiseaseInstanceDao inspectionDiseaseInstanceDao;

    @Value("${file.upload-path}")
    private String path;


    @Override
    public void addMaintainItem(MaintainItem maintainItem) {
        maintainItemDao.save(maintainItem);
    }

    @Override
    public void updMaintainItem(MaintainItemVO maintainItem, ArrayList<MultipartFile> files) {
        FileTypeEnum fileTypeEnum=FileTypeEnum.MAINTAIN_PHOTO;
        maintainItem.setStatus(1);
        ArrayList<String> toDeletePath=new ArrayList<>();
        PlanInfo planInfo=planInfoDao.findById(maintainItem.getPlanId());
        Integer isAllComplete=planDetailExpDao.planAllComplete(maintainItem.getPlanId(),maintainItem.getId());
        if(isAllComplete==1){ //全部完成
            planInfo.setStatus(2);
        }else{
            planInfo.setStatus(1);
        }
        planInfoDao.update(planInfo);
        InspectionDiseaseInstance diseaseInstance=inspectionDiseaseInstanceDao.findById(maintainItem.getDiseaseInstanceId());
        if(diseaseInstance!=null) {
            diseaseInstance.setStatus(2);
            inspectionDiseaseInstanceDao.update(diseaseInstance);
        }
        if(maintainItem.getMaintenanceUnit()==null){
            maintainItem.setMaintenanceUnit(unitDao.findById(loginUserService.getUser().getUnitId()).getName());
//            ProjectAppoint appointCondition=new ProjectAppoint();
//            appointCondition.setReceiveUserId(loginUserService.getUser().getUserId());
//            appointCondition.setProjectId(planInfo.getProjectId());
//            if(projectAppointDao.query(appointCondition).size()>0) {
//                ProjectAppoint result = projectAppointDao.query(appointCondition).get(0);
//                maintainItem.setMaintenanceUnit(userDao.findById(result.getAppointUserId()).getRealName());
//            }
        }
        maintainItem.setCreator(loginUserService.getUser().getRealName());
        maintainItemDao.update(maintainItem);
        Photo deleteCondition=new Photo();
        deleteCondition.setTargetId(maintainItem.getId());
        deleteCondition.setType(6);
        List<Photo> toDelete = photoDao.query(deleteCondition);
        deleteCondition.setType(7);
        toDelete.addAll(photoDao.query(deleteCondition));
        deleteCondition.setType(8);
        toDelete.addAll(photoDao.query(deleteCondition));
        for(Photo photo:toDelete){
            toDeletePath.add(photo.getPath()); //删除图片
            photoDao.deleteById(photo.getId());
        }
        HashMap<String,MultipartFile> fileByName=new HashMap<>();
        for(MultipartFile file : files){
            fileByName.put(file.getOriginalFilename(),file);
        }
        ArrayList<Photo> toSave=new ArrayList<>();
        for(Photo photo:maintainItem.getBeforeList()){
            if(!photo.getPath().startsWith("file")){//并非从手机上传,需要复制一张原有图片
                FileItem fileItem = createFileItem(path+photo.getPath(),photo.getName());
                if(fileItem==null){
                    continue;
                }
                MultipartFile copyFile = new CommonsMultipartFile(fileItem);
                FileData fileInfo = FileUtil.uploadFile(copyFile, fileTypeEnum, photo.getTargetId());
                photo.setPath(fileInfo.getFilePath());
            }else
            if (files.size() > 0) {
                try {
                    MultipartFile nowfile=fileByName.getOrDefault(photo.getName(),null);
                    if(nowfile!=null){
                        FileData fileInfo = FileUtil.uploadFile(nowfile, fileTypeEnum, photo.getTargetId());
                        photo.setPath(fileInfo.getFilePath());
                    }
                }catch (Exception e) {
                    if (!CollectionUtils.isEmpty(maintainItem.getBeforeList())) {
                        for (Photo image : maintainItem.getBeforeList()) {
                            FileUtil.delFileData(image.getPath());
                        }
                    }
                    throw new BusinessException(GlobalResultEnum.FILE_UPLOAD_ERROR);
                }
            }
            photo.setType(6);
            photo.setStatus(1);
            toSave.add(photo);
        }
        for(Photo photo:maintainItem.getProgressList()){
            if(!photo.getPath().startsWith("file")){//并非从手机上传,需要复制一张原有图片
                FileItem fileItem = createFileItem(path+photo.getPath(),photo.getName());
                if(fileItem==null){
                    continue;
                }
                MultipartFile copyFile = new CommonsMultipartFile(fileItem);
                FileData fileInfo = FileUtil.uploadFile(copyFile, fileTypeEnum, photo.getTargetId());
                photo.setPath(fileInfo.getFilePath());
            }else
            if (files.size() > 0) {
                try {
                    MultipartFile nowfile=fileByName.getOrDefault(photo.getName(),null);
                    if(nowfile!=null){
                        FileData fileInfo = FileUtil.uploadFile(nowfile, fileTypeEnum, photo.getTargetId());
                        photo.setPath(fileInfo.getFilePath());
                    }
                }catch (Exception e) {
                    if (!CollectionUtils.isEmpty(maintainItem.getProgressList())) {
                        for (Photo image : maintainItem.getProgressList()) {
                            FileUtil.delFileData(image.getPath());
                        }
                    }
                    throw new BusinessException(GlobalResultEnum.FILE_UPLOAD_ERROR);
                }
            }
            photo.setType(7);
            photo.setStatus(1);
            toSave.add(photo);
        }
        for(Photo photo:maintainItem.getAfterList()){
            if(!photo.getPath().startsWith("file")){//并非从手机上传,需要复制一张原有图片
                FileItem fileItem = createFileItem(path+photo.getPath(),photo.getName());
                if(fileItem==null){
                    continue;
                }
                MultipartFile copyFile = new CommonsMultipartFile(fileItem);
                FileData fileInfo = FileUtil.uploadFile(copyFile, fileTypeEnum, photo.getTargetId());
                photo.setPath(fileInfo.getFilePath());
            }else
            if (files.size() > 0) {
                try {
                    MultipartFile nowfile=fileByName.getOrDefault(photo.getName(),null);
                    if(nowfile!=null){
                        FileData fileInfo = FileUtil.uploadFile(nowfile, fileTypeEnum, photo.getTargetId());
                        photo.setPath(fileInfo.getFilePath());
                    }
                }catch (Exception e) {
                    if (!CollectionUtils.isEmpty(maintainItem.getAfterList())) {
                        for (Photo image : maintainItem.getAfterList()) {
                            FileUtil.delFileData(image.getPath());
                        }
                    }
                    throw new BusinessException(GlobalResultEnum.FILE_UPLOAD_ERROR);
                }
            }
            photo.setType(8);
            photo.setStatus(1);
            toSave.add(photo);
        }
        if (toSave.size()>0){
            photoDao.batchSave(toSave);
        }
        for(String p:toDeletePath){
            FileUtil.delFileData(p);
        }
    }

    private static FileItem createFileItem(String filePath,String fileName) {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        String textFieldName = "textField";
        FileItem item = factory.createItem(textFieldName, "text/plain", true,
                fileName);
        File newfile = new File(filePath);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try{
            FileInputStream fis = new FileInputStream(newfile);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192))
                    != -1)
            {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        }catch (IOException e){
            return null;
        }
        return item;
    }

}
