package com.iware.bridge.evaluation.Utils;

import com.iware.bridge.evaluation.Utils.model.WordCellData;
import com.iware.bridge.evaluation.Utils.model.WordCustomTOC;
import com.iware.bridge.evaluation.vo.PhotoUrl;
import com.iware.bridge.evaluation.vo.WordBasicData;
import com.iware.bridge.evaluation.vo.WordCheckResults;
import com.iware.bridge.evaluation.vo.WordEvaluateResults;
import com.iware.bridge.model.entity.evaluation.Attachment;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.beans.BeanUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.*;

public class TestReportUtils {


    public static String myCreateWord(WordBasicData wordBasicData, int roadId, String fileName) throws IOException, InvalidFormatException, XmlException {

        //Blank Document
        XWPFDocument document = new XWPFDocument();
        TestReportUtils poiWord = new TestReportUtils();
        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
        CTPageMar pageMar = sectPr.addNewPgMar();
        pageMar.setLeft(BigInteger.valueOf(1700L));//3
        pageMar.setTop(BigInteger.valueOf(850L));//1.5
        pageMar.setRight(BigInteger.valueOf(965L));//1.7
        pageMar.setBottom(BigInteger.valueOf(965));//1.7

        UUID uuid = UUID.randomUUID();

        String path = wordBasicData.getFile() + "/testReport/" + roadId + "/" + uuid;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String path2 = wordBasicData.getFile() + "/testReport/" + roadId + "/" + uuid + "/" + fileName + ".doc";
        //Write the Document in file system
        FileOutputStream out = new FileOutputStream(new File(path2));

        DecimalFormat df = new DecimalFormat("######0.00");


        //添加标题
        XWPFParagraph titleParagraph = document.createParagraph();
        //设置段落居中
        titleParagraph.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun titleParagraphRun = titleParagraph.createRun();
        titleParagraphRun.setText("桥梁常规定期检测报告");
        titleParagraphRun.setBold(true);
        titleParagraphRun.setColor("000000");
        titleParagraphRun.setFontFamily("黑体");
        titleParagraphRun.setFontSize(16);

        //创建一个表格
        XWPFTable infoTable = document.createTable(8, 5);


        CTTblBorders borders = infoTable.getCTTbl().getTblPr().addNewTblBorders();

        borders.addNewLeft().setSz(new BigInteger("15"));
        borders.addNewRight().setSz(new BigInteger("15"));
        borders.addNewTop().setSz(new BigInteger("15"));
        borders.addNewBottom().setSz(new BigInteger("15"));
        borders.addNewInsideH().setSz(new BigInteger("8"));
        borders.addNewInsideV().setSz(new BigInteger("8"));

        List<List<WordCellData>> list1 = new ArrayList<>();


        //1
        List<WordCellData> list2 = new ArrayList<>();
        WordCellData wordData = new WordCellData();
        wordData.setName("委托   单位");
        wordData.setValue(800);
        wordData.setAlignment(1);
        wordData.setFontFamily("黑体");
        wordData.setFontSize(12);
        wordData.setHeight(600);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("名称");
        wordData.setValue(800);
        wordData.setAlignment(1);
        wordData.setFontFamily("黑体");
        wordData.setFontSize(12);
        wordData.setHeight(600);
        list2.add(wordData);

        wordData = new WordCellData();
//        wordData.setName(wordBasicData.getUserName());
        wordData.setName("");
        wordData.setValue(3300);
        wordData.setAlignment(0);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(12);
        wordData.setHeight(600);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("合同编号");
        wordData.setValue(1200);
        wordData.setAlignment(1);
        wordData.setFontFamily("黑体");
        wordData.setFontSize(12);
        wordData.setHeight(600);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("");
        wordData.setValue(3300);
        wordData.setAlignment(0);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(12);
        wordData.setHeight(600);
        list2.add(wordData);
        list1.add(list2);

        //2
        list2 = new ArrayList<>();
        wordData = new WordCellData();
        wordData.setName("");
        wordData.setValue(800);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(12);
        wordData.setHeight(600);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("地址");
        wordData.setValue(800);
        wordData.setAlignment(1);
        wordData.setFontFamily("黑体");
        wordData.setFontSize(12);
        wordData.setHeight(600);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("");
        wordData.setValue(3300);
        wordData.setAlignment(0);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(12);
        wordData.setHeight(600);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("检验时间");
        wordData.setValue(1200);
        wordData.setAlignment(1);
        wordData.setFontFamily("黑体");
        wordData.setFontSize(12);
        wordData.setHeight(600);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("");
        wordData.setValue(3300);
        wordData.setAlignment(0);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(12);
        wordData.setHeight(600);
        list2.add(wordData);
        list1.add(list2);
        //3
        list2 = new ArrayList<>();
        wordData = new WordCellData();
        wordData.setName("工程名称");
        wordData.setValue(1);
        wordData.setAlignment(1);
        wordData.setFontFamily("黑体");
        wordData.setFontSize(12);
        wordData.setHeight(600);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("工程名称");
        wordData.setValue(1600);
        wordData.setAlignment(1);
        wordData.setFontFamily("黑体");
        wordData.setFontSize(12);
        wordData.setHeight(600);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("");
        wordData.setValue(3300);
        wordData.setAlignment(0);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(12);
        wordData.setHeight(600);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("工程地点");
        wordData.setValue(1200);
        wordData.setAlignment(1);
        wordData.setFontFamily("黑体");
        wordData.setFontSize(12);
        wordData.setHeight(600);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName(wordBasicData.getProjectSite());
        wordData.setValue(3300);
        wordData.setAlignment(0);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(12);
        wordData.setHeight(600);
        wordData.setIs(1);
        list2.add(wordData);
        list1.add(list2);

        //4
        list2 = new ArrayList<>();
        wordData = new WordCellData();
        wordData.setName("检验内容");
        wordData.setValue(1);
        wordData.setAlignment(1);
        wordData.setFontFamily("黑体");
        wordData.setFontSize(12);
        wordData.setHeight(600);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("检验内容");
        wordData.setValue(1600);
        wordData.setAlignment(1);
        wordData.setFontFamily("黑体");
        wordData.setFontSize(12);
        wordData.setHeight(600);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName(wordBasicData.getDetectionType());
        wordData.setValue(3300);
        wordData.setAlignment(0);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(12);
        wordData.setHeight(600);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("检验类别");
        wordData.setValue(1200);
        wordData.setAlignment(1);
        wordData.setFontFamily("黑体");
        wordData.setFontSize(12);
        wordData.setHeight(600);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("");
        wordData.setValue(3300);
        wordData.setAlignment(0);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(12);
        wordData.setHeight(600);
        list2.add(wordData);
        list1.add(list2);

        //5
        list2 = new ArrayList<>();
        wordData = new WordCellData();
        wordData.setName("检验依据");
        wordData.setValue(1);
        wordData.setAlignment(1);
        wordData.setFontFamily("黑体");
        wordData.setFontSize(12);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("检验依据");
        wordData.setValue(1600);
        wordData.setAlignment(1);
        wordData.setFontFamily("黑体");
        wordData.setFontSize(12);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName(wordBasicData.getDetectionBase());
        wordData.setValue(7798);
        wordData.setAlignment(0);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(12);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("");
        wordData.setValue(1);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(12);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("");
        wordData.setValue(1);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(12);
        wordData.setHeight(800);
        list2.add(wordData);
        list1.add(list2);


        //6
        list2 = new ArrayList<>();
        wordData = new WordCellData();
        wordData.setName("检验结论");
        wordData.setValue(1);
        wordData.setAlignment(1);
        wordData.setFontFamily("黑体");
        wordData.setFontSize(12);
        wordData.setHeight(1200);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("检验结论");
        wordData.setValue(1600);
        wordData.setAlignment(1);
        wordData.setFontFamily("黑体");
        wordData.setFontSize(12);
        wordData.setHeight(1200);
        list2.add(wordData);

        String bciSSS = df.format(Float.parseFloat(wordBasicData.getBciScore()));
        wordBasicData.setBciScore(bciSSS);

        wordData = new WordCellData();
        wordData.setName("    整个桥梁的技术状况指数BCI为" + wordBasicData.getBciScore() + "，整个桥梁完好状态等级评估为" + wordBasicData.getBciLevel() + "级");
        wordData.setValue(7798);
        wordData.setAlignment(0);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(12);
        wordData.setHeight(1200);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("");
        wordData.setValue(1);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(12);
        wordData.setHeight(1200);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("");
        wordData.setValue(1);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(12);
        wordData.setHeight(1200);
        list2.add(wordData);
        list1.add(list2);

        //7
        list2 = new ArrayList<>();
        wordData = new WordCellData();
        wordData.setName("建议");
        wordData.setValue(1);
        wordData.setAlignment(1);
        wordData.setFontFamily("黑体");
        wordData.setFontSize(12);
        wordData.setHeight(6600);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("建议");
        wordData.setValue(1600);
        wordData.setAlignment(1);
        wordData.setFontFamily("黑体");
        wordData.setFontSize(12);
        wordData.setHeight(6600);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("  ");
        wordData.setValue(7798);
        wordData.setAlignment(0);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(12);
        wordData.setHeight(6600);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("");
        wordData.setValue(1);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(12);
        wordData.setHeight(6600);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("");
        wordData.setValue(1);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(12);
        wordData.setHeight(6600);
        list2.add(wordData);
        list1.add(list2);


        //8
        list2 = new ArrayList<>();
        wordData = new WordCellData();
        wordData.setName("");
        wordData.setValue(1);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(12);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("备注");
        wordData.setValue(1600);
        wordData.setAlignment(1);
        wordData.setFontFamily("黑体");
        wordData.setFontSize(12);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("“检验依据”中编号1的标准为我公CMA计量认证范围标准。");
        wordData.setValue(7798);
        wordData.setAlignment(0);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("");
        wordData.setValue(1);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("");
        wordData.setValue(1);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setHeight(800);
        list2.add(wordData);
        list1.add(list2);

        poiWord.tableData(infoTable, list1);
        poiWord.mergeCellsVertically(infoTable, 0, 0, 1);
        poiWord.mergeCellsHorizontal(infoTable, 2, 0, 1);
        poiWord.mergeCellsHorizontal(infoTable, 3, 0, 1);
        poiWord.mergeCellsHorizontal(infoTable, 4, 0, 1);
        poiWord.mergeCellsHorizontal(infoTable, 4, 2, 4);
        poiWord.mergeCellsHorizontal(infoTable, 5, 0, 1);
        poiWord.mergeCellsHorizontal(infoTable, 5, 2, 4);
        poiWord.mergeCellsHorizontal(infoTable, 6, 0, 1);
        poiWord.mergeCellsHorizontal(infoTable, 6, 2, 4);
        poiWord.mergeCellsHorizontal(infoTable, 7, 0, 1);
        poiWord.mergeCellsHorizontal(infoTable, 7, 2, 4);

        document.createParagraph().createRun().addBreak();//添加一个回车空行

        XWPFTable infoTable2 = document.createTable(1, 8);
        CTTblBorders borders2 = infoTable2.getCTTbl().getTblPr().addNewTblBorders();
        borders2.addNewLeft().setVal(STBorder.Enum.forString("dashed"));
        borders2.addNewLeft().setSz(new BigInteger("1"));
        borders2.addNewLeft().setColor("808080");
        borders2.addNewRight().setVal(STBorder.Enum.forString("dashed"));
        borders2.addNewRight().setSz(new BigInteger("1"));
        borders2.addNewRight().setColor("808080");
        borders2.addNewTop().setVal(STBorder.Enum.forString("dashed"));
        borders2.addNewTop().setSz(new BigInteger("1"));
        borders2.addNewTop().setColor("808080");
        borders2.addNewBottom().setVal(STBorder.Enum.forString("dashed"));
        borders2.addNewBottom().setSz(new BigInteger("1"));
        borders2.addNewBottom().setColor("808080");
        borders2.addNewInsideH().setVal(STBorder.Enum.forString("dashed"));
        borders.addNewInsideH().setSz(new BigInteger("8"));
        borders2.addNewInsideH().setColor("808080");
        borders2.addNewInsideV().setVal(STBorder.Enum.forString("dashed"));
        borders.addNewInsideV().setSz(new BigInteger("8"));
        borders2.addNewInsideV().setColor("808080");

        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        wordData = new WordCellData();
        wordData.setName("批准：");
        wordData.setValue(800);
        wordData.setAlignment(1);
        wordData.setFontFamily("黑体");
        wordData.setFontSize(11);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("");
        wordData.setValue(1400);
        wordData.setAlignment(1);
        wordData.setFontFamily("黑体");
        wordData.setFontSize(11);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("审核：");
        wordData.setValue(800);
        wordData.setAlignment(1);
        wordData.setFontFamily("黑体");
        wordData.setFontSize(11);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("");
        wordData.setValue(1400);
        wordData.setAlignment(1);
        wordData.setFontFamily("黑体");
        wordData.setFontSize(11);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("校核：");
        wordData.setValue(800);
        wordData.setAlignment(1);
        wordData.setFontFamily("黑体");
        wordData.setFontSize(11);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("");
        wordData.setValue(1400);
        wordData.setAlignment(1);
        wordData.setFontFamily("黑体");
        wordData.setFontSize(11);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("项目负责：");
        wordData.setValue(1200);
        wordData.setAlignment(1);
        wordData.setFontFamily("黑体");
        wordData.setFontSize(11);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("");
        wordData.setValue(1600);
        wordData.setAlignment(1);
        wordData.setFontFamily("黑体");
        wordData.setFontSize(11);
        wordData.setHeight(800);
        list2.add(wordData);
        list1.add(list2);
        poiWord.tableData(infoTable2, list1);


        WordCustomTOC customTOC = new WordCustomTOC(document.getDocument().getBody().addNewSdt());
        customTOC.myToc();


        poiWord.myCreateParagraph(document, "Heading1", ParagraphAlignment.CENTER, "第 1 章 检测概况及说明", true, "000000", "楷体_GB2312", 12, true, 0, 120);
        poiWord.myCreateParagraph(document, "Heading2", ParagraphAlignment.LEFT, "1.1 工程概况", true, "000000", "楷体_GB2312", 12, false, 0, 120);
        document.createParagraph().createRun().addBreak();//添加一个回车空行
        document.createParagraph().createRun().addBreak();//添加一个回车空行
        String str = "梁现状照片见";

        for (int i = 1; i <= wordBasicData.getPhotoManage().size(); i++) {
            if (i != wordBasicData.getPhotoManage().size()) {
                str += "图1-" + i + "、";
            } else {
                str += "图1-" + i + "。";
            }
        }
        poiWord.myCreateParagraph(document, "", ParagraphAlignment.LEFT, str, false, "000000", "楷体_GB2312", 12, false, 480, 0);
        for (int i = 1; i <= wordBasicData.getPhotoManage().size(); i++) {
            String[] str1 = wordBasicData.getPhotoManage().get(i - 1).getPath().split(wordBasicData.getContext() + "/static/");
            String filePath = wordBasicData.getFile().replace("//", "/");
            String[] name = wordBasicData.getPhotoManage().get(i - 1).getName().split("\\.");
            int type = XWPFDocument.PICTURE_TYPE_JPEG;
            if ("jpg".equals(name[1]) || "jpeg".equals(name[1])) {
                type = XWPFDocument.PICTURE_TYPE_JPEG;
            } else if ("png".equals(name[1])) {
                type = XWPFDocument.PICTURE_TYPE_PNG;
            } else if ("bmp".equals(name[1])) {
                type = XWPFDocument.PICTURE_TYPE_BMP;
            }
            String fp = filePath + str1[1];
            poiWord.myCreateImg(document, ParagraphAlignment.CENTER, fp, type, wordBasicData.getPhotoManage().get(i - 1).getName(), 250, 170);
            poiWord.myCreateParagraph(document, "", ParagraphAlignment.CENTER, "图 1-" + i + " " + name[0], false, "000000", "楷体_GB2312", 11, false, 0, 0);

        }
        poiWord.myCreateParagraph(document, "Heading2", ParagraphAlignment.LEFT, "1.2 检验内容与目的", true, "000000", "楷体_GB2312", 12, false, 0, 120);
        poiWord.myCreateParagraph(document, "", ParagraphAlignment.LEFT, "按规范要求对桥梁的桥面系、上部结构、下部结构的缺损状况进行检查，掌握桥梁的基本状况；根据桥梁的缺损状况检查结果，依据《城市桥梁养护技术规范》（CJJ99-2017）对桥梁技术状况等级进行评估，为桥梁维修养护提供技术依据。", false, "000000", "楷体_GB2312", 12, false, 480, 0);
        poiWord.myCreateParagraph(document, "Heading2", ParagraphAlignment.LEFT, "1.3 技术状况评估标准", true, "000000", "楷体_GB2312", 12, false, 0, 120);
        poiWord.myCreateParagraph(document, "", ParagraphAlignment.LEFT, "根据国家行业标准《城市桥梁养护技术标准》（CJJ 99-2017）的规定：", false, "000000", "楷体_GB2312", 12, false, 480, 0);
        poiWord.myCreateParagraph(document, "", ParagraphAlignment.LEFT, "（1）II~V类养护的城市桥梁完好状态宜分为5个等级：", false, "000000", "楷体_GB2312", 12, false, 480, 0);
        poiWord.myCreateParagraph(document, "", ParagraphAlignment.CENTER, "表1-1 II~V类养护的城市桥梁完好状态分级", false, "000000", "楷体_GB2312", 12, false, 480, 0);

        XWPFTable infoTable12 = document.createTable(6, 4);
        infoTable12.getCTTbl().addNewTblPr().addNewJc().setVal(STJc.CENTER);
        CTTblBorders borders12 = infoTable12.getCTTbl().getTblPr().addNewTblBorders();
        borders12.addNewLeft().setSz(new BigInteger("15"));
        borders12.addNewRight().setSz(new BigInteger("15"));
        borders12.addNewTop().setSz(new BigInteger("15"));
        borders12.addNewBottom().setSz(new BigInteger("15"));


        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        wordData = new WordCellData();
        wordData.setName("等级");
        wordData.setValue(1200);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(400);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("状态");
        wordData.setValue(1200);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(400);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("BCI范围");
        wordData.setValue(2000);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(400);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("养护对策");
        wordData.setValue(4200);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(400);
        list2.add(wordData);
        list1.add(list2);

        List<String> dataStr1 = new ArrayList<>();
        dataStr1.add("A级");
        dataStr1.add("完好");
        dataStr1.add("[90,100]");
        dataStr1.add("日常养护");

        List<String> dataStr2 = new ArrayList<>();
        dataStr2.add("B级");
        dataStr2.add("良好");
        dataStr2.add("[80,90)");
        dataStr2.add("保养小修");

        List<String> dataStr3 = new ArrayList<>();
        dataStr3.add("C级");
        dataStr3.add("合格");
        dataStr3.add("[66,80)");
        dataStr3.add("针对性小修或中修工程");

        List<String> dataStr4 = new ArrayList<>();
        dataStr4.add("D级");
        dataStr4.add("不合格");
        dataStr4.add("[50,66)");
        dataStr4.add("检测评估后进行中修、大修或加固工程");

        List<String> dataStr5 = new ArrayList<>();
        dataStr5.add("E级");
        dataStr5.add("危险");
        dataStr5.add("[0,50)");
        dataStr5.add("检测评估后进行大修、加固或扩建工程");
        list2 = new ArrayList<>();

        for (int i = 0; i < dataStr1.size(); i++) {
            wordData = new WordCellData();
            wordData.setName(dataStr1.get(i));
            if (i == 0) {
                wordData.setValue(1200);
            } else if (i == 1) {
                wordData.setValue(1200);
            } else if (i == 2) {
                wordData.setValue(2000);
            } else if (i == 3) {
                wordData.setValue(4200);
            }
            wordData.setAlignment(1);
            wordData.setFontFamily("楷体_GB2312");
            wordData.setFontSize(11);
            wordData.setHeight(400);
            list2.add(wordData);
            if (dataStr1.size() == i + 1) {
                list1.add(list2);
            }
        }
        list2 = new ArrayList<>();
        for (int i = 0; i < dataStr2.size(); i++) {
            wordData = new WordCellData();
            wordData.setName(dataStr2.get(i));
            if (i == 0) {
                wordData.setValue(1200);
            } else if (i == 1) {
                wordData.setValue(1200);
            } else if (i == 2) {
                wordData.setValue(2000);
            } else if (i == 3) {
                wordData.setValue(4200);
            }
            wordData.setAlignment(1);
            wordData.setFontFamily("楷体_GB2312");
            wordData.setFontSize(11);
            wordData.setHeight(400);
            list2.add(wordData);
            if (dataStr1.size() == i + 1) {
                list1.add(list2);
            }
        }
        list2 = new ArrayList<>();
        for (int i = 0; i < dataStr3.size(); i++) {
            wordData = new WordCellData();
            wordData.setName(dataStr3.get(i));
            if (i == 0) {
                wordData.setValue(1200);
            } else if (i == 1) {
                wordData.setValue(1200);
            } else if (i == 2) {
                wordData.setValue(2000);
            } else if (i == 3) {
                wordData.setValue(4200);
            }
            wordData.setAlignment(1);
            wordData.setFontFamily("楷体_GB2312");
            wordData.setFontSize(11);
            wordData.setHeight(400);
            list2.add(wordData);
            if (dataStr1.size() == i + 1) {
                list1.add(list2);
            }
        }
        list2 = new ArrayList<>();
        for (int i = 0; i < dataStr4.size(); i++) {
            wordData = new WordCellData();
            wordData.setName(dataStr4.get(i));
            if (i == 0) {
                wordData.setValue(1200);
            } else if (i == 1) {
                wordData.setValue(1200);
            } else if (i == 2) {
                wordData.setValue(2000);
            } else if (i == 3) {
                wordData.setValue(4200);
            }
            wordData.setAlignment(1);
            wordData.setFontFamily("楷体_GB2312");
            wordData.setFontSize(11);
            wordData.setHeight(400);
            list2.add(wordData);
            if (dataStr1.size() == i + 1) {
                list1.add(list2);
            }
        }
        list2 = new ArrayList<>();
        for (int i = 0; i < dataStr5.size(); i++) {
            wordData = new WordCellData();
            wordData.setName(dataStr5.get(i));
            if (i == 0) {
                wordData.setValue(1200);
            } else if (i == 1) {
                wordData.setValue(1200);
            } else if (i == 2) {
                wordData.setValue(2000);
            } else if (i == 3) {
                wordData.setValue(4200);
            }
            wordData.setAlignment(1);
            wordData.setFontFamily("楷体_GB2312");
            wordData.setFontSize(11);
            wordData.setHeight(400);
            list2.add(wordData);
            if (dataStr1.size() == i + 1) {
                list1.add(list2);
            }
        }


        poiWord.tableData(infoTable12, list1);


        poiWord.myCreateParagraph(document, "", ParagraphAlignment.LEFT, "（2）II~V类养护的城市桥梁结构状态宜分为5个等级：", false, "000000", "楷体_GB2312", 12, false, 480, 0);
        poiWord.myCreateParagraph(document, "", ParagraphAlignment.CENTER, "表1-2 II~V类养护的城市桥梁结构状态分级", false, "000000", "楷体_GB2312", 12, false, 480, 0);


        XWPFTable infoTable13 = document.createTable(6, 4);
        infoTable13.getCTTbl().addNewTblPr().addNewJc().setVal(STJc.CENTER);
        CTTblBorders borders13 = infoTable13.getCTTbl().getTblPr().addNewTblBorders();
        borders13.addNewLeft().setSz(new BigInteger("15"));
        borders13.addNewRight().setSz(new BigInteger("15"));
        borders13.addNewTop().setSz(new BigInteger("15"));
        borders13.addNewBottom().setSz(new BigInteger("15"));


        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        wordData = new WordCellData();
        wordData.setName("等级");
        wordData.setValue(1200);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(400);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("状态");
        wordData.setValue(1200);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(400);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("BCI范围");
        wordData.setValue(2000);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(400);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("养护对策");
        wordData.setValue(4200);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(400);
        list2.add(wordData);
        list1.add(list2);

        List<String> dataStr6 = new ArrayList<>();
        dataStr6.add("A级");
        dataStr6.add("完好");
        dataStr6.add("[90,100]");
        dataStr6.add("日常养护");

        List<String> dataStr7 = new ArrayList<>();
        dataStr7.add("B级");
        dataStr7.add("良好");
        dataStr7.add("[80,90)");
        dataStr7.add("保养小修");

        List<String> dataStr8 = new ArrayList<>();
        dataStr8.add("C级");
        dataStr8.add("合格");
        dataStr8.add("[66,80)");
        dataStr8.add("针对性小修或局部中修工程");

        List<String> dataStr9 = new ArrayList<>();
        dataStr9.add("D级");
        dataStr9.add("不合格");
        dataStr9.add("[50,66)");
        dataStr9.add("检测评估后进行局部中修、大修或加固工程");

        List<String> dataStr10 = new ArrayList<>();
        dataStr10.add("E级");
        dataStr10.add("危险");
        dataStr10.add("[0,50)");
        dataStr10.add("检测评估后进行大修、加固或扩建工程");
        list2 = new ArrayList<>();

        for (int i = 0; i < dataStr6.size(); i++) {
            wordData = new WordCellData();
            wordData.setName(dataStr6.get(i));
            if (i == 0) {
                wordData.setValue(1200);
            } else if (i == 1) {
                wordData.setValue(1200);
            } else if (i == 2) {
                wordData.setValue(2000);
            } else if (i == 3) {
                wordData.setValue(4200);
            }
            wordData.setAlignment(1);
            wordData.setFontFamily("楷体_GB2312");
            wordData.setFontSize(11);
            wordData.setHeight(400);
            list2.add(wordData);
            if (dataStr1.size() == i + 1) {
                list1.add(list2);
            }
        }
        list2 = new ArrayList<>();
        for (int i = 0; i < dataStr7.size(); i++) {
            wordData = new WordCellData();
            wordData.setName(dataStr7.get(i));
            if (i == 0) {
                wordData.setValue(1200);
            } else if (i == 1) {
                wordData.setValue(1200);
            } else if (i == 2) {
                wordData.setValue(2000);
            } else if (i == 3) {
                wordData.setValue(4200);
            }
            wordData.setAlignment(1);
            wordData.setFontFamily("楷体_GB2312");
            wordData.setFontSize(11);
            wordData.setHeight(400);
            list2.add(wordData);
            if (dataStr1.size() == i + 1) {
                list1.add(list2);
            }
        }
        list2 = new ArrayList<>();
        for (int i = 0; i < dataStr8.size(); i++) {
            wordData = new WordCellData();
            wordData.setName(dataStr8.get(i));
            if (i == 0) {
                wordData.setValue(1200);
            } else if (i == 1) {
                wordData.setValue(1200);
            } else if (i == 2) {
                wordData.setValue(2000);
            } else if (i == 3) {
                wordData.setValue(4200);
            }
            wordData.setAlignment(1);
            wordData.setFontFamily("楷体_GB2312");
            wordData.setFontSize(11);
            wordData.setHeight(400);
            list2.add(wordData);
            if (dataStr1.size() == i + 1) {
                list1.add(list2);
            }
        }
        list2 = new ArrayList<>();
        for (int i = 0; i < dataStr9.size(); i++) {
            wordData = new WordCellData();
            wordData.setName(dataStr9.get(i));
            if (i == 0) {
                wordData.setValue(1200);
            } else if (i == 1) {
                wordData.setValue(1200);
            } else if (i == 2) {
                wordData.setValue(2000);
            } else if (i == 3) {
                wordData.setValue(4200);
            }
            wordData.setAlignment(1);
            wordData.setFontFamily("楷体_GB2312");
            wordData.setFontSize(11);
            wordData.setHeight(400);
            list2.add(wordData);
            if (dataStr1.size() == i + 1) {
                list1.add(list2);
            }
        }
        list2 = new ArrayList<>();
        for (int i = 0; i < dataStr10.size(); i++) {
            wordData = new WordCellData();
            wordData.setName(dataStr10.get(i));
            if (i == 0) {
                wordData.setValue(1200);
            } else if (i == 1) {
                wordData.setValue(1200);
            } else if (i == 2) {
                wordData.setValue(2000);
            } else if (i == 3) {
                wordData.setValue(4200);
            }
            wordData.setAlignment(1);
            wordData.setFontFamily("楷体_GB2312");
            wordData.setFontSize(11);
            wordData.setHeight(400);
            list2.add(wordData);
            if (dataStr1.size() == i + 1) {
                list1.add(list2);
            }
        }


        poiWord.tableData(infoTable13, list1);

        poiWord.myCreateParagraph(document, "Heading2", ParagraphAlignment.LEFT, "1.4 检测仪器设备", true, "000000", "楷体_GB2312", 12, false, 0, 120);
        poiWord.myCreateParagraph(document, "", ParagraphAlignment.LEFT, "现场主要检测仪器设备如表1-1所示。", false, "000000", "楷体_GB2312", 12, false, 480, 0);
        poiWord.myCreateParagraph(document, "", ParagraphAlignment.CENTER, "表 1-1 现场主要检测仪器设备一览表", true, "000000", "楷体_GB2312", 11, false, 0, 0);


        XWPFTable infoTable3 = document.createTable(4, 3);
        infoTable3.getCTTbl().addNewTblPr().addNewJc().setVal(STJc.CENTER);
        CTTblBorders borders3 = infoTable3.getCTTbl().getTblPr().addNewTblBorders();
        borders3.addNewLeft().setSz(new BigInteger("15"));
        borders3.addNewRight().setSz(new BigInteger("15"));
        borders3.addNewTop().setSz(new BigInteger("15"));
        borders3.addNewBottom().setSz(new BigInteger("15"));


        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        wordData = new WordCellData();
        wordData.setName("仪器名称");
        wordData.setValue(3200);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(400);

        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("型号规格");
        wordData.setValue(2000);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(400);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("管理编号");
        wordData.setValue(3200);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(400);
        list2.add(wordData);
        list1.add(list2);


        for (int i = 0; i < 3; i++) {
            list2 = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                wordData = new WordCellData();
                wordData.setName("");
                if (j == 1) {
                    wordData.setValue(2000);
                } else {
                    wordData.setValue(3200);
                }
                wordData.setAlignment(1);
                wordData.setFontFamily("楷体_GB2312");
                wordData.setFontSize(11);
                wordData.setHeight(400);
                list2.add(wordData);
            }
            list1.add(list2);
        }
        poiWord.tableData(infoTable3, list1);

        poiWord.myCreateParagraph(document, "Heading2", ParagraphAlignment.LEFT, "1.5 构件编号说明", true, "000000", "楷体_GB2312", 12, false, 0, 120);
        document.createParagraph().createRun().addBreak();//添加一个回车空行
//        document.createParagraph().setPageBreak(true);//跳到下一页

        poiWord.myCreateParagraph(document, "Heading1", ParagraphAlignment.CENTER, "第 2 章 桥梁缺损状况检查", true, "000000", "楷体_GB2312", 12, true, 0, 120);
        poiWord.myCreateParagraph(document, "Heading2", ParagraphAlignment.LEFT, "2.1 桥面系缺损状况", true, "000000", "楷体_GB2312", 12, false, 0, 120);
        poiWord.myCreateParagraph(document, "", ParagraphAlignment.LEFT, "本次检测中所发现的桥面系病害位置如图2-1所示；桥面系缺损状况检查结果详见表 2-1。", false, "000000", "楷体_GB2312", 12, false, 480, 0);
        for (PhotoUrl photoUrl : wordBasicData.getTotalBD()) {
            String[] str1 = photoUrl.getPath().split(wordBasicData.getContext() + "/static/");
            String filePath = wordBasicData.getFile().replace("//", "/");
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
            poiWord.myCreateImg(document, ParagraphAlignment.CENTER, resultPath, type, photoUrl.getName(), 430, 280);

        }
        poiWord.myCreateParagraph(document, "", ParagraphAlignment.CENTER, "图2-1 桥面病害位置图（单位：m）", false, "000000", "楷体_GB2312", 11, false, 0, 0);
        poiWord.myCreateParagraph(document, "", ParagraphAlignment.CENTER, "表 2-1 桥面系检查结果汇总表", true, "000000", "楷体_GB2312", 11, false, 0, 0);

        Map<String, Map<String, List<WordCheckResults>>> mapMapBD = poiWord.checkMapDataBD(wordBasicData);

        XWPFTable infoTable4 = document.createTable(wordBasicData.getBD().size() + 1, 6);
        infoTable4.getCTTbl().addNewTblPr().addNewJc().setVal(STJc.CENTER);
        CTTblBorders borders4 = infoTable4.getCTTbl().getTblPr().addNewTblBorders();
        borders4.addNewLeft().setSz(new BigInteger("15"));
        borders4.addNewRight().setSz(new BigInteger("15"));
        borders4.addNewTop().setSz(new BigInteger("15"));
        borders4.addNewBottom().setSz(new BigInteger("15"));


        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        wordData = new WordCellData();
        wordData.setName("序号");
        wordData.setValue(1000);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(400);

        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("构件类型");
        wordData.setValue(1500);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(400);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("缺损类型");
        wordData.setValue(1500);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(400);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("缺损状况描述");
        wordData.setValue(3000);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(400);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("位置图编号");
        wordData.setValue(1500);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(400);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("图示编号");
        wordData.setValue(1500);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(400);
        list2.add(wordData);
        list1.add(list2);

        int id = 1;
        int number = 2;
        for (String s : mapMapBD.keySet()) {
            for (String s1 : mapMapBD.get(s).keySet()) {
                for (int i = 0; i < mapMapBD.get(s).get(s1).size(); i++) {
                    list2 = new ArrayList<>();
                    WordCheckResults wordCheckResults = mapMapBD.get(s).get(s1).get(i);
                    for (int j = 0; j < 6; j++) {
                        wordData = new WordCellData();
                        if (j == 0) {
                            wordData.setName("" + id);
                            wordData.setValue(1000);
                        } else if (j == 1) {
                            wordData.setName("" + wordCheckResults.getName());
                            wordData.setValue(1500);
                        } else if (j == 2) {
                            wordData.setName("" + wordCheckResults.getDiseaseName());
                            wordData.setValue(1500);
                        } else if (j == 3) {
                            wordData.setName("" + wordCheckResults.getSpanCode() + "#跨" + wordCheckResults.getName() + wordCheckResults.getDegree() + wordCheckResults.getDiseaseName() + wordCheckResults.getRemark());
                            wordData.setValue(3000);
                        } else if (j == 4) {
                            if (wordCheckResults.getCode() != null) {
                                wordData.setName("" + wordCheckResults.getCode() + "-" + wordCheckResults.getSort());
                            } else {
                                wordData.setName("");
                            }
                            wordData.setValue(1500);
                        } else if (j == 5) {
//                            if (wordCheckResults.getPath() != null) {
//                                wordData.setName("图 2-" + number);
//                                wordCheckResults.setNumber(number);
//                                number++;
//                            } else {4
//                                wordData.setName("");
//                            }
                            if (wordCheckResults.getPathName().size() != 0) {
                                for (Attachment attachment : wordCheckResults.getPathName()) {
                                    if (wordData.getName() != null) {
                                        wordData.setName(wordData.getName() + "图 2-" + number + "      ");
                                    } else {
                                        wordData.setName("图 2-" + number + "      ");
                                    }
                                    attachment.setType(number);
                                    number++;
                                }
                            } else {
                                wordData.setName("");
                            }
                            wordData.setValue(1500);
                        }
                        wordData.setAlignment(1);
                        wordData.setFontFamily("楷体_GB2312");
                        wordData.setFontSize(11);
                        wordData.setHeight(400);
                        list2.add(wordData);
                    }
                    id++;
                    list1.add(list2);
                }
            }
        }
        poiWord.tableData(infoTable4, list1);
        int merge = 1;
        int merge2 = 0;
        for (String s : mapMapBD.keySet()) {
            for (String s1 : mapMapBD.get(s).keySet()) {
                merge2 += mapMapBD.get(s).get(s1).size();
            }
            poiWord.mergeCellsVertically(infoTable4, 1, merge, merge2);
            merge = merge2 + 1;
        }


        merge = 1;
        for (String s : mapMapBD.keySet()) {
            for (String s1 : mapMapBD.get(s).keySet()) {
                poiWord.mergeCellsVertically(infoTable4, 2, merge, merge + mapMapBD.get(s).get(s1).size() - 1);
                merge += mapMapBD.get(s).get(s1).size();
            }
        }


        List<WordCheckResults> wordCheckResults = new ArrayList<>();
        for (String s : mapMapBD.keySet()) {
            for (String s1 : mapMapBD.get(s).keySet()) {
                for (WordCheckResults s2 : mapMapBD.get(s).get(s1)) {
//                    if (s2.getPath() != null) {
//                        wordCheckResults.add(s2);
//                    }
                    for (Attachment attachment : s2.getPathName()) {
                        WordCheckResults model = new WordCheckResults();
                        BeanUtils.copyProperties(s2, model);
                        model.setPath(attachment.getPath());
                        model.setPhotoName(attachment.getName());
                        model.setNumber(attachment.getType());
                        wordCheckResults.add(model);

                    }
                }
            }
        }

        poiWord.myCheckImg(wordCheckResults, wordBasicData, document);

        poiWord.myCreateParagraph(document, "Heading2", ParagraphAlignment.LEFT, "2.2 上部结构缺损状况", true, "000000", "楷体_GB2312", 12, false, 0, 120);


        poiWord.myCreateParagraph(document, "", ParagraphAlignment.LEFT, "上部结构缺损状况检查结果详见表 2-2。", false, "000000", "楷体_GB2312", 12, false, 480, 0);


        for (PhotoUrl photoUrl : wordBasicData.getTotalSUP()) {
            String[] str1 = photoUrl.getPath().split(wordBasicData.getContext() + "/static/");
            String filePath = wordBasicData.getFile().replace("//", "/");
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
            poiWord.myCreateImg(document, ParagraphAlignment.CENTER, resultPath, type, photoUrl.getName(), 430, 280);

        }


        poiWord.myCreateParagraph(document, "", ParagraphAlignment.CENTER, "表 2-2 上部结构检查结果汇总表", true, "000000", "楷体_GB2312", 11, false, 0, 0);


        Map<String, Map<String, Map<String, List<WordCheckResults>>>> mapMapSUP = poiWord.checkMapDataSUP(wordBasicData);

        XWPFTable infoTable5 = document.createTable(wordBasicData.getSUP().size() + 1, 7);
        infoTable5.getCTTbl().addNewTblPr().addNewJc().setVal(STJc.CENTER);
        CTTblBorders borders5 = infoTable5.getCTTbl().getTblPr().addNewTblBorders();
        borders5.addNewLeft().setSz(new BigInteger("15"));
        borders5.addNewRight().setSz(new BigInteger("15"));
        borders5.addNewTop().setSz(new BigInteger("15"));
        borders5.addNewBottom().setSz(new BigInteger("15"));


        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        wordData = new WordCellData();
        wordData.setName("序号");
        wordData.setValue(800);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(400);

        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("位置");
        wordData.setValue(1000);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(400);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("构件类型");
        wordData.setValue(1400);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(400);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("缺损类型");
        wordData.setValue(1200);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(400);
        list2.add(wordData);


        wordData = new WordCellData();
        wordData.setName("缺损状况描述");
        wordData.setValue(3000);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(400);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("位置图编号");
        wordData.setValue(1500);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(400);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("图示编号");
        wordData.setValue(1500);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(400);
        list2.add(wordData);
        list1.add(list2);


        id = 1;
        for (String s : mapMapSUP.keySet()) {
            for (String s1 : mapMapSUP.get(s).keySet()) {
                for (String s2 : mapMapSUP.get(s).get(s1).keySet()) {
                    for (int i = 0; i < mapMapSUP.get(s).get(s1).get(s2).size(); i++) {
                        list2 = new ArrayList<>();
                        WordCheckResults wordCheckResults3 = mapMapSUP.get(s).get(s1).get(s2).get(i);
                        for (int j = 0; j < 7; j++) {
                            wordData = new WordCellData();
                            if (j == 0) {
                                wordData.setName("" + id);
                                wordData.setValue(800);
                            } else if (j == 1) {
                                wordData.setName("" + wordCheckResults3.getSpanCode() + "#跨");
                                wordData.setValue(1000);
                            } else if (j == 2) {
                                wordData.setName("" + wordCheckResults3.getName());
                                wordData.setValue(1400);
                            } else if (j == 3) {
                                wordData.setName("" + wordCheckResults3.getDiseaseName());
                                wordData.setValue(1200);
                            } else if (j == 4) {
                                wordData.setName("" + wordCheckResults3.getSpanCode() + "#跨" + wordCheckResults3.getName() + wordCheckResults3.getDegree() + wordCheckResults3.getDiseaseName() + wordCheckResults3.getRemark());
                                wordData.setValue(3000);
                            } else if (j == 5) {
                                if (wordCheckResults3.getCode() != null) {
                                    wordData.setName("" + wordCheckResults3.getCode() + "-" + wordCheckResults3.getSort());
                                } else {
                                    wordData.setName("");
                                }
                                wordData.setValue(1500);
                            } else if (j == 6) {
//                                if (wordCheckResults3.getPath() != null) {
//                                    wordData.setName("图 2-" + number);
//                                    wordCheckResults3.setNumber(number);
//                                    number++;
//                                } else {
//                                    wordData.setName("");
//                                }
                                if (wordCheckResults3.getPathName().size() != 0) {
                                    for (Attachment attachment : wordCheckResults3.getPathName()) {
                                        if (wordData.getName() != null) {
                                            wordData.setName(wordData.getName() + "图 2-" + number + "      ");
                                        } else {
                                            wordData.setName("图 2-" + number + "      ");
                                        }
                                        attachment.setType(number);
                                        number++;
                                    }
                                } else {
                                    wordData.setName("");
                                }
                                wordData.setValue(1500);
                            }
                            wordData.setAlignment(1);
                            wordData.setFontFamily("楷体_GB2312");
                            wordData.setFontSize(11);
                            wordData.setHeight(400);
                            list2.add(wordData);
                        }
                        id++;
                        list1.add(list2);
                    }
                }
            }
        }
        poiWord.tableData(infoTable5, list1);

        merge = 1;
        merge2 = 0;
        for (String s : mapMapSUP.keySet()) {
            for (String s1 : mapMapSUP.get(s).keySet()) {
                for (String s2 : mapMapSUP.get(s).get(s1).keySet()) {
                    merge2 += mapMapSUP.get(s).get(s1).get(s2).size();
                }
            }
            poiWord.mergeCellsVertically(infoTable5, 1, merge, merge2);
            merge = merge2 + 1;
        }


        merge = 1;
        merge2 = 0;
        for (String s : mapMapSUP.keySet()) {
            for (String s1 : mapMapSUP.get(s).keySet()) {
                for (String s2 : mapMapSUP.get(s).get(s1).keySet()) {
                    merge2 += mapMapSUP.get(s).get(s1).get(s2).size();
                }
                poiWord.mergeCellsVertically(infoTable5, 2, merge, merge2);
                merge = merge2 + 1;
            }
        }

        merge = 1;
        for (String s : mapMapSUP.keySet()) {
            for (String s1 : mapMapSUP.get(s).keySet()) {
                for (String s2 : mapMapSUP.get(s).get(s1).keySet()) {
                    poiWord.mergeCellsVertically(infoTable5, 3, merge, merge + mapMapSUP.get(s).get(s1).get(s2).size() - 1);
                    merge += mapMapSUP.get(s).get(s1).get(s2).size();
                }
                ;
            }
        }


        List<WordCheckResults> wordCheckResults4 = new ArrayList<>();
        for (String s : mapMapSUP.keySet()) {
            for (String s1 : mapMapSUP.get(s).keySet()) {
                for (String s2 : mapMapSUP.get(s).get(s1).keySet()) {
                    for (WordCheckResults s3 : mapMapSUP.get(s).get(s1).get(s2)) {
//                        if (s3.getPath() != null) {
//                            wordCheckResults4.add(s3);
//                        }
                        for (Attachment attachment : s3.getPathName()) {
                            WordCheckResults model = new WordCheckResults();
                            BeanUtils.copyProperties(s3, model);
                            model.setPath(attachment.getPath());
                            model.setPhotoName(attachment.getName());
                            model.setNumber(attachment.getType());
                            wordCheckResults4.add(model);

                        }
                    }
                }
            }
        }

        poiWord.myCheckImg(wordCheckResults4, wordBasicData, document);

        poiWord.myCreateParagraph(document, "Heading2", ParagraphAlignment.LEFT, "2.3 下部结构缺损状况", true, "000000", "楷体_GB2312", 12, false, 0, 120);
        poiWord.myCreateParagraph(document, "", ParagraphAlignment.LEFT, "本次检测中所发现的下部结构病害位置如图2-" + number + "所示，缺损状况检查结果详见表 2-3；", false, "000000", "楷体_GB2312", 12, false, 0, 0);
        for (PhotoUrl photoUrl : wordBasicData.getTotalSUB()) {
            String[] str1 = photoUrl.getPath().split(wordBasicData.getContext() + "/static/");
            String filePath = wordBasicData.getFile().replace("//", "/");
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
            poiWord.myCreateImg(document, ParagraphAlignment.CENTER, resultPath, type, photoUrl.getName(), 430, 280);

        }
        poiWord.myCreateParagraph(document, "", ParagraphAlignment.CENTER, "图2-" + number++ + " 下部结构病害位置图（单位：m）", false, "000000", "楷体_GB2312", 11, false, 0, 0);
        document.createParagraph().createRun().addBreak();//添加一个回车空行
        poiWord.myCreateParagraph(document, "", ParagraphAlignment.CENTER, "表 2-3 下部结构检查结果汇总表", true, "000000", "楷体_GB2312", 11, false, 0, 0);


        Map<String, Map<String, Map<String, List<WordCheckResults>>>> mapMapSUB = poiWord.checkMapDataSUB(wordBasicData);


        XWPFTable infoTable6 = document.createTable(wordBasicData.getSUB().size() + 1, 7);
        infoTable6.getCTTbl().addNewTblPr().addNewJc().setVal(STJc.CENTER);
        CTTblBorders borders6 = infoTable6.getCTTbl().getTblPr().addNewTblBorders();
        borders6.addNewLeft().setSz(new BigInteger("15"));
        borders6.addNewRight().setSz(new BigInteger("15"));
        borders6.addNewTop().setSz(new BigInteger("15"));
        borders6.addNewBottom().setSz(new BigInteger("15"));


        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        wordData = new WordCellData();
        wordData.setName("序号");
        wordData.setValue(800);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(400);

        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("位置");
        wordData.setValue(1000);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(400);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("构件类型");
        wordData.setValue(1400);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(400);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("缺损类型");
        wordData.setValue(1200);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(400);
        list2.add(wordData);


        wordData = new WordCellData();
        wordData.setName("缺损状况描述");
        wordData.setValue(3000);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(400);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("位置图编号");
        wordData.setValue(1500);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(400);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("图示编号");
        wordData.setValue(1500);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(400);
        list2.add(wordData);
        list1.add(list2);


        id = 1;
        for (String s : mapMapSUB.keySet()) {
            for (String s1 : mapMapSUB.get(s).keySet()) {
                for (String s2 : mapMapSUB.get(s).get(s1).keySet()) {
                    for (int i = 0; i < mapMapSUB.get(s).get(s1).get(s2).size(); i++) {
                        list2 = new ArrayList<>();
                        WordCheckResults wordCheckResults3 = mapMapSUB.get(s).get(s1).get(s2).get(i);
                        for (int j = 0; j < 7; j++) {
                            wordData = new WordCellData();
                            if (j == 0) {
                                wordData.setName("" + id);
                                wordData.setValue(800);
                            } else if (j == 1) {
                                wordData.setName("" + wordCheckResults3.getSpanCode() + "#跨");
                                wordData.setValue(1000);
                            } else if (j == 2) {
                                wordData.setName("" + wordCheckResults3.getName());
                                wordData.setValue(1400);
                            } else if (j == 3) {
                                wordData.setName("" + wordCheckResults3.getDiseaseName());
                                wordData.setValue(1200);
                            } else if (j == 4) {
                                wordData.setName("" + wordCheckResults3.getSpanCode() + "#跨" + wordCheckResults3.getName() + wordCheckResults3.getDegree() + wordCheckResults3.getDiseaseName() + wordCheckResults3.getRemark());
                                wordData.setValue(3000);
                            } else if (j == 5) {
                                if (wordCheckResults3.getCode() != null) {
                                    wordData.setName("" + wordCheckResults3.getCode() + "-" + wordCheckResults3.getSort());
                                } else {
                                    wordData.setName("");
                                }
                                wordData.setValue(1500);
                            } else if (j == 6) {
//                                if (wordCheckResults3.getPath() != null) {
//                                    wordData.setName("图 2-" + number);
//                                    wordCheckResults3.setNumber(number);
//                                    number++;
//                                } else {
//                                    wordData.setName("");
//                                }
                                if (wordCheckResults3.getPathName().size() != 0) {
                                    for (Attachment attachment : wordCheckResults3.getPathName()) {
                                        if (wordData.getName() != null) {
                                            wordData.setName(wordData.getName() + "图 2-" + number + "      ");
                                        } else {
                                            wordData.setName("图 2-" + number + "      ");
                                        }
                                        attachment.setType(number);
                                        number++;
                                    }
                                } else {
                                    wordData.setName("");
                                }
                                wordData.setValue(1500);
                            }
                            wordData.setAlignment(1);
                            wordData.setFontFamily("楷体_GB2312");
                            wordData.setFontSize(11);
                            wordData.setHeight(400);
                            list2.add(wordData);
                        }
                        id++;
                        list1.add(list2);
                    }
                }
            }
        }
        poiWord.tableData(infoTable6, list1);


        merge = 1;
        merge2 = 0;
        for (String s : mapMapSUB.keySet()) {
            for (String s1 : mapMapSUB.get(s).keySet()) {
                for (String s2 : mapMapSUB.get(s).get(s1).keySet()) {
                    merge2 += mapMapSUB.get(s).get(s1).get(s2).size();
                }
            }
            poiWord.mergeCellsVertically(infoTable6, 1, merge, merge2);
            merge = merge2 + 1;
        }


        merge = 1;
        merge2 = 0;
        for (String s : mapMapSUB.keySet()) {
            for (String s1 : mapMapSUB.get(s).keySet()) {
                for (String s2 : mapMapSUB.get(s).get(s1).keySet()) {
                    merge2 += mapMapSUB.get(s).get(s1).get(s2).size();
                }
                poiWord.mergeCellsVertically(infoTable6, 2, merge, merge2);
                merge = merge2 + 1;
            }
        }

        merge = 1;
        for (String s : mapMapSUB.keySet()) {
            for (String s1 : mapMapSUB.get(s).keySet()) {
                for (String s2 : mapMapSUB.get(s).get(s1).keySet()) {
                    poiWord.mergeCellsVertically(infoTable6, 3, merge, merge + mapMapSUB.get(s).get(s1).get(s2).size() - 1);
                    merge += mapMapSUB.get(s).get(s1).get(s2).size();
                }
                ;
            }
        }


        List<WordCheckResults> wordCheckResults5 = new ArrayList<>();
        for (String s : mapMapSUB.keySet()) {
            for (String s1 : mapMapSUB.get(s).keySet()) {
                for (String s2 : mapMapSUB.get(s).get(s1).keySet()) {
                    for (WordCheckResults s3 : mapMapSUB.get(s).get(s1).get(s2)) {
//                        if (s3.getPath() != null) {
//                            wordCheckResults5.add(s3);
//                        }
                        for (Attachment attachment : s3.getPathName()) {
                            WordCheckResults model = new WordCheckResults();
                            BeanUtils.copyProperties(s3, model);
                            model.setPath(attachment.getPath());
                            model.setPhotoName(attachment.getName());
                            model.setNumber(attachment.getType());
                            wordCheckResults5.add(model);

                        }
                    }
                }
            }
        }
        document.createParagraph().createRun().addBreak();//添加一个回车空行

        poiWord.myCheckImg(wordCheckResults5, wordBasicData, document);


        poiWord.myCreateParagraph(document, "Heading1", ParagraphAlignment.CENTER, "第 3 章 桥梁技术状况评估", true, "000000", "楷体_GB2312", 12, true, 0, 120);
        poiWord.myCreateParagraph(document, "Heading2", ParagraphAlignment.LEFT, "3.1 桥梁技术状况评估方法", true, "000000", "楷体_GB2312", 12, false, 0, 120);
        poiWord.myCreateParagraph(document, "", ParagraphAlignment.LEFT, "根据《城市桥梁养护技术规范》（CJJ99-2003）的相关规定，Ⅱ～Ⅴ类养护的城市桥梁的完好程度，应以桥梁状况指数BCI确定桥梁技术状况的评估指标。按分层加权法根据定期检查的桥梁技术状况记录，对桥面系、上部结构和下部结构分别进行评估，再综合得出整个桥梁技术状况的评估。", false, "000000", "楷体_GB2312", 12, false, 480, 0);

        poiWord.myCreateParagraph(document, "Heading2", ParagraphAlignment.LEFT, "3.2 桥梁技术状况评估结果", true, "000000", "楷体_GB2312", 12, false, 0, 120);
        poiWord.myCreateParagraph(document, "", ParagraphAlignment.LEFT, "该桥梁为城市次干路上的桥梁，属" + wordBasicData.getBridgeLevel() + "的城市桥梁，其桥梁的技术状况评估结果详见表3-1～表3-4。根据评估结果，整个桥梁的技术状况指数BCI为" + wordBasicData.getBciScore() + "，整个桥梁完好状态等级评估为" + wordBasicData.getBciLevel() + "级。", false, "000000", "楷体_GB2312", 12, false, 480, 0);
        poiWord.myCreateParagraph(document, "", ParagraphAlignment.CENTER, "表 3-1 桥面系完好状况评估结果", true, "000000", "楷体_GB2312", 11, false, 0, 0);

        XWPFTable infoTable7 = document.createTable(wordBasicData.getBDEvaluateResults().size() + 1, 5);
        infoTable7.getCTTbl().addNewTblPr().addNewJc().setVal(STJc.CENTER);
        CTTblBorders borders7 = infoTable7.getCTTbl().getTblPr().addNewTblBorders();
        borders7.addNewLeft().setSz(new BigInteger("15"));
        borders7.addNewRight().setSz(new BigInteger("15"));
        borders7.addNewTop().setSz(new BigInteger("15"));
        borders7.addNewBottom().setSz(new BigInteger("15"));


        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        wordData = new WordCellData();
        wordData.setName("评估    部位");
        wordData.setValue(1000);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("评估要素");
        wordData.setValue(2500);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("权重");
        wordData.setValue(1200);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("综合扣分值");
        wordData.setValue(1500);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(800);
        list2.add(wordData);


        wordData = new WordCellData();
        wordData.setName("技术状况指数(BCIm)");
        wordData.setValue(2300);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(800);
        list2.add(wordData);

        list1.add(list2);

        float bDBcim = 0;
        for (int i = 0; i < wordBasicData.getBDEvaluateResults().size(); i++) {
            WordEvaluateResults wordEvaluateResults = wordBasicData.getBDEvaluateResults().get(i);
            bDBcim += wordEvaluateResults.getActualWeight() * wordEvaluateResults.getDeduct();
        }
        bDBcim = 100 - bDBcim;

        bDBcim = Float.parseFloat(df.format(bDBcim));

        for (int i = 0; i < wordBasicData.getBDEvaluateResults().size(); i++) {
            list2 = new ArrayList<>();
            WordEvaluateResults wordEvaluateResults = wordBasicData.getBDEvaluateResults().get(i);
            for (int j = 0; j < 5; j++) {
                wordData = new WordCellData();
                if (j == 0) {
                    wordData.setName("桥面系");
                    wordData.setValue(1000);
                } else if (j == 3) {
                    wordData.setName("" + Float.parseFloat(df.format(wordEvaluateResults.getDeduct())));
                    wordData.setValue(1500);
                } else if (j == 1) {
                    wordData.setName(wordEvaluateResults.getName());
                    wordData.setValue(2500);
                } else if (j == 4) {
                    wordData.setName("" + bDBcim);
                    wordData.setValue(2300);
                } else if (j == 2) {
                    wordData.setName("" + wordEvaluateResults.getActualWeight());
                    wordData.setValue(1200);
                }
                wordData.setAlignment(1);
                wordData.setFontFamily("楷体_GB2312");
                wordData.setFontSize(11);
                wordData.setHeight(600);
                list2.add(wordData);
            }
            list1.add(list2);
        }

        poiWord.tableData(infoTable7, list1);
        poiWord.mergeCellsVertically(infoTable7, 0, 1, wordBasicData.getBDEvaluateResults().size());
        poiWord.mergeCellsVertically(infoTable7, 4, 1, wordBasicData.getBDEvaluateResults().size());


        poiWord.myCreateParagraph(document, "", ParagraphAlignment.CENTER, "表3-2 上部结构完好状况评估结果", true, "000000", "楷体_GB2312", 11, false, 0, 0);


        Map<String, List<WordEvaluateResults>> BCIsmap = poiWord.BCIs(wordBasicData);

        Map<String, Float> BCIsScore = new HashMap<>();
        for (String integer : BCIsmap.keySet()) {
            float score = 0;
            for (WordEvaluateResults wordEvaluateResults : BCIsmap.get(integer)) {
                score += wordEvaluateResults.getActualWeight() * wordEvaluateResults.getDeduct();
            }
            score = 100 - score;
            BCIsScore.put(integer, score);
        }

        float totalBCIs = 0;
        for (String integer : BCIsScore.keySet()) {
            totalBCIs += BCIsScore.get(integer);
        }
        if (BCIsScore.size() == 0) {
            totalBCIs = 100;
        } else {
            totalBCIs = totalBCIs / BCIsScore.size();
        }
        totalBCIs = Float.parseFloat(df.format(totalBCIs));

        XWPFTable infoTable8 = document.createTable(wordBasicData.getSUPEvaluateResults().size() + 1, 7);
        infoTable8.getCTTbl().addNewTblPr().addNewJc().setVal(STJc.CENTER);
        CTTblBorders borders8 = infoTable8.getCTTbl().getTblPr().addNewTblBorders();
        borders8.addNewLeft().setSz(new BigInteger("15"));
        borders8.addNewRight().setSz(new BigInteger("15"));
        borders8.addNewTop().setSz(new BigInteger("15"));
        borders8.addNewBottom().setSz(new BigInteger("15"));


        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        wordData = new WordCellData();
        wordData.setName("评估    部位");
        wordData.setValue(1000);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("构件类型");
        wordData.setValue(2500);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("");
        wordData.setValue(1);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("权重");
        wordData.setValue(1200);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("综合扣分值");
        wordData.setValue(1500);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(800);
        list2.add(wordData);


        wordData = new WordCellData();
        wordData.setName("技术状况指数(BCIs)");
        wordData.setValue(2300);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("");
        wordData.setValue(1);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(800);
        list2.add(wordData);

        list1.add(list2);


        for (int i = 0; i < wordBasicData.getSUPEvaluateResults().size(); i++) {
            list2 = new ArrayList<>();
            WordEvaluateResults wordEvaluateResults = wordBasicData.getSUPEvaluateResults().get(i);
            for (int j = 0; j < 7; j++) {
                wordData = new WordCellData();
                if (j == 0) {
                    wordData.setName("上部结构");
                    wordData.setValue(1000);
                } else if (j == 1) {
                    wordData.setName("" + wordEvaluateResults.getSpanCode());
                    wordData.setValue(1000);
                } else if (j == 2) {
                    wordData.setName(wordEvaluateResults.getName());
                    wordData.setValue(1500);
                } else if (j == 4) {
                    wordData.setName("" + Float.parseFloat(df.format(wordEvaluateResults.getDeduct())));
                    wordData.setValue(1500);
                } else if (j == 5) {
                    wordData.setName("" + df.format(BCIsScore.get(wordEvaluateResults.getSpanCode())));
                    wordData.setValue(1150);
                } else if (j == 3) {
                    wordData.setName("" + wordEvaluateResults.getActualWeight());
                    wordData.setValue(1200);
                } else if (j == 6) {
                    wordData.setName("" + totalBCIs);
                    wordData.setValue(1150);
                }
                wordData.setAlignment(1);
                wordData.setFontFamily("楷体_GB2312");
                wordData.setFontSize(11);
                wordData.setHeight(600);
                list2.add(wordData);
            }
            list1.add(list2);
        }
        poiWord.tableData(infoTable8, list1);

        poiWord.mergeCellsHorizontal(infoTable8, 0, 5, 6);
        poiWord.mergeCellsHorizontal(infoTable8, 0, 1, 2);

        poiWord.mergeCellsVertically(infoTable8, 0, 1, wordBasicData.getSUPEvaluateResults().size());
        poiWord.mergeCellsVertically(infoTable8, 6, 1, wordBasicData.getSUPEvaluateResults().size());
        merge = 1;
        for (String integer : BCIsmap.keySet()) {
            poiWord.mergeCellsVertically(infoTable8, 1, merge, merge + BCIsmap.get(integer).size() - 1);
            poiWord.mergeCellsVertically(infoTable8, 5, merge, merge + BCIsmap.get(integer).size() - 1);
            merge += BCIsmap.get(integer).size();
        }


        poiWord.myCreateParagraph(document, "", ParagraphAlignment.CENTER, "表3-3 下部结构完好状况评估结果", true, "000000", "楷体_GB2312", 11, false, 0, 0);

        Map<String, List<WordEvaluateResults>> BCIxmap = poiWord.BCIx(wordBasicData);

        Map<String, Float> BCIxScore = new HashMap<>();
        for (String integer : BCIxmap.keySet()) {
            float score = 0;
            for (WordEvaluateResults wordEvaluateResults : BCIxmap.get(integer)) {
                score += wordEvaluateResults.getActualWeight() * wordEvaluateResults.getDeduct();
            }
            score = 100 - score;
            BCIxScore.put(integer, score);
        }

        float totalBCIx = 0;
        for (String integer : BCIxScore.keySet()) {
            totalBCIx += BCIxScore.get(integer);
        }
        if (BCIxScore.size() == 0) {
            totalBCIx = 100;
        } else {
            totalBCIx = totalBCIx / BCIxScore.size();
        }

        totalBCIx = Float.parseFloat(df.format(totalBCIx));

        XWPFTable infoTable9 = document.createTable(wordBasicData.getSUBEvaluateResults().size() + 1, 7);
        infoTable9.getCTTbl().addNewTblPr().addNewJc().setVal(STJc.CENTER);
        CTTblBorders borders9 = infoTable9.getCTTbl().getTblPr().addNewTblBorders();
        borders9.addNewLeft().setSz(new BigInteger("15"));
        borders9.addNewRight().setSz(new BigInteger("15"));
        borders9.addNewTop().setSz(new BigInteger("15"));
        borders9.addNewBottom().setSz(new BigInteger("15"));


        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        wordData = new WordCellData();
        wordData.setName("评估    部位");
        wordData.setValue(1000);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("构件类型");
        wordData.setValue(2500);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("");
        wordData.setValue(1);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("权重");
        wordData.setValue(1200);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("综合扣分值");
        wordData.setValue(1500);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(800);
        list2.add(wordData);


        wordData = new WordCellData();
        wordData.setName("技术状况指数(BCIx)");
        wordData.setValue(2300);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("");
        wordData.setValue(1);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(800);
        list2.add(wordData);

        list1.add(list2);


        for (int i = 0; i < wordBasicData.getSUBEvaluateResults().size(); i++) {
            list2 = new ArrayList<>();
            WordEvaluateResults wordEvaluateResults = wordBasicData.getSUBEvaluateResults().get(i);
            for (int j = 0; j < 7; j++) {
                wordData = new WordCellData();
                if (j == 0) {
                    wordData.setName("下部结构");
                    wordData.setValue(1000);
                } else if (j == 1) {
                    wordData.setName("" + wordEvaluateResults.getSpanCode());
                    wordData.setValue(1000);
                } else if (j == 2) {
                    wordData.setName(wordEvaluateResults.getName());
                    wordData.setValue(1500);
                } else if (j == 4) {
                    wordData.setName("" + Float.parseFloat(df.format(wordEvaluateResults.getDeduct())));
                    wordData.setValue(1500);
                } else if (j == 5) {
                    wordData.setName("" + df.format(BCIxScore.get(wordEvaluateResults.getSpanCode())));
                    wordData.setValue(1150);
                } else if (j == 3) {
                    wordData.setName("" + wordEvaluateResults.getActualWeight());
                    wordData.setValue(1200);
                } else if (j == 6) {
                    wordData.setName("" + totalBCIx);
                    wordData.setValue(1150);
                }
                wordData.setAlignment(1);
                wordData.setFontFamily("楷体_GB2312");
                wordData.setFontSize(11);
                wordData.setHeight(600);
                list2.add(wordData);
            }
            list1.add(list2);
        }
        poiWord.tableData(infoTable9, list1);

        poiWord.mergeCellsHorizontal(infoTable9, 0, 5, 6);
        poiWord.mergeCellsHorizontal(infoTable9, 0, 1, 2);

        poiWord.mergeCellsVertically(infoTable9, 0, 1, wordBasicData.getSUBEvaluateResults().size());
        poiWord.mergeCellsVertically(infoTable9, 6, 1, wordBasicData.getSUBEvaluateResults().size());
        merge = 1;
        for (String integer : BCIxmap.keySet()) {
            poiWord.mergeCellsVertically(infoTable9, 1, merge, merge + BCIxmap.get(integer).size() - 1);
            poiWord.mergeCellsVertically(infoTable9, 5, merge, merge + BCIxmap.get(integer).size() - 1);
            merge += BCIxmap.get(integer).size();
        }

        poiWord.myCreateParagraph(document, "", ParagraphAlignment.CENTER, "表3-4 整个桥梁完好状况评估结果", true, "000000", "楷体_GB2312", 11, false, 0, 0);
        XWPFTable infoTable10 = document.createTable(4, 5);
        infoTable10.getCTTbl().addNewTblPr().addNewJc().setVal(STJc.CENTER);
        CTTblBorders borders10 = infoTable10.getCTTbl().getTblPr().addNewTblBorders();
        borders10.addNewLeft().setSz(new BigInteger("15"));
        borders10.addNewRight().setSz(new BigInteger("15"));
        borders10.addNewTop().setSz(new BigInteger("15"));
        borders10.addNewBottom().setSz(new BigInteger("15"));


        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        wordData = new WordCellData();
        wordData.setName("桥梁部位");
        wordData.setValue(1700);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("权重");
        wordData.setValue(1700);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("各部位技术状况指数");
        wordData.setValue(1700);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("整个桥梁技术状况指数BCI");
        wordData.setValue(1700);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(800);
        list2.add(wordData);


        wordData = new WordCellData();
        wordData.setName("评估等级");
        wordData.setValue(1700);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(1);
        wordData.setHeight(800);
        list2.add(wordData);

        list1.add(list2);


        list2 = new ArrayList<>();
        wordData = new WordCellData();
        wordData.setName("桥面系");
        wordData.setValue(1700);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(0);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("" + wordBasicData.getBDWeight());
        wordData.setValue(1700);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(0);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("" + bDBcim);
        wordData.setValue(1700);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(0);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName(wordBasicData.getBciScore());
        wordData.setValue(1700);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(0);
        wordData.setHeight(800);
        list2.add(wordData);


        wordData = new WordCellData();
        wordData.setName(wordBasicData.getBciLevel());
        wordData.setValue(1700);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(0);
        wordData.setHeight(800);
        list2.add(wordData);

        list1.add(list2);


        list2 = new ArrayList<>();
        wordData = new WordCellData();
        wordData.setName("上部结构");
        wordData.setValue(1700);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(0);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("" + wordBasicData.getSUPWeight());
        wordData.setValue(1700);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(0);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("" + totalBCIs);
        wordData.setValue(1700);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(0);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName(wordBasicData.getBciScore());
        wordData.setValue(1700);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(0);
        wordData.setHeight(800);
        list2.add(wordData);


        wordData = new WordCellData();
        wordData.setName(wordBasicData.getBciLevel());
        wordData.setValue(1700);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(0);
        wordData.setHeight(800);
        list2.add(wordData);

        list1.add(list2);

        list2 = new ArrayList<>();
        wordData = new WordCellData();
        wordData.setName("下部结构");
        wordData.setValue(1700);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(0);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("" + wordBasicData.getSUBWeight());
        wordData.setValue(1700);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(0);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName("" + totalBCIx);
        wordData.setValue(1700);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(0);
        wordData.setHeight(800);
        list2.add(wordData);

        wordData = new WordCellData();
        wordData.setName(wordBasicData.getBciScore());
        wordData.setValue(1700);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(0);
        wordData.setHeight(800);
        list2.add(wordData);


        wordData = new WordCellData();
        wordData.setName(wordBasicData.getBciLevel());
        wordData.setValue(1700);
        wordData.setAlignment(1);
        wordData.setFontFamily("楷体_GB2312");
        wordData.setFontSize(11);
        wordData.setBold(0);
        wordData.setHeight(800);
        list2.add(wordData);

        list1.add(list2);


        poiWord.tableData(infoTable10, list1);
        poiWord.mergeCellsVertically(infoTable10, 3, 1, 3);
        poiWord.mergeCellsVertically(infoTable10, 4, 1, 3);


        poiWord.myCreateParagraph(document, "Heading1", ParagraphAlignment.CENTER, "第 4 章 结论与建议", true, "000000", "楷体_GB2312", 12, true, 0, 120);
        poiWord.myCreateParagraph(document, "Heading2", ParagraphAlignment.LEFT, "4.1 桥梁缺损状况检查结果", true, "000000", "楷体_GB2312", 12, false, 0, 120);
        document.createParagraph().createRun().addBreak();
        poiWord.myCreateParagraph(document, "Heading2", ParagraphAlignment.LEFT, "4.2 桥梁技术状况评估结论", true, "000000", "楷体_GB2312", 12, false, 0, 120);
        document.createParagraph().createRun().addBreak();
        poiWord.myCreateParagraph(document, "Heading2", ParagraphAlignment.LEFT, "4.3 建议", true, "000000", "楷体_GB2312", 12, false, 0, 120);
        document.createParagraph().createRun().addBreak();

        poiWord.createDefaultFooter(document);

        customTOC.createCustomTOC(document);
        customTOC.addItem2TOC(document);


        document.write(out);
        out.close();

        return "/testReport/" + roadId + "/" + uuid + "/" + fileName + ".doc";
    }


    /**
     * 表格数据处理
     *
     * @param infoTable 表格对象
     * @param lists     数据集合
     */
    public void tableData(XWPFTable infoTable, List<List<WordCellData>> lists) {
        TestReportUtils poiWord = new TestReportUtils();
        for (int i = 0; i < lists.size(); i++) {
            List<WordCellData> strList = lists.get(i);
            XWPFTableRow infoTableRow = infoTable.getRow(i);
            infoTableRow.getCtRow().addNewTrPr().addNewTrHeight().setVal(BigInteger.valueOf(lists.get(i).get(0).getHeight()));
            for (int j = 0; j < strList.size(); j++) {
                poiWord.tableStyle(infoTableRow.getCell(j), strList.get(j));
            }
        }
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
//        if (wordData.getAlignment() == 1) {
//            //水平居中
//            cell.getCTTc().getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
//        } else {
//            cell.getCTTc().getPList().get(0).addNewPPr().addNewJc().setVal(STJc.LEFT);
//        }
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
            p.setAlignment(ParagraphAlignment.CENTER);
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

    /**
     * 行合并
     *
     * @param table    表格对象
     * @param col      列
     * @param startRow 开始行
     * @param endRow   结束行
     */
    public void mergeCellsVertically(XWPFTable table, int col, int startRow, int endRow) {
        for (int i = startRow; i <= endRow; i++) {
            XWPFTableCell cell = table.getRow(i).getCell(col);
            if (i == startRow) {
                // The first merged cell is set with RESTART merge value
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            }
        }
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

    /**
     * 设置行间距
     *
     * @param para 段落对象
     */
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
     * 创建段落
     *
     * @param document      word对象
     * @param styleText     标题名称
     * @param alignment     对齐方式
     * @param mainText      正文内容
     * @param bold          加粗
     * @param color         颜色
     * @param family        字体
     * @param fontSize      字号
     * @param pageBreak     段前分页
     * @param firstLine     首行缩进
     * @param spacingBefore 段前间距
     */
    public void myCreateParagraph(XWPFDocument document, String styleText, ParagraphAlignment alignment, String mainText, boolean bold,
                                  String color, String family, int fontSize, boolean pageBreak, int firstLine, int spacingBefore) {
        TestReportUtils poiWord = new TestReportUtils();
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
        poiWord.setSingleLineSpacing(para);
        //段前分页
        para.setPageBreak(pageBreak);
        //首行缩进
        para.setIndentationFirstLine(firstLine);
        //标题对应
        para.setStyle(styleText);
        //段前间距
        para.setSpacingBefore(spacingBefore);

    }

    /**
     * 插入图片
     *
     * @param document   word对象
     * @param alignment  对齐方式
     * @param filePath   图片路径
     * @param fileFormat 图片格式
     * @param fileName   图片名称
     * @param width      宽度
     * @param height     高度
     * @throws IOException
     * @throws InvalidFormatException
     */
    public void myCreateImg(XWPFDocument document, ParagraphAlignment alignment, String filePath, int fileFormat, String fileName, double width, double height) throws IOException, InvalidFormatException {
        XWPFParagraph para = document.createParagraph();
        para.setAlignment(alignment);
        XWPFRun run = para.createRun();
        File picture = new File(filePath);
        BufferedImage sourceImg = ImageIO.read(new FileInputStream(picture));
        width = sourceImg.getWidth();
        height = sourceImg.getHeight();
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
        run.addPicture(new FileInputStream(filePath), fileFormat, fileName, Units.toEMU(width), Units.toEMU(height));
        para.setSpacingBefore(50);
    }


    public void createFooter(XWPFDocument doc) throws IOException {

        // create footer
        XWPFHeaderFooterPolicy policy = doc.getHeaderFooterPolicy();
        CTP ctpFooter = CTP.Factory.newInstance();

        XWPFParagraph[] parsFooter;

        // add style (s.th.)
        CTPPr ctppr = ctpFooter.addNewPPr();
        CTString pst = ctppr.addNewPStyle();
        pst.setVal("style21");
        CTJc ctjc = ctppr.addNewJc();
        ctjc.setVal(STJc.CENTER);
        ctppr.addNewRPr();

        // add everything from the footerXXX.xml you need
        CTR ctr = ctpFooter.addNewR();
        ctr.addNewRPr();
        CTFldChar fch = ctr.addNewFldChar();
        fch.setFldCharType(STFldCharType.BEGIN);

        ctr = ctpFooter.addNewR();
        ctr.addNewInstrText().setStringValue(" PAGE ");

        ctpFooter.addNewR().addNewFldChar().setFldCharType(STFldCharType.SEPARATE);

        ctpFooter.addNewR().addNewT().setStringValue("1");
        ctpFooter.addNewR().addNewFldChar().setFldCharType(STFldCharType.END);

        XWPFParagraph footerParagraph = new XWPFParagraph(ctpFooter, doc);

        parsFooter = new XWPFParagraph[1];

        parsFooter[0] = footerParagraph;

        policy.createFooter(XWPFHeaderFooterPolicy.DEFAULT, parsFooter);
    }

    /**
     * 创建页脚（页码）
     *
     * @param document
     * @throws IOException
     * @throws XmlException
     */
    public void createDefaultFooter(final XWPFDocument document) throws IOException, XmlException {
        CTP pageNo = CTP.Factory.newInstance();
        XWPFParagraph footer = new XWPFParagraph(pageNo, document);


        CTPPr begin = pageNo.addNewPPr();
        begin.addNewPStyle().setVal("style21");
        begin.addNewJc().setVal(STJc.CENTER);

        pageNo.addNewR().addNewFldChar().setFldCharType(STFldCharType.BEGIN);
        pageNo.addNewR().addNewInstrText().setStringValue("PAGE   \\* MERGEFORMAT");
        pageNo.addNewR().addNewFldChar().setFldCharType(STFldCharType.SEPARATE);

        CTR end = pageNo.addNewR();
        CTRPr endRPr = end.addNewRPr();
        endRPr.addNewNoProof();
        endRPr.addNewLang().setVal("zh-CN");
        end.addNewFldChar().setFldCharType(STFldCharType.END);

        CTSectPr sectPr = document.getDocument().getBody().isSetSectPr() ? document.getDocument().getBody().getSectPr() : document.getDocument().getBody().addNewSectPr();

        XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(document, sectPr);
        policy.createFooter(STHdrFtr.DEFAULT, new XWPFParagraph[]{footer});


    }

    /**
     * 桥面系缺损数据分层
     *
     * @param wordBasicData
     * @return
     */
    public Map<String, Map<String, List<WordCheckResults>>> checkMapDataBD(WordBasicData wordBasicData) {
        Map<String, Map<String, List<WordCheckResults>>> mapMap = new HashMap<>();
        for (WordCheckResults s : wordBasicData.getBD()) {
            mapMap.put(s.getName(), new HashMap<String, List<WordCheckResults>>());
        }

        for (WordCheckResults ss : wordBasicData.getBD()) {
            mapMap.get(ss.getName()).put(ss.getDiseaseName(), new ArrayList<WordCheckResults>());

        }

        for (WordCheckResults ss : wordBasicData.getBD()) {
            mapMap.get(ss.getName()).get(ss.getDiseaseName()).add(ss);
        }
        return mapMap;
    }

    /**
     * 上部结构缺损分层
     *
     * @param wordBasicData
     * @return
     */
    public Map<String, Map<String, Map<String, List<WordCheckResults>>>> checkMapDataSUP(WordBasicData wordBasicData) {
        Map<String, Map<String, Map<String, List<WordCheckResults>>>> mapMap = new HashMap<>();
        for (WordCheckResults s : wordBasicData.getSUP()) {
            mapMap.put(s.getSpanCode(), new HashMap<>());
        }

        for (WordCheckResults ss : wordBasicData.getSUP()) {
            mapMap.get(ss.getSpanCode()).put(ss.getName(), new HashMap<>());

        }

        for (WordCheckResults ss : wordBasicData.getSUP()) {
            mapMap.get(ss.getSpanCode()).get(ss.getName()).put(ss.getDiseaseName(), new ArrayList<>());
        }

        for (WordCheckResults ss : wordBasicData.getSUP()) {
            mapMap.get(ss.getSpanCode()).get(ss.getName()).get(ss.getDiseaseName()).add(ss);
        }
        return mapMap;
    }

    /**
     * 下部结构缺损分层
     *
     * @param wordBasicData
     * @return
     */
    public Map<String, Map<String, Map<String, List<WordCheckResults>>>> checkMapDataSUB(WordBasicData wordBasicData) {
        Map<String, Map<String, Map<String, List<WordCheckResults>>>> mapMap = new HashMap<>();
        for (WordCheckResults s : wordBasicData.getSUB()) {
            mapMap.put(s.getSpanCode(), new HashMap<>());
        }

        for (WordCheckResults ss : wordBasicData.getSUB()) {
            mapMap.get(ss.getSpanCode()).put(ss.getName(), new HashMap<>());

        }

        for (WordCheckResults ss : wordBasicData.getSUB()) {
            mapMap.get(ss.getSpanCode()).get(ss.getName()).put(ss.getDiseaseName(), new ArrayList<>());
        }

        for (WordCheckResults ss : wordBasicData.getSUB()) {
            mapMap.get(ss.getSpanCode()).get(ss.getName()).get(ss.getDiseaseName()).add(ss);
        }
        return mapMap;
    }

    /**
     * 上部结构完好分层
     *
     * @param wordBasicData
     * @return
     */
    public Map<String, List<WordEvaluateResults>> BCIs(WordBasicData wordBasicData) {
        Map<String, List<WordEvaluateResults>> map = new HashMap<>();
        for (WordEvaluateResults supEvaluateResult : wordBasicData.getSUPEvaluateResults()) {
            map.put(supEvaluateResult.getSpanCode(), new ArrayList<>());
        }

        for (WordEvaluateResults supEvaluateResult : wordBasicData.getSUPEvaluateResults()) {
            map.get(supEvaluateResult.getSpanCode()).add(supEvaluateResult);
        }

        return map;

    }

    /**
     * 下部结构完好分层
     *
     * @param wordBasicData
     * @return
     */
    public Map<String, List<WordEvaluateResults>> BCIx(WordBasicData wordBasicData) {
        Map<String, List<WordEvaluateResults>> map = new HashMap<>();
        for (WordEvaluateResults supEvaluateResult : wordBasicData.getSUBEvaluateResults()) {
            map.put(supEvaluateResult.getSpanCode(), new ArrayList<>());
        }

        for (WordEvaluateResults supEvaluateResult : wordBasicData.getSUBEvaluateResults()) {
            map.get(supEvaluateResult.getSpanCode()).add(supEvaluateResult);
        }

        return map;

    }

    /**
     * 一行添加两张图片
     *
     * @param wordCheckResults
     * @param wordBasicData
     * @param document
     * @throws IOException
     * @throws InvalidFormatException
     */
    public void myCheckImg(List<WordCheckResults> wordCheckResults, WordBasicData wordBasicData, XWPFDocument document) throws IOException, InvalidFormatException {
        TestReportUtils poiWord = new TestReportUtils();
        for (int i = 0; i < wordCheckResults.size(); i = i + 2) {
            XWPFParagraph para = document.createParagraph();
            para.setAlignment(ParagraphAlignment.LEFT);
            para.setIndentationFirstLine(480);
            XWPFRun run = para.createRun();
            para.setSpacingBefore(282);
            String[] str1 = wordCheckResults.get(i).getPath().split(wordBasicData.getContext() + "/static/");
            String filePath = wordBasicData.getFile().replace("//", "/");
            String[] name = wordCheckResults.get(i).getPhotoName().split("\\.");
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
            if (width > 175) {
                Integer ii = (int) Math.floor(width / 175);
                width = width / ii;
                height = height / ii;
            }
            if (height > 250) {
                Integer ii = (int) Math.floor(height / 250);
                width = width / ii;
                height = height / ii;
            }
            run.addPicture(new FileInputStream(resultPath), type, wordCheckResults.get(i).getPhotoName(), Units.toEMU(width), Units.toEMU(height));

            if (i + 2 <= wordCheckResults.size()) {
                run.setText("    ");
                str1 = wordCheckResults.get(i + 1).getPath().split(wordBasicData.getContext() + "/static/");
                filePath = wordBasicData.getFile().replace("//", "/");
                name = wordCheckResults.get(i + 1).getPhotoName().split("\\.");
                type = XWPFDocument.PICTURE_TYPE_JPEG;
                if ("jpg".equals(name[1]) || "jpeg".equals(name[1])) {
                    type = XWPFDocument.PICTURE_TYPE_JPEG;
                } else if ("png".equals(name[1])) {
                    type = XWPFDocument.PICTURE_TYPE_PNG;
                } else if ("bmp".equals(name[1])) {
                    type = XWPFDocument.PICTURE_TYPE_BMP;
                }
                resultPath = filePath + str1[1];
                picture = new File(resultPath);
                sourceImg = ImageIO.read(new FileInputStream(picture));
                width = sourceImg.getWidth();
                height = sourceImg.getHeight();
                if (width > 175) {
                    Integer ii = (int) Math.floor(width / 175);
                    width = width / ii;
                    height = height / ii;
                }
                if (height > 250) {
                    Integer ii = (int) Math.floor(height / 250);
                    width = width / ii;
                    height = height / ii;
                }
                run.addPicture(new FileInputStream(resultPath), type, wordCheckResults.get(i + 1).getPhotoName(), Units.toEMU(width), Units.toEMU(height));
                poiWord.myCreateParagraph(document, "", ParagraphAlignment.BOTH, "图 2-" + wordCheckResults.get(i).getNumber() + "  " + wordCheckResults.get(i).getSpanCode() + "#跨" + wordCheckResults.get(i).getName() + wordCheckResults.get(i).getDegree() +
                        wordCheckResults.get(i).getDiseaseName() + "      图 2-" + wordCheckResults.get(i + 1).getNumber() + "  " + wordCheckResults.get(i + 1).getSpanCode() + "#跨" + wordCheckResults.get(i + 1).getName() + wordCheckResults.get(i + 1).getDegree() +
                        wordCheckResults.get(i + 1).getDiseaseName(), false, "000000", "楷体_GB2312", 11, false, 480, 0);
            } else {
                poiWord.myCreateParagraph(document, "", ParagraphAlignment.BOTH, "图 2-" + wordCheckResults.get(i).getNumber() + "  " + wordCheckResults.get(i).getSpanCode() + "#跨" + wordCheckResults.get(i).getName() + wordCheckResults.get(i).getDegree() +
                        wordCheckResults.get(i).getDiseaseName(), false, "000000", "楷体_GB2312", 11, false, 480, 0);

            }
        }
    }


}
