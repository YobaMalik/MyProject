package com.example.demo.WordReplacePackage;

import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;


public class WordReplacePackage implements Callable<Void> {

    Row tokenRow;
    Row valueRow;
    String fileName;
    ByteArrayInputStream value;
    Map<String, ByteArrayOutputStream> resultDocs;
    XSSFWorkbook wb;
    public WordReplacePackage(Map<String, ByteArrayOutputStream> resultDocs,
                              ByteArrayInputStream value, Row tokenRow,
                              Row valueRow, String fileName, XSSFWorkbook wb) {

        this.value=value;
        this.fileName=fileName;
        this.tokenRow=tokenRow;
        this.valueRow=valueRow;
        this.resultDocs=resultDocs;
        this.wb=wb;
    }


    private ByteArrayOutputStream copyStream(InputStream value) throws IOException {
        ByteArrayOutputStream in=new ByteArrayOutputStream();
        byte[] bts=new byte[8192];
        int count=0;
        while((count=value.read(bts))!=-1){
            in.write(bts,0,count);
        }
        return in;
    }

    public void replaceTokenByValue(Map<String, ByteArrayOutputStream> resultDocs,
                                    ByteArrayInputStream value, Row tokenRow,
                                    Row valueRow, String fileName) {

        FormulaEvaluator evaluator1 =  this.wb.getCreationHelper().createFormulaEvaluator();
        try  ( XWPFDocument wDoc  = new XWPFDocument(value)){

            //int pageCount=wDoc.getProperties().getExtendedProperties().getUnderlyingProperties().getPages();

            if (valueRow != null) {
                for (int i = 1; i < valueRow.getLastCellNum(); i++) {
                    String str1 = null ;

                    if(tokenRow.getCell(i)!=null) {
                        str1 = tokenRow.getCell(i).toString();
                    }
                    String str2 = null ;

                    if(valueRow.getCell(i)!=null) {
                        CellValue valCell=evaluator1.evaluate( valueRow.getCell(i));
                        if(valCell!=null) {
                            if( valCell.getStringValue()==null) {
                                str2 = valueRow.getCell(i).toString();
                            } else{
                                str2=valCell.getStringValue();
                            }
                            if(this.stringToDouble(str2)) {
                                str2=str2.replace(".0", "");
                                str2=str2.replace(".", ",");
                            }
                        }
                    }
                    // System.out.println(str1.substring(0, 1));
                    //  System.out.println(text);

                    for (XWPFTable docTable : wDoc.getTables()) {
                        new ReplaceMethods().replaceDatesInTables(docTable, str1, str2);
                    }

                    //   new ReplaceDocsFooter().replaceFooterText(wDoc, str1,str2, pageCount );

                    for (XWPFParagraph p : wDoc.getParagraphs()) {
                        List<XWPFRun> r = p.getRuns();

                        for (XWPFRun e : r) {
                            String text = e.getText(0);

                            if (text != null && text.contains(str1)) {
                                text = text.replaceAll(str1, str2);
                                e.setText(text, 0);

                            }
                        }
                    }
                }
            }

            try {
                ByteArrayOutputStream out1=new ByteArrayOutputStream();
                wDoc.write(out1);
                resultDocs.putIfAbsent(fileName + ".docx", out1);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception  e) {
            e.printStackTrace();
        } // TODO Auto-generated catch block

    }


    private double conv(String vaule) throws ParseException {
        NumberFormat format=NumberFormat.getInstance(Locale.FRANCE);
        Number number=format.parse(vaule);
        return  number.doubleValue();
    }

    private boolean stringToDouble(String value){
        try {
            double d=Double.parseDouble(value);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public Void call() {
        this.replaceTokenByValue(this.resultDocs,this.value,this.tokenRow,this.valueRow,this.fileName);
        return null;
    }
}


