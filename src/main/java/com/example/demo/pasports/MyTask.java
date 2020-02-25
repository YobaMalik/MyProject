package com.example.demo.pasports;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MyTask implements Callable<Void> {
    private InputStream fFile;
    private String filename;
    private Queue<RowfTable<String>> allTable;
    public MyTask(String filename, InputStream fFile, Queue<RowfTable<String>> allTable1){
        this.allTable=allTable1;
        this.fFile=fFile;
        this.filename=filename;
    }

		public Void call() throws IOException {
            try(Workbook pasp=new XSSFWorkbook(fFile)) {
                CategoryCalc newDocument = new CategoryCalc();
                newDocument.getinfopart2(pasp); // get info from part 2
                newDocument.getNameTitul(pasp);
                newDocument.calc(pasp, filename, allTable); // get and write in (file and result arraylist) info from part 3 and 5
                pasp.close();
              	System.out.println(filename +" Done" );
            }
            return null;
        }
    }

