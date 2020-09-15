package com.example.demo.OBRE;

import com.example.demo.Interface.IResultDocs;

import com.example.demo.Pasport.RowfTable;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

@Service
public class CreateOBRE implements GetTableNumber, IResultDocs {

    private void fillWordFile(Map<String, ByteArrayOutputStream> fileArray, Map<String, ByteArrayOutputStream> resultList){

        try {
            String templatePath="/home/yoba/Рабочий стол/testResult/1.docx";
            //String templatePath="C:\\Users\\Yoba\\Desktop\\sv-va\\1.docx";
            FileInputStream oTemplate=new FileInputStream(new File(templatePath));
            XWPFDocument wDoc=new XWPFDocument(oTemplate);
            OBREapplication newOBRE=new OBREapplication();

        for (Map.Entry<String, ByteArrayOutputStream> ent:fileArray.entrySet()){
            Queue<RowfTable<String>> row=new ConcurrentLinkedQueue<>();
            InputStream in=new ByteArrayInputStream(ent.getValue().toByteArray());
            PaspInfo dataExtraction=new PaspInfo(in);
            dataExtraction.fillResultQueue(ent.getKey(),row);
            newOBRE.FillTable(wDoc,row);
        }
        newOBRE.DeleteTabpe(wDoc);

            ByteArrayOutputStream out=new ByteArrayOutputStream();
            wDoc.write(out);
            resultList.put("Template.docx",out);
            wDoc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void createDocs(Map<String, ByteArrayOutputStream> fileInput,
                           Map<String, ByteArrayOutputStream> resultList,
                           ExecutorService newTask ) {

        this.fillWordFile(fileInput, resultList);
    }
}
