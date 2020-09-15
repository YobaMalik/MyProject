package com.example.demo.PaspAddDoc;

import com.example.demo.Pasport.RowfTable;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Callable;

public class ThreadClass implements  Callable<Void>{
        private InputStream fFile;
        private String fileName;
        Map<String, ByteArrayOutputStream> somList;
        public   ThreadClass(String fileName, InputStream fFile,
                             Map<String, ByteArrayOutputStream> somList){
            this.fFile=fFile;
            this.fileName=fileName;
            this.somList=somList;
        }

    @Override
    public Void call() throws IOException {
            PaspAddDoc newExceldDoc = new PaspAddDoc(fFile);
            //newExceldDoc.testsom("C:\\Users\\Yoba\\Desktop\\Свидетельство о монтаже №123.xlsx", this.somList);
            newExceldDoc.testsom("/home/yoba/Рабочий стол/Свидетельство о монтаже №123.xlsx", this.somList);
            System.out.println(fileName +" Done" );

        return null;
    }
}

