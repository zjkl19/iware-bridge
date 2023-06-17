package com.iware.bridge.evaluation.Utils.model;

import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.math.BigInteger;
import java.util.List;

public class WordCustomTOC {

    private CTSdtBlock block;
    private WordCustomTOC toc;

    public WordCustomTOC(CTSdtBlock block) {
        this.block = block;
    }

    /**
     * 目录
     */
    public void myToc() {
        CTSdtPr sdtPr = block.addNewSdtPr();
        CTDecimalNumber id = sdtPr.addNewId();
        id.setVal(new BigInteger("4844945"));
        sdtPr.addNewDocPartObj().addNewDocPartGallery().setVal("Table of contents");
        CTSdtEndPr sdtEndPr = block.addNewSdtEndPr();
        CTRPr rPr = sdtEndPr.addNewRPr();
        CTFonts fonts = rPr.addNewRFonts();
        fonts.setAsciiTheme(STTheme.MINOR_H_ANSI);
        fonts.setEastAsiaTheme(STTheme.MINOR_H_ANSI);
        fonts.setHAnsiTheme(STTheme.MINOR_H_ANSI);
        fonts.setCstheme(STTheme.MINOR_BIDI);
        rPr.addNewB().setVal(STOnOff.OFF);
        rPr.addNewBCs().setVal(STOnOff.OFF);
        rPr.addNewColor().setVal("auto");
        rPr.addNewSz().setVal(new BigInteger("24"));
        rPr.addNewSzCs().setVal(new BigInteger("24"));
        CTSdtContentBlock content = block.addNewSdtContent();
        CTP p = content.addNewP();
//        p.setRsidR("00EF7E24".getBytes(LocaleUtil.CHARSET_1252));
//        p.setRsidRDefault("00EF7E24".getBytes(LocaleUtil.CHARSET_1252));
        p.addNewPPr().addNewPStyle().setVal("TOCHeading");
        p.addNewR().addNewT().setStringValue("(附 页)");//源码中为"Table of contents"
        //居中
        CTPPr pr = p.getPPr();
        CTJc jc = pr.isSetJc() ? pr.getJc() : pr.addNewJc();
        STJc.Enum en = STJc.Enum.forInt(ParagraphAlignment.CENTER.getValue());
        jc.setVal(en);
        //字体
        CTRPr pRpr = p.getRArray(0).addNewRPr();
        fonts = pRpr.isSetRFonts() ? pRpr.getRFonts() : pRpr.addNewRFonts();
        fonts.setAscii("Times New Roman");
        fonts.setEastAsia("楷体_GB2312");
        fonts.setHAnsi("楷体_GB2312");
        //加粗
        CTOnOff bold = pRpr.isSetB() ? pRpr.getB() : pRpr.addNewB();
        bold.setVal(STOnOff.TRUE);
        //字体大小
        CTHpsMeasure sz = pRpr.isSetSz() ? pRpr.getSz() : pRpr.addNewSz();
        sz.setVal(new BigInteger("28"));


        CTP p2 = content.addNewP();
//        p2.setRsidR("00EF7E24".getBytes(LocaleUtil.CHARSET_1252));
//        p2.setRsidRDefault("00EF7E24".getBytes(LocaleUtil.CHARSET_1252));
        p2.addNewPPr().addNewPStyle().setVal("TOCHeading");
        p2.addNewR().addNewT().setStringValue("目     录");//源码中为"Table of contents"
        //设置段落对齐方式，即将“目录”二字居中
        CTPPr pr2 = p2.getPPr();
        CTJc jc2 = pr2.isSetJc() ? pr2.getJc() : pr2.addNewJc();
        STJc.Enum en2 = STJc.Enum.forInt(ParagraphAlignment.CENTER.getValue());
        jc2.setVal(en2);
        //"目录"二字的字体
        CTRPr pRpr2 = p2.getRArray(0).addNewRPr();
        fonts = pRpr2.isSetRFonts() ? pRpr2.getRFonts() : pRpr2.addNewRFonts();
        fonts.setAscii("Times New Roman");
        fonts.setEastAsia("楷体_GB2312");
        fonts.setHAnsi("楷体_GB2312");
        //"目录"二字加粗
        CTOnOff bold2 = pRpr2.isSetB() ? pRpr2.getB() : pRpr2.addNewB();
        bold2.setVal(STOnOff.TRUE);
        // 设置“目录”二字字体大小为24号
        CTHpsMeasure sz2 = pRpr2.isSetSz() ? pRpr2.getSz() : pRpr2.addNewSz();
        sz2.setVal(new BigInteger("24"));

        CTSpacing pSpacing = pr2.getSpacing() != null ? pr2.getSpacing() : pr2.addNewSpacing();
        pSpacing.setBeforeLines(new BigInteger("100"));

    }

    /**
     * 不带页码的目录
     *
     * @param level 标题等级
     * @param title 文本
     * @param block 对象
     */
    public void addRowOnlyTitle(int level, String title, CTSdtBlock block) {
        CTSdtContentBlock contentBlock = block.getSdtContent();
        CTP p = contentBlock.addNewP();
//        p.setRsidR("00EF7E24".getBytes(LocaleUtil.CHARSET_1252));
//        p.setRsidRDefault("00EF7E24".getBytes(LocaleUtil.CHARSET_1252));
        CTPPr pPr = p.addNewPPr();
        pPr.addNewPStyle().setVal("TOC" + level);
        CTTabs tabs = pPr.addNewTabs();//Set of Custom Tab Stops自定义制表符集合
        CTTabStop tab = tabs.addNewTab();//Custom Tab Stop自定义制表符
        tab.setVal(STTabJc.RIGHT);
        tab.setLeader(STTabTlc.DOT);
        tab.setPos(new BigInteger("9190"));//默认为8290，因为调整过页边距，所有需要调整，手动设置找出最佳值
        pPr.addNewRPr().addNewNoProof();//不检查语法
        CTR run = p.addNewR();
        run.addNewRPr().addNewNoProof();
        run.addNewT().setStringValue(title);

        //设置行间距
        CTSpacing pSpacing = pPr.getSpacing() != null ? pPr.getSpacing() : pPr.addNewSpacing();
        pSpacing.setLineRule(STLineSpacingRule.AUTO);//行间距类型：多倍
        pSpacing.setLine(new BigInteger("360"));//此处1.5倍行间距
        if (level == 1) {
            pSpacing.setBeforeLines(new BigInteger("50"));//段前0.2
            pSpacing.setAfterLines(new BigInteger("50"));//段后0.1
        }

        //设置字体
        CTRPr pRpr = run.getRPr();
        CTFonts fonts = pRpr.isSetRFonts() ? pRpr.getRFonts() : pRpr.addNewRFonts();
        fonts.setAscii("Times New Roman");
        fonts.setEastAsia("楷体_GB2312");
        fonts.setHAnsi("楷体_GB2312");
        // 设置字体大小
        CTHpsMeasure sz = pRpr.isSetSz() ? pRpr.getSz() : pRpr.addNewSz();
        sz.setVal(new BigInteger("20"));

        CTHpsMeasure szCs = pRpr.isSetSzCs() ? pRpr.getSzCs() : pRpr.addNewSzCs();
        szCs.setVal(new BigInteger("20"));
        if (level == 1) {
            CTOnOff bold2 = pRpr.isSetB() ? pRpr.getB() : pRpr.addNewB();
            bold2.setVal(STOnOff.TRUE);

        }

    }

    /**
     * 带页码的目录
     *
     * @param level       标题等级
     * @param title       文本
     * @param page        页码
     * @param bookmarkRef
     */
    public void addRow(int level, String title, int page, String bookmarkRef) {
        CTSdtContentBlock contentBlock = this.block.getSdtContent();
        CTP p = contentBlock.addNewP();
//        p.setRsidR("00EF7E24".getBytes(LocaleUtil.CHARSET_1252));
//        p.setRsidRDefault("00EF7E24".getBytes(LocaleUtil.CHARSET_1252));
        CTPPr pPr = p.addNewPPr();
        pPr.addNewPStyle().setVal("TOC" + level);
        CTTabs tabs = pPr.addNewTabs();//Set of Custom Tab Stops自定义制表符集合
        CTTabStop tab = tabs.addNewTab();//Custom Tab Stop自定义制表符
        tab.setVal(STTabJc.RIGHT);
        tab.setLeader(STTabTlc.DOT);
        tab.setPos(new BigInteger("9100"));//默认为8290，因为调整过页边距，所有需要调整，手动设置找出最佳值
        pPr.addNewRPr().addNewNoProof();//不检查语法
        CTR run = p.addNewR();
        run.addNewRPr().addNewNoProof();
        run.addNewT().setStringValue(title);//添加标题文字
        //设置标题字体
        CTRPr pRpr = run.getRPr();
        CTFonts fonts = pRpr.isSetRFonts() ? pRpr.getRFonts() : pRpr.addNewRFonts();
        fonts.setAscii("Times New Roman");
        fonts.setEastAsia("楷体");
        fonts.setHAnsi("楷体");
        // 设置标题字体大小
        CTHpsMeasure sz = pRpr.isSetSz() ? pRpr.getSz() : pRpr.addNewSz();
        sz.setVal(new BigInteger("21"));
        CTHpsMeasure szCs = pRpr.isSetSzCs() ? pRpr.getSzCs() : pRpr.addNewSzCs();
        szCs.setVal(new BigInteger("21"));
        //添加制表符
        run = p.addNewR();
        run.addNewRPr().addNewNoProof();
        run.addNewTab();
//        //添加页码左括号
//        p.addNewR().addNewT().setStringValue("(");
        //STFldCharType.BEGIN标识与结尾处STFldCharType.END相对应
        run = p.addNewR();
        run.addNewRPr().addNewNoProof();
        run.addNewFldChar().setFldCharType(STFldCharType.BEGIN);//Field Character Type
        // pageref run
        run = p.addNewR();
        run.addNewRPr().addNewNoProof();
        CTText text = run.addNewInstrText();//Field Code 添加域代码文本控件
        text.setSpace(SpaceAttribute.Space.PRESERVE);
        // bookmark reference
        //源码的域名为" PAGEREF _Toc","\h"含义为在目录内建立目录项与页码的超链接
        text.setStringValue(" PAGEREF " + bookmarkRef + " \\h ");
        p.addNewR().addNewRPr().addNewNoProof();
        run = p.addNewR();
        run.addNewRPr().addNewNoProof();
        run.addNewFldChar().setFldCharType(STFldCharType.SEPARATE);
        // page number run
        run = p.addNewR();
        run.addNewRPr().addNewNoProof();
        run.addNewT().setStringValue(Integer.toString(page));
        run = p.addNewR();
        run.addNewRPr().addNewNoProof();
        //STFldCharType.END标识与上面STFldCharType.BEGIN相对应
        run.addNewFldChar().setFldCharType(STFldCharType.END);
//        //添加页码右括号
//        p.addNewR().addNewT().setStringValue(")");
        //设置行间距
        CTSpacing pSpacing = pPr.getSpacing() != null ? pPr.getSpacing() : pPr.addNewSpacing();
        pSpacing.setLineRule(STLineSpacingRule.AUTO);//行间距类型：多倍
        pSpacing.setLine(new BigInteger("360"));//此处1.5倍行间距
        if (level == 1) {
            CTOnOff bold2 = pRpr.isSetB() ? pRpr.getB() : pRpr.addNewB();
            bold2.setVal(STOnOff.TRUE);
            pSpacing.setBeforeLines(new BigInteger("50"));//段前0.2
            pSpacing.setAfterLines(new BigInteger("50"));//段后0.1

        }
    }


    public void addItem2TOC(XWPFDocument document) {
        List<XWPFParagraph> paragraphs = document.getParagraphs();
//        final WordCustomTOC customTOC = new WordCustomTOC(block);
        for (XWPFParagraph par : paragraphs) {
            String parStyle = par.getStyle();
            if (parStyle != null && parStyle.startsWith("Heading")) {
                List<CTBookmark> bookmarkList = par.getCTP().getBookmarkStartList();
                try {
                    int level = Integer.parseInt(parStyle.substring("Heading".length()));
//                    if (level == 1) {
//                        //添加栏目
//                        customTOC.addRowOnlyTitle(level, par.getText());
//                    } else {
//                        //添加标题
//                        customTOC.addRow(level, par.getText(), 1, bookmarkList.get(0).getName());
//                    }
//                    this.toc.addRow(level, par.getText(), 5, "");
                    this.toc.addRowOnlyTitle(level, par.getText(), this.block);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void createCustomTOC(XWPFDocument document) {
        this.toc = new WordCustomTOC(block);
        addCustomHeadingStyle(document, "Heading1", 1);
        addCustomHeadingStyle(document, "Heading2", 2);
    }

    /**
     * 自定义标题
     *
     * @param docxDocument word对象
     * @param strStyleId   标题名称
     * @param headingLevel 标题层级
     */
    private void addCustomHeadingStyle(XWPFDocument docxDocument, String strStyleId, int headingLevel) {

        CTStyle ctStyle = CTStyle.Factory.newInstance();
        ctStyle.setStyleId(strStyleId);

        CTString styleName = CTString.Factory.newInstance();
        styleName.setVal(strStyleId);
        ctStyle.setName(styleName);

        CTDecimalNumber indentNumber = CTDecimalNumber.Factory.newInstance();
        indentNumber.setVal(BigInteger.valueOf(headingLevel));

        // lower number > style is more prominent in the formats bar
        ctStyle.setUiPriority(indentNumber);

        CTOnOff onoffnull = CTOnOff.Factory.newInstance();
        ctStyle.setUnhideWhenUsed(onoffnull);

        // style shows up in the formats bar
        ctStyle.setQFormat(onoffnull);

        // style defines a heading of the given level
        CTPPr ppr = CTPPr.Factory.newInstance();
        ppr.setOutlineLvl(indentNumber);
        ctStyle.setPPr(ppr);

        XWPFStyle style = new XWPFStyle(ctStyle);

        // is a null op if already defined
        XWPFStyles styles = docxDocument.createStyles();

        style.setType(STStyleType.PARAGRAPH);
        styles.addStyle(style);

    }

}
