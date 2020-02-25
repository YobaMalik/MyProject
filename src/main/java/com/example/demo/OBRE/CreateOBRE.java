package com.example.demo.OBRE;

import com.example.demo.pasports.RowfTable;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
@Service
public class CreateOBRE implements GetTableNumber{

    public void fillWordFile(Map<String, ByteArrayOutputStream> FileArray){

        try {
            FileInputStream oTemplate=new FileInputStream(new File("C:\\Users\\Yoba\\Desktop\\свидетельства\\1.docx"));
            XWPFDocument wDoc=new XWPFDocument(oTemplate);

            OBREapplication newOBRE=new OBREapplication();
        for (Map.Entry<String, ByteArrayOutputStream> ent:FileArray.entrySet()){
                Queue<RowfTable<String>> row=new ConcurrentLinkedQueue<>();
                PaspInfo calc=new PaspInfo();
                InputStream in=new ByteArrayInputStream(ent.getValue().toByteArray());
                Workbook wb=new XSSFWorkbook(in);
                calc.calc(wb,ent.getKey(),row);
                newOBRE.FillTable(wDoc,row);
        }
        newOBRE.DeleteTabpe(wDoc);
            //wDoc.write(new FileOutputStream(new File("C:\\Users\\Yoba\\Desktop\\свидетельства\\12.docx")));
            wDoc.write(new FileOutputStream(new File("C:\\Users\\Yoba\\Desktop\\свидетельства\\12.docx")));
            wDoc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
