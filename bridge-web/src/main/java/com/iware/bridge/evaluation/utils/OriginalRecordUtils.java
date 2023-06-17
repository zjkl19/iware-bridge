package com.iware.bridge.evaluation.Utils;


import com.iware.bridge.evaluation.Utils.model.WordCellData;
import com.iware.bridge.evaluation.vo.DetectionRecord;
import com.iware.bridge.evaluation.vo.ExportData;
import com.iware.bridge.evaluation.vo.PhotoUrl;
import com.iware.bridge.evaluation.vo.ShowManage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class OriginalRecordUtils {


    public List<ShowManage> str(List<PhotoUrl> photoUrlsBD, List<PhotoUrl> photoUrlsSUP, List<PhotoUrl> photoUrlsSUB,
                                List<DetectionRecord> detectionRecordsBD, List<DetectionRecord> detectionRecordsSUP, List<DetectionRecord> detectionRecordsSUB) {
        Map<String, Map<String, Map<String, ShowManage>>> manageMap = new HashMap<>();
        for (DetectionRecord detectionRecord : detectionRecordsBD) {
            manageMap.put(detectionRecord.getRoadName(), new HashMap<>());
        }
        for (DetectionRecord detectionRecord : detectionRecordsSUP) {
            manageMap.put(detectionRecord.getRoadName(), new HashMap<>());
        }
        for (DetectionRecord detectionRecord : detectionRecordsSUB) {
            manageMap.put(detectionRecord.getRoadName(), new HashMap<>());
        }

        for (DetectionRecord detectionRecord : detectionRecordsBD) {
            manageMap.get(detectionRecord.getRoadName()).put(detectionRecord.getSpanCode(), new HashMap<>());
        }
        for (DetectionRecord detectionRecord : detectionRecordsSUP) {
            manageMap.get(detectionRecord.getRoadName()).put(detectionRecord.getSpanCode(), new HashMap<>());
        }
        for (DetectionRecord detectionRecord : detectionRecordsSUB) {
            manageMap.get(detectionRecord.getRoadName()).put(detectionRecord.getSpanCode(), new HashMap<>());
        }


        for (DetectionRecord detectionRecord : detectionRecordsBD) {
            ShowManage showManage = new ShowManage();
            showManage.setList(new ArrayList<>());
            showManage.setPhotoUrls(new ArrayList<>());
            showManage.setType("桥面系");
            showManage.setRoadName(detectionRecord.getRoadName());
            showManage.setSpanName(detectionRecord.getSpanCode());
            manageMap.get(detectionRecord.getRoadName()).get(detectionRecord.getSpanCode()).put("桥面系", showManage);
        }
        for (DetectionRecord detectionRecord : detectionRecordsSUP) {
            ShowManage showManage = new ShowManage();
            showManage.setList(new ArrayList<>());
            showManage.setPhotoUrls(new ArrayList<>());
            showManage.setType("上部结构");
            showManage.setRoadName(detectionRecord.getRoadName());
            showManage.setSpanName(detectionRecord.getSpanCode());
            manageMap.get(detectionRecord.getRoadName()).get(detectionRecord.getSpanCode()).put("上部结构", showManage);
        }
        for (DetectionRecord detectionRecord : detectionRecordsSUB) {
            ShowManage showManage = new ShowManage();
            showManage.setList(new ArrayList<>());
            showManage.setPhotoUrls(new ArrayList<>());
            showManage.setType("下部结构");
            showManage.setRoadName(detectionRecord.getRoadName());
            showManage.setSpanName(detectionRecord.getSpanCode());
            manageMap.get(detectionRecord.getRoadName()).get(detectionRecord.getSpanCode()).put("下部结构", showManage);
        }

        for (PhotoUrl photoUrl : photoUrlsBD) {
            manageMap.get(photoUrl.getRoadName()).get(photoUrl.getSpanName()).get("桥面系").getPhotoUrls().add(photoUrl);
        }
        for (PhotoUrl photoUrl : photoUrlsSUP) {
            manageMap.get(photoUrl.getRoadName()).get(photoUrl.getSpanName()).get("上部结构").getPhotoUrls().add(photoUrl);
        }
        for (PhotoUrl photoUrl : photoUrlsSUB) {
            manageMap.get(photoUrl.getRoadName()).get(photoUrl.getSpanName()).get("下部结构").getPhotoUrls().add(photoUrl);

        }

        for (DetectionRecord detectionRecord : detectionRecordsBD) {
            String str = "编号：" + detectionRecord.getCode() + "-" + detectionRecord.getSort() + "，" +
                    checkIsNull("", detectionRecord.getSpanCode(), "#跨") + checkIsNull("", detectionRecord.getName(), "，") +
                    checkIsNull("", detectionRecord.getDiseaseName(), "，") + checkIsNull("程度：", detectionRecord.getDegree(), "，");
            if (detectionRecord.getRemark() != null && !"".equals(detectionRecord.getRemark().trim())) {
                str += checkIsNull("备注：", detectionRecord.getRemark(), "，");
            }
            str += "位置:（" + detectionRecord.getxAxis() + "，" + detectionRecord.getyAxis() + "）";
            detectionRecord.setText(str);
            manageMap.get(detectionRecord.getRoadName()).get(detectionRecord.getSpanCode()).get("桥面系").getList().add(detectionRecord);

        }

        for (DetectionRecord detectionRecord : detectionRecordsSUP) {
            String str = "编号：" + detectionRecord.getCode() + "-" + detectionRecord.getSort() + "，" +
                    checkIsNull("", detectionRecord.getSpanCode(), "#跨") + checkIsNull("", detectionRecord.getComponentCode(), "#") +
                    checkIsNull("", detectionRecord.getName(), "，") +
                    checkIsNull("", detectionRecord.getDiseaseName(), "，") + checkIsNull("程度：", detectionRecord.getDegree(), "，");
            if (detectionRecord.getRemark() != null && !"".equals(detectionRecord.getRemark().trim())) {
                str += checkIsNull("备注：", detectionRecord.getRemark(), "，");
            }
            str += "位置:（" + detectionRecord.getxAxis() + "，" + detectionRecord.getyAxis() + "）";
            detectionRecord.setText(str);
            manageMap.get(detectionRecord.getRoadName()).get(detectionRecord.getSpanCode()).get("上部结构").getList().add(detectionRecord);

        }
        for (DetectionRecord detectionRecord : detectionRecordsSUB) {
            String str = "编号：" + detectionRecord.getCode() + "-" + detectionRecord.getSort() + "，" +
                    checkIsNull("", detectionRecord.getSpanCode(), "#跨") + checkIsNull("", detectionRecord.getComponentCode(), "#") +
                    checkIsNull("", detectionRecord.getName(), "，") +
                    checkIsNull("", detectionRecord.getDiseaseName(), "，") + checkIsNull("程度：", detectionRecord.getDegree(), "，");
            if (detectionRecord.getRemark() != null && !"".equals(detectionRecord.getRemark().trim())) {
                str += checkIsNull("备注：", detectionRecord.getRemark(), "，");
            }
            str += "位置:（" + detectionRecord.getxAxis() + "，" + detectionRecord.getyAxis() + "）";
            detectionRecord.setText(str);
            manageMap.get(detectionRecord.getRoadName()).get(detectionRecord.getSpanCode()).get("下部结构").getList().add(detectionRecord);

        }
        List<ShowManage> showManages = new ArrayList<>();
        for (String s : manageMap.keySet()) {
            for (String s1 : manageMap.get(s).keySet()) {
                for (String s2 : manageMap.get(s).get(s1).keySet()) {
                    showManages.add(manageMap.get(s).get(s1).get(s2));
                }
            }
        }
        List<ShowManage> showManages2 = new ArrayList<>();
        for (ShowManage showManage : showManages) {
            if ("桥面系".equals(showManage.getType())) {
                showManages2.add(showManage);
            }
        }
        for (ShowManage showManage : showManages) {
            if ("上部结构".equals(showManage.getType())) {
                showManages2.add(showManage);
            }
        }
        for (ShowManage showManage : showManages) {
            if ("下部结构".equals(showManage.getType())) {
                showManages2.add(showManage);
            }
        }


//        showManages = showManages.stream().sorted(Comparator.comparing(ShowManage::getRoadName)).sorted(Comparator.comparing(ShowManage::getType)).collect(Collectors.toList());

        return showManages2;
    }

    private String checkIsNull(String beforeString, String checkString, String addString) {
        return checkString == null ? "" : beforeString + checkString + addString;
    }

    public String exportRecord(ExportData exportData) throws IOException, InvalidFormatException, XmlException {
        XWPFDocument document = new XWPFDocument();
        OriginalRecordUtils utils = new OriginalRecordUtils();
        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
        CTPageMar pageMar = sectPr.addNewPgMar();
        pageMar.setLeft(BigInteger.valueOf(1700L));//3
        pageMar.setTop(BigInteger.valueOf(850L));//1.5
        pageMar.setRight(BigInteger.valueOf(965L));//1.7
        pageMar.setBottom(BigInteger.valueOf(965));//1.7

        UUID uuid = UUID.randomUUID();

        String path = exportData.getFilePath() + "/exportRecord/" + exportData.getId() + "/" + uuid;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String path2 = exportData.getFilePath() + "/exportRecord/" + exportData.getId() + "/" + uuid + "/" + exportData.getFileName() + ".doc";
        //Write the Document in file system
        FileOutputStream out = new FileOutputStream(new File(path2));

        utils.myCreateParagraph(document, "", ParagraphAlignment.CENTER, "检 测 记 录 表", true, "000000", "黑体", 16, false, 0, 0);

        List<ShowManage> str = utils.str(exportData.getPhotoUrlsBD(), exportData.getPhotoUrlsSUP(), exportData.getPhotoUrlsSUB(), exportData.getDetectionRecordsBD(), exportData.getDetectionRecordsSUP(),
                exportData.getDetectionRecordsSUB());


        //创建一个表格
        XWPFTable infoTable = document.createTable(2, 4);


        CTTblBorders borders = infoTable.getCTTbl().getTblPr().addNewTblBorders();

        borders.addNewLeft().setSz(new BigInteger("15"));
        borders.addNewRight().setSz(new BigInteger("15"));
        borders.addNewTop().setSz(new BigInteger("15"));
        borders.addNewBottom().setSz(new BigInteger("15"));
        borders.addNewInsideH().setSz(new BigInteger("8"));
        borders.addNewInsideV().setSz(new BigInteger("8"));

        List<List<WordCellData>> list1 = new ArrayList<>();

        List<WordCellData> list2 = new ArrayList<>();
        WordCellData wordData = new WordCellData();
        wordData.setName("工程名称");
        wordData.setValue(1500);
        wordData.setAlignment(1);
        wordData.setFontFamily("黑体");
        wordData.setFontSize(12);
        wordData.setHeight(600);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName(exportData.getOriginalRecord().getProjectName() + "-" + exportData.getOriginalRecord().getStructureName());
        wordData.setValue(3000);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体");
        wordData.setFontSize(12);
        wordData.setHeight(600);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("工程地点");
        wordData.setValue(1500);
        wordData.setAlignment(1);
        wordData.setFontFamily("黑体");
        wordData.setFontSize(12);
        wordData.setHeight(600);
        list2.add(wordData);


        wordData = new WordCellData();
        wordData.setName(exportData.getOriginalRecord().getProjectLocation());
        wordData.setValue(3000);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体");
        wordData.setFontSize(12);
        wordData.setHeight(600);
        wordData.setIs(1);
        list2.add(wordData);
        list1.add(list2);

        utils.tableData(infoTable, list1, str, exportData);

        utils.mergeCellsHorizontal(infoTable, 1, 0, 3);


        document.write(out);
        out.close();

        return exportData.getFileContext() + "/static/exportRecord/" + exportData.getId() + "/" + uuid + "/" + exportData.getFileName() + ".doc";
    }

    /**
     * 表格数据处理
     *
     * @param infoTable 表格对象
     * @param lists     数据集合
     */
    public void tableData(XWPFTable infoTable, List<List<WordCellData>> lists, List<ShowManage> showManages, ExportData exportData) throws IOException, InvalidFormatException {
        OriginalRecordUtils utils = new OriginalRecordUtils();
        for (int i = 0; i < lists.size(); i++) {
            List<WordCellData> strList = lists.get(i);
            XWPFTableRow infoTableRow = infoTable.getRow(i);
            infoTableRow.getCtRow().addNewTrPr().addNewTrHeight().setVal(BigInteger.valueOf(lists.get(i).get(0).getHeight()));
            for (int j = 0; j < strList.size(); j++) {
                utils.tableStyle(infoTableRow.getCell(j), strList.get(j));
            }
        }
        XWPFTableRow infoTableRow2 = infoTable.getRow(1);
        if (showManages.isEmpty()) {
            XWPFTableCell cell = infoTableRow2.getCell(0);
            CTP ctp = CTP.Factory.newInstance();
            //行宽
            cell.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(9000));
            //垂直居中
            cell.getCTTc().addNewTcPr().addNewVAlign().setVal(STVerticalJc.CENTER);
            XWPFParagraph p = new XWPFParagraph(ctp, cell);
            p.setAlignment(ParagraphAlignment.LEFT);
            p.setSpacingBefore(100);
            p.setSpacingAfter(100);
            XWPFRun run = p.createRun();
            for (int i = 0; i < 35; i++) {
                run.addBreak();
            }
            cell.setParagraph(p);
        } else {
            utils.cell(infoTableRow2.getCell(0), showManages, exportData);
        }
    }

    public void cell(XWPFTableCell cell, List<ShowManage> showManages, ExportData exportData) throws IOException, InvalidFormatException {
        CTP ctp = CTP.Factory.newInstance();
        //行宽
        cell.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(9000));
        //垂直居中
        cell.getCTTc().addNewTcPr().addNewVAlign().setVal(STVerticalJc.CENTER);
        //内容以及字体
        XWPFParagraph p = new XWPFParagraph(ctp, cell);
        p.setAlignment(ParagraphAlignment.LEFT);
        p.setSpacingBefore(100);
        p.setSpacingAfter(100);
        XWPFRun run = p.createRun();
        for (ShowManage showManage : showManages) {
            run.setText("  " + showManage.getRoadName() + " > " + showManage.getType());
            run.addBreak();
            run.addBreak();
            for (PhotoUrl photoUrl : showManage.getPhotoUrls()) {
                String[] str1 = photoUrl.getPath().split(exportData.getFileContext() + "/static/");
                String filePath = exportData.getFilePath().replace("//", "/");
                String[] name = photoUrl.getName().split("\\.");
                int type = XWPFDocument.PICTURE_TYPE_JPEG;
                if ("jpg".equals(name[1]) || "jpeg".equals(name[1])) {
                    type = XWPFDocument.PICTURE_TYPE_JPEG;
                } else if ("png".equals(name[1])) {
                    type = XWPFDocument.PICTURE_TYPE_PNG;
                } else if ("bmp".equals(name[1])) {
                    type = XWPFDocument.PICTURE_TYPE_BMP;
                }
                String resultPath = filePath + str1[1];
                File picture = new File(resultPath);
                BufferedImage sourceImg = ImageIO.read(new FileInputStream(picture));
                Integer width = sourceImg.getWidth();
                Integer height = sourceImg.getHeight();
                if (width > 300) {
                    Integer i = (int) Math.floor(width / 300);
                    width = width / i;
                    height = height / i;
                }
                if (height > 250) {
                    Integer i = (int) Math.floor(height / 250);
                    width = width / i;
                    height = height / i;
                }
                run.addPicture(new FileInputStream(resultPath), type, photoUrl.getName(), Units.toEMU(width), Units.toEMU(height));
                run.addBreak();
            }

            for (DetectionRecord detectionRecord : showManage.getList()) {
                run.setText(" " + detectionRecord.getText());
                run.addBreak();
            }
            run.addBreak();
        }
        CTFonts font = run.getCTR().addNewRPr().addNewRFonts();
        font.setEastAsia("楷体"); //改变中文字体设置这个
        font.setAscii("Times New Roman");    //改变数字或者英文字体需要设置这个
        run.setFontSize(12);

        cell.setParagraph(p);
    }


    /**
     * 单元格内容样式
     *
     * @param cell     单元格对象
     * @param wordData 数据对象
     */
    public void tableStyle(XWPFTableCell cell, WordCellData wordData) {
        CTP ctp = CTP.Factory.newInstance();
        //行宽
        cell.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(wordData.getValue()));
        //垂直居中
        cell.getCTTc().addNewTcPr().addNewVAlign().setVal(STVerticalJc.CENTER);
        //内容以及字体
        XWPFParagraph p = new XWPFParagraph(ctp, cell);
        if (wordData.getAlignment() == 1) {
            p.setAlignment(ParagraphAlignment.CENTER);
        } else {
            p.setAlignment(ParagraphAlignment.LEFT);
        }
        p.setSpacingBefore(100);
        p.setSpacingAfter(100);
        XWPFRun run = p.createRun();
        if (wordData.getIs() != 1) {
            run.setText(wordData.getName());
        } else {
            Integer fenHang = 12;
            Integer length = wordData.getName().length() / fenHang;
            Integer length2 = wordData.getName().length() % fenHang;
            for (int i = 0; i < length; i++) {
                run.setText(wordData.getName().substring(i * fenHang, (i + 1) * fenHang));
                run.addBreak();
            }
            run.setText(wordData.getName().substring(length * fenHang, length * fenHang + length2));
        }


        CTFonts font = run.getCTR().addNewRPr().addNewRFonts();
        font.setEastAsia(wordData.getFontFamily()); //改变中文字体设置这个
        font.setAscii("Times New Roman");    //改变数字或者英文字体需要设置这个
        run.setFontSize(wordData.getFontSize());
        if (wordData.getBold() == 1) {
            run.setBold(true);
        }
        cell.setParagraph(p);
    }

    public void myCreateParagraph(XWPFDocument document, String styleText, ParagraphAlignment alignment, String mainText, boolean bold,
                                  String color, String family, int fontSize, boolean pageBreak, int firstLine, int spacingBefore) {
        OriginalRecordUtils utils = new OriginalRecordUtils();
        XWPFParagraph para = document.createParagraph();
        //对齐方式
        para.setAlignment(alignment);
        XWPFRun run = para.createRun();
        //内容
        run.setText(mainText);
        //加粗
        run.setBold(bold);
        //颜色
        run.setColor(color);
        //字体
        CTFonts font = run.getCTR().addNewRPr().addNewRFonts();
        font.setEastAsia(family); //改变中文字体设置这个
        font.setAscii("Times New Roman");    //改变数字或者英文字体需要设置这个
        //字号
        run.setFontSize(fontSize);
        //行间距
        utils.setSingleLineSpacing(para);
        //段前分页
        para.setPageBreak(pageBreak);
        //首行缩进
        para.setIndentationFirstLine(firstLine);
        //标题对应
        para.setStyle(styleText);
        //段前间距
        para.setSpacingBefore(spacingBefore);

    }

    public void setSingleLineSpacing(XWPFParagraph para) {
        CTP ctp = para.getCTP();
        CTPPr ppr = ctp.isSetPPr() ? ctp.getPPr() : ctp.addNewPPr();
        CTSpacing spacing = ppr.isSetSpacing() ? ppr.getSpacing() : ppr.addNewSpacing();
        spacing.setAfter(BigInteger.valueOf(0));
        spacing.setBefore(BigInteger.valueOf(0));
        //注意设置行距类型为 EXACT
        spacing.setLineRule(STLineSpacingRule.EXACT);
        //1磅数是20
        spacing.setLine(BigInteger.valueOf(400));
    }

    /**
     * 列合并
     *
     * @param table    表格对象
     * @param row      行
     * @param fromCell 开始列
     * @param toCell   结束列
     */
    private void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {
        for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
            XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
            if (cellIndex == fromCell) {
                // The first merged cell is set with RESTART merge value
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }


}
