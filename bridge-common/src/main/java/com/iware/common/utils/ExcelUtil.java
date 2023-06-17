package com.iware.common.utils;

import com.iware.common.constant.GlobalConstant;
import com.iware.common.enums.FileTypeEnum;
import com.iware.common.exception.BusinessException;
import com.iware.common.pojo.FieldInfo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: xfc
 * @Date: 2019/5/20 0020 14:21
 * @Description:
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExcelUtil {

    private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * 导出Excel
     *
     * @param sheetName sheet名称
     * @param title     标题
     * @param values    内容
     * @param wb        HSSFWorkbook对象
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] values, HSSFWorkbook wb) {

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null)
            wb = new HSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for (int i = 0; i < values.length; i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < values[i].length; j++) {
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        return wb;
    }

    /**
     * float数值保留小数
     * @param value 数值
     * @param bit   保留小数
     * @return
     */
    public static String formatFloat(Float value, Integer bit) {

        if(value == null) return null;
        DecimalFormat format = new DecimalFormat();
        format.setMaximumFractionDigits(bit);
        format.setGroupingUsed(false);
        format.setGroupingSize(0);
        if (value > 0)
            format.setRoundingMode(RoundingMode.FLOOR);
        else
            format.setRoundingMode(RoundingMode.CEILING);
        return format.format(value);
    }

    /**
     * 时间格式化
     * @param date    日期
     * @param pattern 格式
     * @return
     */
    public static String formatDate(Date date, String pattern) {

        if (date == null) return null;
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    /**
     * 数据转化
     * @param fieldInfoList 字段信息
     * @param dataList      数据列表
     * @param clazz         类信息
     * @param <T>           泛型标志
     * @return
     * @throws Exception
     */
    public static <T> String[][] dataTransfer(List<FieldInfo> fieldInfoList, List<T> dataList, Class<T> clazz)
            throws InvocationTargetException, IllegalAccessException {

        String [][] content = new String[dataList.size()][fieldInfoList.size()];

        List<Method> methodList = new ArrayList<>();
        while(clazz != null) {
            methodList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredMethods())));
            clazz = (Class<T>) clazz.getSuperclass();
        }
        for (FieldInfo fieldInfo : fieldInfoList) {
            for(Method method : methodList){
                if (method.getName().startsWith("get") && method.getName().toLowerCase()
                        .contains(fieldInfo.getFieldName().toLowerCase())) {
                    fieldInfo.setMethod(method);
                    break;
                }
            }
        }
        for (int i = 0; i < fieldInfoList.size(); i++) {
            Method method = fieldInfoList.get(i).getMethod();
            method.setAccessible(true);
            String str = "";
            for (int j = 0; j < dataList.size(); j++) {

                Object returnValue = method.invoke(dataList.get(j));
                if (method.getReturnType() == Float.class) {
                    str = formatFloat((float)returnValue, 2);
                } else if (method.getReturnType() == Date.class) {
                    str = formatDate((Date)returnValue, GlobalConstant.FULL_DATE_FORMAT);
                } else {
                    if(returnValue==null){
                        str = "";
                    }else {
                        str = returnValue.toString();
                    }
                }
                content[j][i] = str;
            }
        }
        return content;
    }

    public static <T> String createExcel(String fileName, String sheetName, List<FieldInfo> fieldInfoList,
                                         List<T> dataList, Class<T> clazz) {


        String fileFullPath = FileUtil.genFileFullPath(FileTypeEnum.EXCEL_TEMP.getCode());
        String targetFilepath = String.format("%s%s", fileFullPath, fileName);
        String[][] values;

        List<String> header = fieldInfoList.stream().map(FieldInfo::getHeaderName).collect(Collectors.toList());
        try {

            values = dataTransfer(fieldInfoList, dataList, clazz);
            HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, header.toArray(new String[fieldInfoList.size()]),
                values, null);

            //判断文件夹是否存在
            File file = new File(fileFullPath);
            if (!file.exists()){
                file.mkdirs();
            }
            logger.info("文件路径：{}", targetFilepath);
            FileOutputStream out = new FileOutputStream(targetFilepath);
            wb.write(out);
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.error(ExceptionUtils.getErrorStack(e));
            throw new BusinessException("文件导出失败");
        }
        return targetFilepath;
    }

}
