package com.example.demo.PaspAddDoc;

import com.example.demo.pasports.CategoryCalc;
import com.example.demo.pasports.RowfTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ThreadClass implements  Callable<Void>{
        private InputStream fFile;
        private String filename;
        private ConcurrentLinkedQueue<RowfTable<String>> allTable=new ConcurrentLinkedQueue<>();
    ConcurrentHashMap<String, ByteArrayOutputStream> SOMList=new ConcurrentHashMap<>();
        public   ThreadClass(String filename,InputStream fFile, ConcurrentLinkedQueue<RowfTable<String>> allTable1, ConcurrentHashMap<String, ByteArrayOutputStream> SOMList){
            this.allTable=allTable1;
            this.fFile=fFile;
            this.filename=filename;
            this.SOMList=SOMList;
        }



    @Override
    public Void call() throws IOException {

        XSSFWorkbook pasp=null;
        try {

            PaspAddDoc test123 = new PaspAddDoc();
            pasp=new XSSFWorkbook(fFile);
            test123.getinfopart2(pasp);
            test123.getNameTitul(pasp);
            test123.calc(pasp, filename, allTable);

            test123.testsom("C:\\Users\\Yoba\\Desktop\\Свидетельство о монтаже №123.xlsx", this.SOMList);
           // test123.testsom("/home/yoba/Рабочий стол/Свидетельство о монтаже №123.xlsx", this.SOMList);
            pasp.close();
            System.out.println(filename +" Done" );
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            pasp.close();
        }
return null;
    }
}

