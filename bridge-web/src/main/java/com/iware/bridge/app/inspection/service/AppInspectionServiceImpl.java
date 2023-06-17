package com.iware.bridge.app.inspection.service;

import com.iware.bridge.app.assess.service.LoginUserService;
import com.iware.bridge.inspection.dao.InspectionDiseaseExpDao;
import com.iware.bridge.inspection.dao.PlanDetailExpDao;
import com.iware.bridge.inspection.dao.PlanExpDao;
import com.iware.bridge.inspection.vo.DiseaseInstanceExpVo;
import com.iware.bridge.inspection.vo.PlanDetailVO;
import com.iware.bridge.inspection.vo.PlanFilter;
import com.iware.bridge.inspection.vo.PlanVO;
import com.iware.bridge.model.dao.global.PhotoDao;
import com.iware.bridge.model.dao.inspection.DiseaseInstanceOptionRelDao;
import com.iware.bridge.model.dao.inspection.InspectionDiseaseInstanceDao;
import com.iware.bridge.model.dao.inspection.PlanDetailDao;
import com.iware.bridge.model.dao.inspection.PlanInfoDao;
import com.iware.bridge.model.dao.user.UnitDao;
import com.iware.bridge.model.dao.user.UserDao;
import com.iware.bridge.model.entity.global.Photo;
import com.iware.bridge.model.entity.inspection.DiseaseInstanceOptionRel;
import com.iware.bridge.model.entity.inspection.InspectionDiseaseInstance;
import com.iware.bridge.model.entity.inspection.PlanInfo;
import com.iware.bridge.model.entity.user.User;
import com.iware.common.enums.FileTypeEnum;
import com.iware.common.enums.GlobalResultEnum;
import com.iware.common.exception.BusinessException;
import com.iware.common.pojo.FileData;
import com.iware.common.properties.PowerProperties;
import com.iware.common.utils.FileUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AppInspectionServiceImpl implements AppInspectionService{

    @Resource
    private PlanDetailDao planDetailDao;

    @Resource
    private PhotoDao photoDao;

    @Resource
    private InspectionDiseaseInstanceDao inspectionDiseaseInstanceDao;

    @Resource
    private InspectionDiseaseExpDao inspectionDiseaseExpDao;

    @Resource
    private PlanDetailExpDao planDetailExpDao;

    @Resource
    private DiseaseInstanceOptionRelDao diseaseInstanceOptionRelDao;

    @Resource
    private PlanInfoDao planInfoDao;

    @Autowired
    private PowerProperties power;

    @Autowired
    private PlanExpDao planExpDao;

    @Autowired
    private LoginUserService loginUserServ;

    @Resource
    private UnitDao unitDao;

    @Resource
    private UserDao userDao;

    @Value("${file.upload-path}")
    private String path;


    @Override
    public void updPlanDetail(PlanDetailVO planDetailVO,ArrayList<MultipartFile> files) {
        Logger logger= LoggerFactory.getLogger(AppInspectionServiceImpl.class);
        logger.debug(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"filelong0"+files.size());
        FileTypeEnum fileTypeEnum=FileTypeEnum.INSPECTION_PHOTO;
        planDetailVO.setStatus(1);
        //之后需要删除的图片
        ArrayList<String> toDeletePath=new ArrayList<>();
        //更改planInfo状态
        Integer detailLocation=planDetailExpDao.planDetailInPlanInfo(planDetailVO.getId(),planDetailVO.getPlanId());
        PlanInfo toUpdate=planInfoDao.findById(planDetailVO.getPlanId());
        if(detailLocation!=null){
            if(detailLocation==0){
                toUpdate.setStatus(2);
            }else{
                toUpdate.setStatus(1);
            }
            planInfoDao.update(toUpdate);
        }
        if(planDetailVO.getInspectionUnit()==null) {
            planDetailVO.setInspectionUnit(unitDao.findById(loginUserServ.getUser().getUnitId()).getName());
        }
        planDetailDao.update(planDetailVO);
        InspectionDiseaseInstance condition=new InspectionDiseaseInstance();
        condition.setPlanDetailId(planDetailVO.getId());
        Photo photoCondition=new Photo();
        photoCondition.setType(5);
        for(InspectionDiseaseInstance instance:inspectionDiseaseInstanceDao.query(condition)){
            inspectionDiseaseInstanceDao.deleteById(instance.getId());
            photoCondition.setTargetId(instance.getId());
            List<Photo> toDeletePhoto=photoDao.query(photoCondition);
            for(Photo deletePhoto:toDeletePhoto){
                toDeletePath.add(deletePhoto.getPath());//删除图片
                photoDao.deleteById(deletePhoto.getId());
            }
        }
        List<Photo> photoList=planDetailVO.getPhotoList();
        HashMap<Integer,List<Photo>> photoMap=new HashMap<>();
        for(Photo photo:photoList){
            List<Photo> photos;
            if(photoMap.containsKey(photo.getTargetId())){
                photos=photoMap.get(photo.getTargetId());
            }else{
                photos=new ArrayList<>();
            }
            photos.add(photo);
            photoMap.put(photo.getTargetId(),photos);
        }
        HashMap<String,MultipartFile> fileByName=new HashMap<>();
        for(MultipartFile file : files){
            fileByName.put(file.getOriginalFilename(),file);
        }
        for(DiseaseInstanceExpVo instance:planDetailVO.getDiseaseInstanceList()){
            Integer diseaseCode=instance.getId();
            instance.setId(null);
            if(instance.getStrategy()==4||instance.getStrategy()==7||instance.getStrategy()==9){
                instance.setStatus(2);
            }else{
                instance.setStatus(0);
            }
            inspectionDiseaseInstanceDao.save(instance);
            Integer diseaseId=instance.getId();
            for(Integer optionId:instance.getOptionIds()){
                DiseaseInstanceOptionRel rel=new DiseaseInstanceOptionRel();
                rel.setInspectionDiseaseOptionId(optionId);
                rel.setInspectionDiseaseInstanceId(instance.getId());
                diseaseInstanceOptionRelDao.save(rel);
            }
            for(Photo photo:photoMap.getOrDefault(diseaseCode,new ArrayList<>())){
                photo.setTargetId(diseaseId);
                logger.debug(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"filelong"+files.size());
                if(!photo.getPath().startsWith("file")){//并非从手机上传,需要复制一张原有图片
                    FileItem fileItem = createFileItem(path+photo.getPath(),photo.getName());
                    if(fileItem==null){
                        continue;
                    }
                    MultipartFile copyFile = new CommonsMultipartFile(fileItem);
                    FileData fileInfo = FileUtil.uploadFile(copyFile, fileTypeEnum, photo.getTargetId());
                    photo.setPath(fileInfo.getFilePath());
                    photo.setStatus(1);
                    continue;
                }
                if (fileByName.size() > 0) {
                    logger.debug(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"phoname"+photo.getName());
                    try {
                        MultipartFile nowfile=fileByName.getOrDefault(photo.getName(),null);
                        if(nowfile!=null){
                            logger.debug(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"oriname"+nowfile.getOriginalFilename());
                            FileData fileInfo = FileUtil.uploadFile(nowfile, fileTypeEnum, photo.getTargetId());
                            photo.setPath(fileInfo.getFilePath());
                            photo.setStatus(1);
                        }
                    } catch (Exception e) {
                        if (!CollectionUtils.isEmpty(photoMap.get(diseaseCode))) {
                            for (Photo image : planDetailVO.getPhotoList()) {
                                FileUtil.delFileData(image.getPath());
                            }
                        }
//                        throw e;
                        throw new BusinessException(GlobalResultEnum.FILE_UPLOAD_ERROR);
                    }
                }
            }
            if(photoMap.getOrDefault(diseaseCode,new ArrayList<>()).size()>0) {
                photoDao.batchSave(photoMap.get(diseaseCode));
            }
        }
        for(String p:toDeletePath){
            FileUtil.delFileData(p);
        }
    }

    @Override
    public void addDisease(InspectionDiseaseInstance instance) {
        inspectionDiseaseInstanceDao.save(instance);
    }

    @Override
    public InspectionDiseaseInstance selDisease(Integer maintainItemId) {
        return inspectionDiseaseExpDao.selDiseaseByMaintainId(maintainItemId);
    }

    @Override
    public void delDisease(Integer diseaseInstanceId) {
        inspectionDiseaseInstanceDao.deleteById(diseaseInstanceId);
    }

    @Override
    public void updDisease(InspectionDiseaseInstance instance) {
        inspectionDiseaseInstanceDao.update(instance);
    }

    @Override
    public void uploadFiles(Photo photo, MultipartFile[] files) {
        List<Photo> photoList = new ArrayList<>();
        FileTypeEnum fileTypeEnum=FileTypeEnum.MAINTAIN_PHOTO;
        if(photo.getType()==5){
            fileTypeEnum=FileTypeEnum.INSPECTION_PHOTO;
        }
        if (files.length > 0) {
            try {
                int i=1;
                for(MultipartFile file : files) {
                    FileData fileInfo = FileUtil.uploadFile(file, fileTypeEnum, photo.getTargetId());
                    photo.setPath(fileInfo.getFilePath());
                    if(photo.getType()!=5){
                        String diseaseName = inspectionDiseaseExpDao.selDiseaseByMaintainId(photo.getTargetId()).getDiseaseName();
                        String photoName=diseaseName;
                        switch (photo.getType()){
                            case 6:
                                photoName+="维修前-";
                                break;
                            case 7:
                                photoName+="维修中-";
                                break;
                            case 8:
                                photoName+="维修后-";
                                break;
                            default:
                        }
                        photoName+=i;
                        i++;
                        photo.setName(photoName);
                    }else {
                        photo.setName(fileInfo.getName());
                    }
                    photoList.add(photo);
                }
            } catch (Exception e) {
                if (!CollectionUtils.isEmpty(photoList)) {
                    for (Photo image : photoList) {
                        FileUtil.delFileData(image.getPath());
                    }
                }
                throw new BusinessException(GlobalResultEnum.FILE_UPLOAD_ERROR);
            }
            photoDao.batchSave(photoList);
        }
    }

    @Override
    public List<PlanVO> listPlan(PlanFilter filter) {
        Integer powerId;
        if (filter.getType().equals(1)) {
            powerId = power.getInspectionPower();
        } else {
            powerId = power.getMaintainPower();
        }
        User loginUser = loginUserServ.getUser();
        List<PlanVO> result=planExpDao.listPlan(1,loginUser.getUnitId(),
                 powerId, filter);
        return result;
    }

    @Override
    public PlanDetailVO selectLastInspection(Integer planDetailId) {
        PlanDetailVO result=planDetailExpDao.selectLastInspection(planDetailId);
        if(result==null){
            return null;
        }
        result.setDiseaseInstanceList(planDetailExpDao.selectDiseaseByDetailId(result.getId()));
//        ArrayList<Photo> photos=new ArrayList<>();
        Photo condition=new Photo();
        condition.setType(5);
        for(DiseaseInstanceExpVo instance:result.getDiseaseInstanceList()) {
            condition.setTargetId(instance.getId());
            List<Photo> thisPhotos=photoDao.query(condition);
            instance.setPhotoList(thisPhotos);
//            photos.addAll(thisPhotos);
        }
//        result.setPhotoList(photos);
        return result;
    }

    @Override
    public List<DiseaseInstanceExpVo> selectDiseaseByDetailId(Integer planDetailId) {
        return planDetailExpDao.selectDiseaseByDetailId(planDetailId);
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
