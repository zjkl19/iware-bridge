package com.iware.bridge.inspection.serivce;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.deepoove.poi.util.TableTools;
import com.iware.bridge.inspection.vo.InspectionDiseaseInstanceVO;
import com.iware.bridge.inspection.vo.InspectionWordTableData;
import com.iware.bridge.model.entity.inspection.InspectionDisease;
import com.iware.common.utils.WordPoitlUtil;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import java.util.*;


/**
 * @author lbx
 * 根据桥梁1.3.3需求实现抽象类WordPoitlUtil
 */
public class InspectionTableExcUtil extends WordPoitlUtil {

    public final static Integer BRIDGE_DECK_SYSTEM_DISEASE_MAX_ID=15;

    public final static Integer TUNNEL_MIN_ID=27;

    private final static List<Integer> BRIDGE_TABLE=new ArrayList<Integer>(){
        {
            add(CLASS_OTHER);
        }
    };

    private List<Integer> tableDataClass;

    public static boolean isDefault=true;

    private static List<String> tableLabels=new ArrayList<String>(){
        {
            add("inspection");
        }
    };

    private String outputPath="C://file";

    @Override
    protected List<String> getTableLabel() {
        return tableLabels;
    }

    @Override
    protected String getOutputFilePath() {
        return outputPath;
    }


    /**
     * 处理多表格数据，key为表格标签，value为表格内单元格数据
     * @param tableRowData 行表头数据
     * @param tableData 表格内数据
     * @return
     */
    @Override
    protected HashMap<String, List<Object>> excTableData(List<?> tableRowData, List<?> tableData, Map<Integer,String> normalOption) {
        HashMap<String,List<Object>> result=new HashMap<>();
        if(tableRowData.size()<=0||!(tableRowData.get(0) instanceof InspectionDisease)){
            result.put("inspection",new ArrayList<>());
            return result;
        }
        HashMap<Integer, InspectionDiseaseInstanceVO> tableDataMap=new HashMap<>();
        if(tableData.size()>0&&tableData.get(0) instanceof InspectionDiseaseInstanceVO){
            for(Object o:tableData){
                tableDataMap.put(((InspectionDiseaseInstanceVO)o).getInspectionDiseaseId(),((InspectionDiseaseInstanceVO)o));
            }
        }
        List<Object> inspectionTableRows=new ArrayList<>();
        for(Object o:tableRowData){
            InspectionDisease disease=(InspectionDisease) o;
            InspectionWordTableData cell=new InspectionWordTableData();
            cell.setDiseasePart(disease.getDiseasePart());
            cell.setCheckItem(disease.getCheckItem());
            cell.setDamageType(disease.getDamageType());
            InspectionDiseaseInstanceVO instance=tableDataMap.getOrDefault(disease.getId(),null);
            cell.setCondition(instance!=null?instance.getOptionName():normalOption.get(disease.getId()));
            if(disease.getId()<=BRIDGE_DECK_SYSTEM_DISEASE_MAX_ID){
                cell.setDisease(instance!=null?
                        instance.getQuantity()+"("+disease.getUnit()+")"
                        :"-");
            }else{
                cell.setDisease(instance!=null?instance.getExceptionPart():"-");
            }
            cell.setRemark(instance!=null?instance.getRemarks():"-");
            if(disease.getId()>=TUNNEL_MIN_ID){ //隧道独有，异常判定
                //隧道默认处置措施
                cell.setStrategy(instance!=null?strategyToString(instance.getStrategy()):"跟踪监测");
                cell.setExceptionType(instance!=null?exceptionTypeToString(instance.getExceptionType()):"-");
            }else{//桥梁
                cell.setStrategy(instance!=null?strategyToString(instance.getStrategy()):"观察");
            }
            inspectionTableRows.add(cell);
        }
        result.put("inspection",inspectionTableRows);
        return result;
    }

    /**
     * 当表格数不为一个，或表格中处理数据不为InspectionWordTableData
     * 通过改变isDefault标志位，并赋值tableDataClass
     * @param tableDataClass 各个表格中数据类型:WordPoitlUtil中静态值
     */
    public void setTableDataClass(List<Integer> tableDataClass){
        this.tableDataClass=tableDataClass;
    }


    /**
     *
     * @param doc
     * @param tableLabel 标签列表，为tableData的key
     * @param tableData key为表格标签，value中为表格数据列表
     */
    @Override
    protected XWPFDocument excWordTableLoopRow(XWPFDocument doc,
                                    List<String> tableLabel,
                                    HashMap<String, List<Object>> tableData
    ) {
        //表格模板循环填充
        LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
        ConfigureBuilder builder = Configure.builder();
        Configure config;
        for(String label:tableLabel){
            builder=builder.bind(label,policy);
        }
        config=builder.build();
        XWPFTemplate template = XWPFTemplate.compile(doc, config).render(tableData);
//        //导出?双方案未确定
//        try {
//            template.writeToFile(outputFilePath);
//        }catch (Exception e){
//            //未处理的ioException
//            e.printStackTrace();
//        }
        //类型转换
        return template.getXWPFDocument();
    }

    @Override
    protected List<Integer> getTableDataClass() {
        if(isDefault) {
            return BRIDGE_TABLE;
        }else{
            return tableDataClass;
        }
    }

    @Override
    protected List<Integer[]> getMergeColRowStartEnd(List<Object> tableData) {
        List<Integer[]> result=new ArrayList<>();
        if(tableData.size()>0&&tableData.get(0) instanceof InspectionWordTableData){
            Integer colOnePosition=0;
            String lastDataOne=((InspectionWordTableData) tableData.get(0)).getDiseasePart();
            Integer colTwoPosition=0;
            String lastDataTwo=((InspectionWordTableData) tableData.get(0)).getCheckItem();
            Integer colThreePosition=0;
            String lastDataThree=((InspectionWordTableData) tableData.get(0)).getDamageType();
            for(int i=0;i<=tableData.size();i++){
                InspectionWordTableData nowObject=(i!=tableData.size()?(InspectionWordTableData) tableData.get(i):new InspectionWordTableData());
                if(!lastDataOne.equals(nowObject.getDiseasePart())){
                    if(i-colOnePosition>1){ //连续相同格子数大于1
                        Integer[] positions=new Integer[3];
                        positions[0]=0;
                        positions[1]=colOnePosition+1;
                        positions[2]=i+1-1;
                        result.add(positions);
                    }
                    colOnePosition=i;
                    lastDataOne=nowObject.getDiseasePart();
                }
                if(!lastDataTwo.equals(nowObject.getCheckItem())){
                    if(i-colTwoPosition>1){ //连续相同格子数大于1
                        Integer[] positions=new Integer[3];
                        positions[0]=1;
                        positions[1]=colTwoPosition+1;
                        positions[2]=i+1-1;
                        result.add(positions);
                    }
                    colTwoPosition=i;
                    lastDataTwo=nowObject.getCheckItem();
                }
                if(!lastDataThree.equals(nowObject.getDamageType())){
                    if(i-colThreePosition>1){ //连续相同格子数大于1
                        Integer[] positions=new Integer[3];
                        positions[0]=2;
                        positions[1]=colThreePosition+1;
                        positions[2]=i+1-1;
                        result.add(positions);
                    }
                    colThreePosition=i;
                    lastDataThree=nowObject.getDamageType();
                }
            }
            return result;
        }
        /** 其他类的实现方法 */
        return result;
    }

    @Override
    protected List<Integer[]> getMergeColRowStartEndTunnel(List<Object> tableData) {
        List<Integer[]> result=new ArrayList<>();
        if(tableData.size()>0&&tableData.get(0) instanceof InspectionWordTableData){
            Integer colOnePosition=0;
            String lastDataOne=((InspectionWordTableData) tableData.get(0)).getCheckItem();
            Integer colTwoPosition=0;
            String lastDataTwo=((InspectionWordTableData) tableData.get(0)).getDamageType();
            for(int i=0;i<=tableData.size();i++){
                InspectionWordTableData nowObject=(i!=tableData.size()?(InspectionWordTableData) tableData.get(i):new InspectionWordTableData());
                if(!lastDataOne.equals(nowObject.getCheckItem())){
                    if(i-colOnePosition>1){ //连续相同格子数大于1
                        Integer[] positions=new Integer[3];
                        positions[0]=0;
                        positions[1]=colOnePosition+2;
                        positions[2]=i+2-1;
                        result.add(positions);
                    }
                    colOnePosition=i;
                    lastDataOne=nowObject.getCheckItem();
                }
                if(!lastDataTwo.equals(nowObject.getDamageType())){
                    if(i-colTwoPosition>1){ //连续相同格子数大于1
                        Integer[] positions=new Integer[3];
                        positions[0]=1;
                        positions[1]=colTwoPosition+2;
                        positions[2]=i+2-1;
                        result.add(positions);
                    }
                    colTwoPosition=i;
                    lastDataTwo=nowObject.getDamageType();
                }
            }
            return result;
        }
        /** 其他类的实现方法 */
        return result;
    }

    //合并横向单元格
    @Override
    protected void excWordTableMergeHorizontal(XWPFDocument document,boolean isTunnel) {
        Iterator<XWPFTable> iterator = document.getTablesIterator();
        XWPFTable table;
        while (iterator.hasNext()) {
            table = iterator.next();
            Iterator<XWPFTableRow> rowIterator=table.getRows().iterator();
            XWPFTableRow row;
            int rowLocation=-1;
            while(rowIterator.hasNext()){
                rowLocation++;
                row=rowIterator.next();
                XWPFTableCell cell1=row.getCell(0);
                XWPFTableCell cell2=row.getCell(1);
                XWPFTableCell cell3=row.getCell(2);
                int begin=1;
                int end=1;
                if(cell1==null|| cell1.getText()==null){
                    continue;
                }
                if(cell2==null|| cell2.getText()==null){
                    continue;
                }
                if(cell3==null|| cell3.getText()==null){
                    continue;
                }
                if(cell1.getText().equals(cell2.getText())){
                    begin=0;
                }
                //隧道只进行两个判断
                if(!isTunnel&&cell2.getText().equals(cell3.getText())){
                    end=2;
                }
//                System.out.println("行号"+rowLocation+"0:"+cell1.getText()+",1:"+ cell2.getText()+",2:"+ cell3.getText());
                if(begin!=end) {
                    TableTools.mergeCellsHorizonal(table,rowLocation,begin,end);
                }
//                    for(int cellIndex = begin; cellIndex <= end; cellIndex++){
//                        CTVMerge vmerge = CTVMerge.Factory.newInstance();
//                        if(cellIndex == begin){
//                            vmerge.setVal(STMerge.RESTART);
//                        } else {
//                            vmerge.setVal(STMerge.CONTINUE);
//                        }
//                        XWPFTableCell cell = table.getRow(rowLocation).getCell(cellIndex);
//                        CTTcPr tcPr = cell.getCTTc().getTcPr();
//                        if (tcPr != null) {
//                            tcPr.setVMerge(vmerge);
//                        } else {
//                            tcPr = CTTcPr.Factory.newInstance();
//                            tcPr.setVMerge(vmerge);
//                            cell.getCTTc().setTcPr(tcPr);
//                        }
//                    }
//                }
            }
        }
    }

    private String strategyToString(int s){
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
            default:
        }
        return "";
    }

    private String exceptionTypeToString(Integer s){
        if(s==null){
            return "";
        }
        switch (s){
            case 1:
                return "一般异常";
            case 2:return "严重异常";
            default:
        }
        return "";
    }

    public static List<String> getTableLabels() {
        return tableLabels;
    }

    public static void setTableLabels(List<String> tableLabels) {
        InspectionTableExcUtil.tableLabels = tableLabels;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    //默认生成word方法(限定v1.3.3巡查表)
//    private String defaultCreateWord(String templatePath,
//                                    Map<String, Object> otherWordData,
//                                    List<?> tableRowData,
//                                    List<?> tableData,
//                                    String relativeFilePath){
        //返回结果，输出文件相对路径
//        String result="";

        //修改标志位，是否使用默认表格值:1个表格，处理数据为InspectionWordTableData
//        InspectionTableExcUtil.isDefault=false;

        //选择使用处理数据方法标志位
//        ArrayList<Integer> dataClasses=new ArrayList<>();
//        dataClasses.add(WordPoitlUtil.CLASS_OTHER);
//        this.setTableDataClass(dataClasses);

        //创建word并输出
//        try {
//            result=createWord(templatePath, otherWordData, tableRowData, tableData, relativeFilePath,);
//        }catch (IOException e){
//            e.printStackTrace();
//        }

        //返回值
//        return result;
//    }
}
