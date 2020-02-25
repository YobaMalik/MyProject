package com.example.demo.PaspAddDoc;

import com.example.demo.pasports.RowfTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadClass implements  Callable<Void>{
        private InputStream fFile;
        private String fileName;
        private Queue<RowfTable<String>> allTable;
        Map<String, ByteArrayOutputStream> somList;
        public   ThreadClass(String fileName, InputStream fFile, Queue<RowfTable<String>> allTable1, Map<String, ByteArrayOutputStream> somList){
            this.allTable=allTable1;
            this.fFile=fFile;
            this.fileName=fileName;
            this.somList=somList;
        }

    @Override
    public Void call() throws IOException {

        try ( XSSFWorkbook pasp=new XSSFWorkbook(fFile)){
            PaspAddDoc newWordDoc = new PaspAddDoc();
            newWordDoc.getinfopart2(pasp);
            newWordDoc.getNameTitul(pasp);
            newWordDoc.calc(pasp, fileName, allTable);
            newWordDoc.testsom("C:\\Users\\Yoba\\Desktop\\Свидетельство о монтаже №123.xlsx", this.somList);
           // newWordDoc.testsom("/home/yoba/������� ����/������������� � ������� �123.xlsx", this.SOMList);
            pasp.close();
            System.out.println(fileName +" Done" );
        }
        return null;
    }
}

