package com.example.demo.WordReplacePackage;

import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReplaceMethods {

    public void replaceFooterText(XWPFDocument document, String token, String value , int pageCount) throws XmlException {

        Iterator<XWPFFooter> iter=document.getFooterList().iterator();
        while(iter.hasNext()) {
            XWPFFooter f=iter.next();
            for (XWPFParagraph paragraph : f.getParagraphs()) {
                XmlCursor cursor = paragraph.getCTP().newCursor();
                cursor.selectPath("declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' .//*/w:txbxContent/w:p/w:r");

                List<XmlObject> ctrsintxtbx = new ArrayList<XmlObject>();

                while(cursor.hasNextSelection()) {
                    cursor.toNextSelection();
                    XmlObject obj = cursor.getObject();
                    ctrsintxtbx.add(obj);
                }
                for (XmlObject obj : ctrsintxtbx) {
                    CTR ctr = CTR.Factory.parse(obj.xmlText());
                    //CTR ctr = CTR.Factory.parse(obj.newInputStream());
                    XWPFRun bufferrun = new XWPFRun(ctr, (IRunBody)paragraph);
                    String text = bufferrun.getText(0);



                    if (text != null && text.contains(token)) {
                        text = text.replace(token, value);
                        bufferrun.setText(text, 0);
                        obj.set(bufferrun.getCTR());
                    }

                    if (text != null && text.contains("Колич")) {
                        text = text.replace("Колич", Integer.toString(pageCount));
                        bufferrun.setText(text, 0);
                        obj.set(bufferrun.getCTR());
                    }
                }
            }

        }

    }

    public void replaceFooterText(XWPFDocument document, String token, String value) {
        for (XWPFFooter footer : document.getFooterList()) {
            for (XWPFParagraph p : footer.getParagraphs()) {
                List<XWPFRun> r = p.getRuns();

                for (XWPFRun e : r) {
                    String text = e.getText(0);
                    if (text != null && text.contains(token)) {
                        text = text.replaceAll(token, value);
                        e.setText(text, 0);

                    }
                }
            }
        }
    }
    public void replaceDatesInTables(XWPFTable tables,String token,String value){
        if(token!=null&& value!=null) {
            tables.getRows().forEach(z->{
                z.getTableCells().forEach(jCell->{
                    StringBuffer text=new StringBuffer();

                    for (XWPFParagraph b : jCell.getParagraphs()) {
                        Iterator<XWPFRun> runIter = b.getRuns().iterator();
                        while (runIter.hasNext()) {
                            try {

                                XWPFRun n = runIter.next();
                                text.append(n.getText(0));
                            } catch (Exception ez) {
                                ez.printStackTrace();
                            }
                        }
                    }

                    String res= text.toString();
                    if (res.contains(token)) {
                        XWPFParagraph par=jCell.getParagraphArray(0);
                        String inStyle=par.getStyle();

                        CTPPr cellCTP=jCell.getCTTc().getPArray(0).getPPr();
                        CTP newCTP=CTP.Factory.newInstance();
                        newCTP.setPPr(cellCTP);

                        jCell.getCTTc().setPArray(new CTP[]{newCTP});


                        res = res.replaceAll(token, value);
                        jCell.setText(res);
                        jCell.getParagraphArray(0).setStyle(inStyle);
                    }
                    jCell.getTables().forEach(tbl-> this.replaceDatesInTables(tbl, token, value));
                });
            });
        }
    }
}

