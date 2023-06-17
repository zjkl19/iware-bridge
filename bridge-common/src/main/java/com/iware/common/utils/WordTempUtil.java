package com.iware.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordTempUtil extends XWPFDocument  {

    private static Logger logger = LoggerFactory.getLogger(WordTempUtil.class);

    /**
     * 替换段落里面的变量
     * @param doc 要替换的文档
     * @param params 参数
     */
    public static void replaceInPara(XWPFDocument doc, Map<String, Object> params)  {
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
        XWPFParagraph para;
        while (iterator.hasNext()) {
            para = iterator.next();
            WordTempUtil.replaceInPara(para, params);
        }
    }

    /**
     * 替换段落里面的变量
     * @param para 要替换的段落
     * @param params 参数
     */
    public static void replaceInPara(XWPFParagraph para, Map<String, Object> params) {
        List<XWPFRun> runs;
        if (WordTempUtil.matcher(para.getParagraphText()).find()) {
            runs = para.getRuns();

            int start = -1;
            int end = -1;
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < runs.size(); i++) {
                XWPFRun run = runs.get(i);
                String runText = run.toString();
                logger.info("------>>>>>>>>>{}", runText);
                if (StringUtils.isNotEmpty(runText)) {
                    if ('$' == runText.charAt(0) && '{' == runText.charAt(1)) {
                        start = i;
                    }
                    if ((start != -1)) {
                        str.append(runText);
                    }
                    if ('}' == runText.charAt(runText.length() - 1) && start != -1) {
                            end = i;
                            break;
                    }
                }
            }
            logger.info("start--->{}", start);
            logger.info("end--->{}", end);

            logger.info("str---->>>{}", str);

            for (int i = start; i <= end; i++) {
                para.removeRun(i);
                i--;
                end--;
            }

            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (str.toString().equals(entry.getKey())) {
                    String value = entry.getValue().toString();
                    List<String> values = Arrays.asList(value.split("\n"));

                    if(!CollectionUtils.isEmpty(values)) {
                        for (Integer i = 0; i < values.size(); i++) {
                            if (i == 0) {
                                XWPFRun run = para.createRun();
                                String stringWord = values.get(i);
                                run.setText(stringWord);
                                run.setFontSize(12);
                                CTFonts fonts = run.getCTR().addNewRPr().addNewRFonts();
                                fonts.setEastAsia("楷体");
                            } else {
                                XWPFRun run = para.createRun();
                                run.addBreak();
                                run.setText("\r\r\r\r"+values.get(i));
                                run.setFontSize(12);
                                CTFonts fonts = run.getCTR().addNewRPr().addNewRFonts();
                                fonts.setEastAsia("楷体");
                            }
                        }
                    }

                    //【字体处理】

                    break;
                }
            }

        }
    }

    /**
     * 替换表格里面的变量
     * @param doc 要替换的文档
     * @param params 参数
     */
    public static void replaceInTable(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFTable> iterator = doc.getTablesIterator();
        XWPFTable table;
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;
        List<XWPFParagraph> paras;
        while (iterator.hasNext()) {
            table = iterator.next();
            rows = table.getRows();
            for (XWPFTableRow row : rows) {
                cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    paras = cell.getParagraphs();
                    for (XWPFParagraph para : paras) {
                        WordTempUtil.replaceInPara(para, params);
                    }
                }
            }
        }
    }

    /**
     * 正则匹配字符串
     * @param str
     * @return
     */
    private static Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}",
                Pattern.CASE_INSENSITIVE);
        return pattern.matcher(str);
    }


//    /**
//     * @param id
//     * @param width 宽
//     * @param height 高
//     * @param paragraph  段落
//     */
//    public void createPicture(int id, int width, int height,XWPFParagraph paragraph) {
//        final int EMU = 9525;
//        width *= EMU;
//        height *= EMU;
//        String blipId = getAllPictures().get(id).getPackageRelationship().getId();
//        CTInline inline = paragraph.createRun().getCTR().addNewDrawing().addNewInline();
//        String picXml = ""
//                +"<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">"
//                +"   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
//                +"      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
//                +"         <pic:nvPicPr>" + "            <pic:cNvPr id=\""
//                + id
//                +"\" name=\"Generated\"/>"
//                +"            <pic:cNvPicPr/>"
//                +"         </pic:nvPicPr>"
//                +"         <pic:blipFill>"
//                +"            <a:blip r:embed=\""
//                + blipId
//                +"\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>"
//                +"            <a:stretch>"
//                +"               <a:fillRect/>"
//                +"            </a:stretch>"
//                +"         </pic:blipFill>"
//                +"         <pic:spPr>"
//                +"            <a:xfrm>"
//                +"               <a:off x=\"0\" y=\"0\"/>"
//                +"               <a:ext cx=\""
//                + width
//                +"\" cy=\""
//                + height
//                +"\"/>"
//                +"            </a:xfrm>"
//                +"            <a:prstGeom prst=\"rect\">"
//                +"               <a:avLst/>"
//                +"            </a:prstGeom>"
//                +"         </pic:spPr>"
//                +"      </pic:pic>"
//                +"   </a:graphicData>" + "</a:graphic>";
//
//        inline.addNewGraphic().addNewGraphicData();
//        XmlToken xmlToken = null;
//        try{
//            xmlToken = XmlToken.Factory.parse(picXml);
//        }catch(XmlException xe) {
//            xe.printStackTrace();
//        }
//        inline.set(xmlToken);
//
//        inline.setDistT(0);
//        inline.setDistB(0);
//        inline.setDistL(0);
//        inline.setDistR(0);
//
//        CTPositiveSize2D extent = inline.addNewExtent();
//        extent.setCx(width);
//        extent.setCy(height);
//
//        CTNonVisualDrawingProps docPr = inline.addNewDocPr();
//        docPr.setId(id);
//        docPr.setName("图片"+ id);
//        docPr.setDescr("测试");
//    }

    /**因POI 3.8自带的BUG 导致添加进的图片不显示，只有一个图片框，将图片另存为发现里面的图片是一个PNG格式的透明图片
     * 这里自定义添加图片的方法
     * 往Run中插入图片(解决在word中不显示的问题)
     * @param run
     * @param blipId      图片的id
     * @param id	      图片的类型
     * @param width       图片的宽
     * @param height      图片的高
     * @author lgj
     */
    public static void addPictureToRun(XWPFRun run,String blipId,int id,int width, int height){
        final int EMU = 9525;
        width *= EMU;
        height *= EMU;

        CTInline inline =run.getCTR().addNewDrawing().addNewInline();

        String picXml = "" +
                "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                "   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">" +
                "      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">" +
                "         <pic:nvPicPr>" +
                "            <pic:cNvPr id=\"" + id + "\" name=\"Generated\"/>" +
                "            <pic:cNvPicPr/>" +
                "         </pic:nvPicPr>" +
                "         <pic:blipFill>" +
                "            <a:blip r:embed=\"" + blipId + "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>" +
                "            <a:stretch>" +
                "               <a:fillRect/>" +
                "            </a:stretch>" +
                "         </pic:blipFill>" +
                "         <pic:spPr>" +
                "            <a:xfrm>" +
                "               <a:off x=\"0\" y=\"0\"/>" +
                "               <a:ext cx=\"" + width + "\" cy=\"" + height + "\"/>" +
                "            </a:xfrm>" +
                "            <a:prstGeom prst=\"rect\">" +
                "               <a:avLst/>" +
                "            </a:prstGeom>" +
                "         </pic:spPr>" +
                "      </pic:pic>" +
                "   </a:graphicData>" +
                "</a:graphic>";

        XmlToken xmlToken = null;
        try {
            xmlToken = XmlToken.Factory.parse(picXml);
        } catch(XmlException xe) {
            logger.error(ExceptionUtils.getErrorStack(xe));
        }
        inline.set(xmlToken);

        inline.setDistT(0);
        inline.setDistB(0);
        inline.setDistL(0);
        inline.setDistR(0);

        CTPositiveSize2D extent = inline.addNewExtent();
        extent.setCx(width);
        extent.setCy(height);

        CTNonVisualDrawingProps docPr = inline.addNewDocPr();
        docPr.setId(id);
        docPr.setName("Picture " + id);
        docPr.setDescr("Generated");
    }

}
