package com.iware.common.utils;


import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import java.io.*;
import java.util.*;

/**
 * @author lbx
 * 该类提供word导出的整体逻辑
 * 部分方法的具体实现需要根据具体需求完成继承子类
 */
public abstract class WordPoitlUtil {

    public static final int CLASS_TUNNEL = 1;

    public static final int CLASS_OTHER = -1;

    //生成XWPFDocument并输出
    public String createWord(String templatePath,
                             Map<String, Object> otherWordData,
                             List<?> tableRowData,
                             List<?> tableData,
                             String relativeFilePath,
                             Map<Integer,String> normalOption) throws IOException {
        XWPFDocument result= createWord(templatePath,otherWordData,tableRowData,tableData,normalOption);
        /** IO流输出 */
        File outFile = new File(getOutputFilePath() + relativeFilePath);
        // 如果没有文件夹，则新建
        if(!outFile.getParentFile().exists()) {
            outFile.getParentFile().mkdirs();
        }
        // 如果存在了这个简报，则删除，重新保存
        if(outFile.exists()) {
            outFile.delete();
        }
        // 重新保存
        FileOutputStream out = new FileOutputStream(outFile);
        result.write(out);
        out.close();
        /** IO流输出结束 */
        return relativeFilePath;
    }

    //处理word模板并生成XWPFDocument
    public XWPFDocument createWord(String templatePath,
                                   Map<String, Object> otherWordData,
                                   List<?> tableRowData,
                                   List<?> tableData,
                                   Map<Integer,String> normalOption) throws IOException {
        InputStream input = new FileInputStream(templatePath);
        XWPFDocument doc = new XWPFDocument(input);
        WordTempUtil.replaceInPara(doc, otherWordData);
        WordTempUtil.replaceInTable(doc, otherWordData);
        //处理表格原始数据
        HashMap<String,List<Object>> tableCellData=excTableData(tableRowData,tableData,normalOption);
        //生成表格数据
        XWPFDocument result= excWordTableLoopRow(
                doc,
                getTableLabel(),
                tableCellData
        );
        //表格单元格合并处理
        List<List<Object>> oriDataList=new ArrayList<>();
        for(String key:getTableLabel()){
            oriDataList.add(tableCellData.getOrDefault(key,new ArrayList<>()));
        }
        excWordTableMerge(result,oriDataList);
        return result;
    }

    protected abstract List<String> getTableLabel();

    protected abstract String getOutputFilePath();

    protected abstract HashMap<String,List<Object>> excTableData(List<?> tableRowData,List<?> tableData,Map<Integer,String> normalOption);

    /**
     * 操作填充表格
     * @param doc
     * @param tableLabel
     * @param tableData
     */
    protected abstract XWPFDocument excWordTableLoopRow(XWPFDocument doc,
                                             List<String> tableLabel,
                                             HashMap<String,List<Object>> tableData);

    /**
     * 处理表格合并
     * @param doc
     * @param tableData
     */
    protected void excWordTableMerge(XWPFDocument doc,List<List<Object>> tableData){
        Iterator<XWPFTable> iterator = doc.getTablesIterator();
        XWPFTable table;
        int i=-1;
        List<Integer> tableDataClass=getTableDataClass();
        while (iterator.hasNext()) {
            i++;
            table = iterator.next();
            switch (tableDataClass.get(i)){
                case CLASS_OTHER:
                    for(Integer[] position:getMergeColRowStartEnd(tableData.get(i))){
                        //合并单元格
                        mergeCellVertically(table,position[0],position[1],position[2]);
                    }
                    //横向合并单元格
                    excWordTableMergeHorizontal(doc,false);
                    break;
                case CLASS_TUNNEL:
                    for(Integer[] position:getMergeColRowStartEndTunnel(tableData.get(i))){
                        //合并单元格
                        mergeCellVertically(table,position[0],position[1],position[2]);
                    }
                    //横向合并单元格
                    excWordTableMergeHorizontal(doc,true);
                    break;
                default:
            }
        }
    }

    protected abstract List<Integer> getTableDataClass();

    /**
     * 需要实现
     * 通过数据list获取需要合并单元格下标
     * @return 需要合并的单元格下标，0为列，1为开始行，2为结束行
     */
    protected abstract List<Integer[]> getMergeColRowStartEnd(List<Object> tableData);

    protected abstract List<Integer[]> getMergeColRowStartEndTunnel(List<Object> tableData);

    protected abstract void excWordTableMergeHorizontal(XWPFDocument document,boolean isTunnel);

    /**
     * 合并行
     * @param table
     * @param col 需要合并的列
     * @param fromRow 开始行
     * @param toRow 结束行
     */
    protected void mergeCellVertically(XWPFTable table, int col, int fromRow, int toRow) {
        for(int rowIndex = fromRow; rowIndex <= toRow; rowIndex++){
            CTVMerge vmerge = CTVMerge.Factory.newInstance();
            if(rowIndex == fromRow){
                vmerge.setVal(STMerge.RESTART);
            } else {
                vmerge.setVal(STMerge.CONTINUE);
            }
            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
            CTTcPr tcPr = cell.getCTTc().getTcPr();
            if (tcPr != null) {
                tcPr.setVMerge(vmerge);
            } else {
                tcPr = CTTcPr.Factory.newInstance();
                tcPr.setVMerge(vmerge);
                cell.getCTTc().setTcPr(tcPr);
            }
        }
    }
}
