package com.example.demo.pasports;

import org.apache.commons.compress.utils.Lists;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Service
public class GetInfoPasp implements NewTable {
    private  Queue<RowfTable<String>> allTable1=new ConcurrentLinkedQueue<>();
    ExecutorService pool = Executors.newFixedThreadPool(8);


    private void createTable(Map<String, ByteArrayOutputStream> fileInput) throws IOException {
        Workbook book = new XSSFWorkbook();
        Sheet sheet = book.createSheet("Result");
        Row row = sheet.createRow(0);
        String[] head={"Линия","Рабочая давление","рабочая температура","расчетное давление","расчетная температура","диаметр и толщина","отбрак","длина","наименование трубопровода","класс опасности по гост",
                "наименование рабочей среды","пожаровзрывоопасность","группа рс по гост","кат по тр тс","кат по гост","скорость коррозии","название файла","рабочее давление раздел 2","расчетное давление раздел 2","рабочая температура раздел 2",
                "расчетная температура раздел 2","чертежи","сварка","давление испытаний 2","герметичность","что-то","№ паспортца","макс диаметр паспорта","материал труб, требуется сверка с паспортом в случае пустого значения"};
        for (int i=0;i<head.length;i++){
            row.createCell(i).setCellValue(head[i]);
        }

        List<String> list = new ArrayList<>();
     //  String filePath="/home/yoba/Рабочий стол/testResult/testres.xlsx";
        String filePath="C:\\Users\\Yoba\\Desktop\\свидетельства\\testres.xlsx";
        try( FileOutputStream outputStream =  new FileOutputStream(filePath)) {
            List<Callable<Void>> tasks = new ArrayList<>();
            for(Map.Entry<String,ByteArrayOutputStream> entry:fileInput.entrySet()){
                InputStream in = new ByteArrayInputStream(entry.getValue().toByteArray());
                tasks.add(new MyTask(entry.getKey(),in,  allTable1));
            }
            try {
                this.pool.invokeAll(tasks);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
           List<RowfTable<String>> resultTable=Lists.newArrayList(allTable1.iterator());

            resultTable.sort((arg0, arg1) -> Integer.compare(arg0.getValue(26).compareTo(arg1.getValue(26)), 0));

            int ij=1;
            Iterator<RowfTable<String>> iter=resultTable.iterator(); 
            while (iter.hasNext()){
                RowfTable<String> n1=iter.next();
                Row newr=sheet.createRow(ij);
                for (int i=0;i<n1.getSize();i++){
                    if (!(n1.getValue(i)==null)){
                        newr.createCell(i).setCellValue(n1.getValue(i));
                    }
                    else {
                        newr.createCell(i).setCellValue("");
                    }
                }
                ij++;
            }
            book.write(outputStream);
            book.close();
        }


    }

    @Override
    public double getTable(Map<String,ByteArrayOutputStream> FileInput) throws IOException {
        long starttime = System.currentTimeMillis();
        this.createTable(FileInput);
        System.out.println((double)System.currentTimeMillis() / 60000 - (double)starttime / 60000);
        return (double)System.currentTimeMillis() / 60000 - (double)starttime / 60000;
    }
}


