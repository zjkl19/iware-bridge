package com.iware.common.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class ExcelImportUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExcelImportUtil.class);

    public static final int EXCEL_CELL_TYPE_STRING=1;

    public static final int EXCEL_CELL_TYPE_INT=2;

    public static final int EXCEL_CELL_TYPE_DOUBLE=3;

    public static final int EXCEL_CELL_TYPE_DATE=4;

    public static final int EXCEL_CELL_TYPE_MONTH=6;

    public static final int EXCEL_CELL_TYPE_BIGDATA=5;

    public <T> Map<String,List<T>> importExcel(File file) throws IOException, //流关闭开启异常
            ClassNotFoundException, //传入类名有误
            NoSuchMethodException, //传入方法名有误
            IllegalAccessException, //非法访问，权限问题
            InvocationTargetException, //构造对象或调用方法 目标异常
            InstantiationException, //构造对象异常
            ParseException, //解析异常,日期解析
            IllegalStateException,  //解析异常，单元格解析
            TypeNotPresentException   //类型不匹配异常，必填项为空
    {
        HashMap<String, List<T>> result = new HashMap<>();
        XSSFWorkbook workbook=null;
        try{
            workbook = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheet = null;
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                //获取每个sheet
                sheet = workbook.getSheetAt(i);
                List<T> list = new ArrayList<>();
                String rowClassName = getRowClassName(sheet.getSheetName());
                if (rowClassName == null) {
                    continue;
                }
                //getPhysicalNumberOfRows获取有记录的行数
                for (int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {
                    Row row = sheet.getRow(j);
                    if (null != row) {
                        //getLastCellNum获取最后一列
                        Class<?> cls = Class.forName(rowClassName);
                        Constructor<?> cons = cls.getConstructor();
                        Object object = cons.newInstance();
                        for (int k = 0; k < row.getLastCellNum(); k++) {
                            if (null != row.getCell(k)) {
                                Cell cell = row.getCell(k);
                                Method m;
                                switch (colWithPropertyType(sheet.getSheetName()).getOrDefault(k, 0)) {
                                    case EXCEL_CELL_TYPE_STRING://String
                                        String str = cell.getStringCellValue();
                                        if(str.isEmpty()){
                                            throw new TypeNotPresentException("必填项不能为空(第"+(i+1)+"个表,第"+(k+1)+"列)",new Throwable());
                                        }
                                        m = cls.getDeclaredMethod("set" + colWithPropertyName(sheet.getSheetName()).getOrDefault(k, "Id"), String.class);
                                        m.setAccessible(true);
                                        m.invoke(object, str);
                                        break;
                                    case EXCEL_CELL_TYPE_INT://int
                                        double d = cell.getNumericCellValue();
                                        int intValue = (int) d;
                                        m = cls.getDeclaredMethod("set" + colWithPropertyName(sheet.getSheetName()).getOrDefault(k, "Id"), Integer.class);
                                        m.setAccessible(true);
                                        m.invoke(object, intValue);
                                        break;
                                    case EXCEL_CELL_TYPE_DOUBLE://double
                                        Double dou = cell.getNumericCellValue();
                                        m = cls.getDeclaredMethod("set" + colWithPropertyName(sheet.getSheetName()).getOrDefault(k, "Id"), Double.class);
                                        m.setAccessible(true);
                                        m.invoke(object, dou);
                                        break;
                                    case EXCEL_CELL_TYPE_DATE://日期
                                        String str1 = cell.getStringCellValue();
                                        Date date = new SimpleDateFormat("yyyy/MM/dd").parse(str1);
                                        m = cls.getDeclaredMethod("set" + colWithPropertyName(sheet.getSheetName()).getOrDefault(k, "Id"), Date.class);
                                        m.invoke(object, date);
                                        break;
                                    case EXCEL_CELL_TYPE_BIGDATA://大数BigDecimal
                                        double dou1 = cell.getNumericCellValue();
                                        BigDecimal big = BigDecimal.valueOf(dou1);
                                        m = cls.getDeclaredMethod("set" + colWithPropertyName(sheet.getSheetName()).getOrDefault(k, "Id"), BigDecimal.class);
                                        m.invoke(object, big);
                                        break;
                                    case EXCEL_CELL_TYPE_MONTH://日期
                                        String str2 = cell.getStringCellValue();
                                        Date date1 = new SimpleDateFormat("yyyy/MM").parse(str2);
                                        m = cls.getDeclaredMethod("set" + colWithPropertyName(sheet.getSheetName()).getOrDefault(k, "Id"), Date.class);
                                        m.invoke(object, date1);
                                        break;
                                    default:
                                }
                            }
                        }
                        list.add((T) object);
                    }
                }
                result.put(sheet.getSheetName(), list);
            }
        }catch (Exception e) {
            logger.error(ExceptionUtils.getErrorStack(e));
            throw e;
        }finally {
            if(workbook!=null) {
                workbook.close();
            }
        }
        return result;
    }

    protected abstract HashMap<Integer,String> colWithPropertyName(String sheetName);

    protected abstract HashMap<Integer,Integer> colWithPropertyType(String sheetName);

    protected abstract String getRowClassName(String sheetName);

}
