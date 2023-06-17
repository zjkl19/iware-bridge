package com.iware.bridge.inspection.serivce;

import com.iware.bridge.inspection.vo.InspectionDiseaseInstanceVO;
import com.iware.bridge.inspection.vo.InspectionWordDataVo;
import com.iware.bridge.inspection.vo.MaintainItemVO;
import com.iware.common.exception.BusinessException;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author LBX
 * @date 2021-8-22
 */
public class WordMapData {

    private static WordMapData instance;

    private WordMapData(){
        super();
    }

    public static WordMapData getInstance(){
        if(instance == null){
            instance = new WordMapData();
        }
        return instance;
    }

    public Map<String, Object> getReplaceMapInspection(String creator, InspectionWordDataVo report,
                                                      List<InspectionDiseaseInstanceVO> diseases){
        if(report == null){
            throw new BusinessException("计划详情不存在");
        }
        HashMap<String, Object> replaceWordMap = new HashMap<>();
        //巡查map填充
        HashMap<String, List<InspectionDiseaseInstanceVO>> diseaseInstanceVOHashMap = new HashMap<>();
        for(InspectionDiseaseInstanceVO vo : diseases){
            List<InspectionDiseaseInstanceVO> list;
            if (diseaseInstanceVOHashMap.containsKey(vo.getDamageType())){
                list = diseaseInstanceVOHashMap.get(vo.getDamageType());
            }else{
                list = new ArrayList<>();
            }
            list.add(vo);
            diseaseInstanceVOHashMap.put(vo.getDamageType(), list);
        }
        String headData = "桥名:" + report.getStructureName() +
                "\t巡检日期:" + report.getInspectionYear() + "年" +
                report.getInspectionMonth() + "月" +
                report.getInspectionDate() + "日" +
                report.getInspectionEve() + "午\t星期" +
                insDayToString(report.getInspectionDay() - 1) + "\t天气" +
                report.getInspectionWeather();
        replaceWordMap.put("${headData}", headData);
        if(diseaseInstanceVOHashMap.containsKey("桥、桥区施工")){
            InspectionDiseaseInstanceVO toSet=diseaseInstanceVOHashMap.get("桥、桥区施工").get(0);
            replaceWordMap.put("${b9condition}","有");
            replaceWordMap.put("${b9disease}",toSet.getOptionName());
            replaceWordMap.put("${b9remark}",toSet.getRemarks());
        }else{
            replaceWordMap.put("${b9condition}","无");
            replaceWordMap.put("${b9disease}","-");
            replaceWordMap.put("${b9remark}","-");
        }
        if(diseaseInstanceVOHashMap.containsKey("其他危及行车、行船、行人安全的病害因素")){
            List<InspectionDiseaseInstanceVO> list=diseaseInstanceVOHashMap.get("其他危及行车、行船、行人安全的病害因素");
            replaceWordMap.put("${b10condition}","有");
            replaceWordMap.put("${b10disease}",list.get(0).getOptionName());
        }else{
            replaceWordMap.put("${b10condition}","无");
            replaceWordMap.put("${b10disease}","-");
        }
        if(diseaseInstanceVOHashMap.containsKey("其他说明")) {
            InspectionDiseaseInstanceVO toSet = diseaseInstanceVOHashMap.get("其他说明").get(0);
            replaceWordMap.put("${botherremark}",toSet.getRemarks());
        }else{
            replaceWordMap.put("${botherremark}","-");
        }
        String footData="核对存档人:\t\t\t\t"+"巡查人:"+(report.getInspector()==null?"":report.getInspector());
        replaceWordMap.put("${footData}",footData);
        return replaceWordMap;
    }

    public Map<String,Object> getReplaceMapInspectionTunnel(String creator, InspectionWordDataVo report, List<InspectionDiseaseInstanceVO> diseases){
        if(report==null){
            throw new BusinessException("计划详情不存在");
        }
        HashMap<String,Object> replaceWordMap=new HashMap<>();
        String headData="桥名:"+report.getStructureName()+
                "\t巡检日期:"+report.getInspectionYear()+"年"+
                report.getInspectionMonth()+"月"+
                report.getInspectionDate()+"日"+
                report.getInspectionEve()+"午\t星期"+
                insDayToString(report.getInspectionDay()-1)+"\t天气"+
                report.getInspectionWeather();
        replaceWordMap.put("${headData}",headData);
//        HashSet<Integer> set=new HashSet<>();
//        for(int i=1;i<=37;i++){
//            set.add(i);
//        }
        if(diseases.size()>0){
            replaceWordMap.put("${botherremark}",diseases.get(0).getRemarks());
        }else{
            replaceWordMap.put("${botherremark}","-");
        }
//        for(InspectionDiseaseInstanceVO diseaseInstanceVO:diseases){
//            if(diseaseInstanceVO.getInspectionDiseaseId()==64){//其他说明
//                replaceWordMap.put("${botherremark}",diseaseInstanceVO.getRemarks());
//            }
////            else {
////                Integer local = diseaseInstanceVO.getInspectionDiseaseId() - 26;
////                replaceWordMap.put("${b" + local + "condition}", "缺损");
////                replaceWordMap.put("${b" + local + "part}", diseaseInstanceVO.getExceptionPart());
////                replaceWordMap.put("${b" + local + "remark}", diseaseInstanceVO.getRemarks());
////                replaceWordMap.put("${b" + local + "type}", exceptionTypeToString(diseaseInstanceVO.getExceptionType()));
////                replaceWordMap.put("${b" + local + "strategy}", strategyToString(diseaseInstanceVO.getStrategy()));
////                set.remove(local);
////            }
//        }
//        for(int i:set){
//            replaceWordMap.put("${b" + i + "condition}", "完好");
//        }
        String footData="核对存档人:\t\t\t\t"+"巡查人:"+(report.getInspector()==null?"":report.getInspector());
        replaceWordMap.put("${footData}",footData);
        return replaceWordMap;
    }

    public String strategyToString(int s){
        switch (s){
            case 1:
                return "观察";
            case 2:return "报修";
            case 3:return "监测";
            case 4:return "即修";
            case 5:return "更换";
            case 6:return "增设";
            case 7:return "跟踪监测";
            case 8:return "维修处置";
            case 9:return "定期或专项检查";
        }
        return "";
    }

    public String exceptionTypeToString(Integer s){
        if (s!=null)
        switch (s){
            case 1:
                return "一般异常";
            case 2:return "严重异常";
            default:
        }
        return "";
    }

    public Map<String,Object> getReplaceMapMaintain(MaintainItemVO maintainItemVO){
        HashMap<String,Object> replaceWordMap=new HashMap<>();
        replaceWordMap.put("${manageUnit}",maintainItemVO.getManageDepartment()!=null?maintainItemVO.getManageDepartment():"");
        replaceWordMap.put("${maintainUnit}",maintainItemVO.getMaintenanceUnit()!=null?maintainItemVO.getMaintenanceUnit():"");
        replaceWordMap.put("${projectName}",maintainItemVO.getProjectName()!=null?maintainItemVO.getProjectName():"");
        replaceWordMap.put("${structureName}",maintainItemVO.getStructureName()!=null?maintainItemVO.getStructureName():"");
        replaceWordMap.put("${quantities}",maintainItemVO.getQuantities()!=null?maintainItemVO.getQuantities():"");
        replaceWordMap.put("${checkItem}",maintainItemVO.getName()!=null?maintainItemVO.getName():"");
        replaceWordMap.put("${maintainContent}",maintainItemVO.getContent()!=null?maintainItemVO.getContent():"");
        if(maintainItemVO.getDiseaseName()==null&&maintainItemVO.getDiseaseRemark()==null){
            replaceWordMap.put("${diseaseDefect}","");
        }else {
            replaceWordMap.put("${diseaseDefect}", "" +
                    (maintainItemVO.getDiseaseName() != null ? maintainItemVO.getDiseaseName()+ ":" : "") +
                    (maintainItemVO.getDiseaseRemark() != null ? maintainItemVO.getDiseaseRemark() : ""));
        }
        replaceWordMap.put("${maintainmethod}",maintainItemVO.getMethod()!=null?maintainItemVO.getMethod():"");
        replaceWordMap.put("${maintainResult}",maintainItemVO.getResult()!=null?maintainItemVO.getResult():"");
        replaceWordMap.put("${maintainer}","维修人员："+(maintainItemVO.getCreator()!=null?maintainItemVO.getCreator():""));
        replaceWordMap.put("${phone}","联系方式："+(maintainItemVO.getPhone()!=null?maintainItemVO.getPhone():""));
        replaceWordMap.put("${briefTime}",maintainItemVO.getMaintainTime()!=null?
                new SimpleDateFormat("yyyy-MM-dd").format(maintainItemVO.getMaintainTime()):"");
        return replaceWordMap;
    }

    public String insDayToString(int day){
        switch (day){
            case 1:return "一";
            case 2:return "二";
            case 3:return "三";
            case 4:return "四";
            case 5:return "五";
            case 6:return "六";
            case 0:return "日";
        }
        return "";
    }

}
