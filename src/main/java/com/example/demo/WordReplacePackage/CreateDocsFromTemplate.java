package com.example.demo.WordReplacePackage;

import com.example.demo.Interface.ResultDocs;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class CreateDocsFromTemplate implements ResultDocs  {

    ExecutorService newPool ;
    private InputStream excelFile ;
    private Map<String, Row> tokenMap = new HashMap<>();
    private Map<String, ByteArrayOutputStream> result = new ConcurrentHashMap<>();
    private List<String> fileNameList=new ArrayList<>();
    private Map<String, ByteArrayOutputStream> template=new HashMap<>();
    private XSSFWorkbook wb;

    @Override
    public void createDocs(Map<String, ByteArrayOutputStream> fileInput, Map<String, ByteArrayOutputStream> resultList,ExecutorService newTask) throws IOException {
        this.newPool=newTask;
        this.getTemplateStream(fileInput);
        this.getExcelTable(fileInput);
        this.invokeTaskArrayList(resultList);
    }

    public void getExcelTable(Map<String, ByteArrayOutputStream> fileArray) throws IOException {
        fileArray.forEach((key, value) -> {
            if (key.contains(".xlsx") && this.excelFile == null) {
                this.excelFile = new ByteArrayInputStream(value.toByteArray());
            }
        });
        this.wb=new XSSFWorkbook(this.excelFile);
        new Token().setTokenMap(this.wb, this.tokenMap,this.fileNameList);
    }


    public  void getTemplateStream(Map<String, ByteArrayOutputStream> fileInput) throws IOException {

        Iterator<Map.Entry<String, ByteArrayOutputStream>> iter = fileInput.entrySet().iterator();

        while (iter.hasNext()) {
            Map.Entry<String, ByteArrayOutputStream> entry=iter.next();
            if (entry.getKey().contains(".docx") && entry.getKey().contains("multi")) {
                template.putIfAbsent("multi",entry.getValue());
            }
            if (entry.getKey().contains(".docx") && !entry.getKey().contains("multi")) {
                template.putIfAbsent("single",entry.getValue());
            }
        }
    }

    public void invokeTaskArrayList(Map<String, ByteArrayOutputStream> resultList) throws IOException {
        List<WordReplacePackage> taskList=new ArrayList<>();
        Row tokenRow=this.tokenMap.get("Токены");

        Iterator<Map.Entry<String, Row>> iter=this.tokenMap.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry<String, Row> entry=iter.next();

            if(entry.getKey().compareTo("Токены")!=0) {
                Row valueRow = entry.getValue();
                int fileCount = this.rowsNumb(entry.getKey());
                try {

                    ByteArrayOutputStream out=(fileCount <= 1 && fileCount > 0 ? this.template.get("single") : this.template.get("multi"));
                    System.out.println(fileCount);
                    taskList.add(new WordReplacePackage(resultList, new ByteArrayInputStream(out.toByteArray()), tokenRow, valueRow, entry.getKey(), this.wb));

                } catch (Exception e) {
                    e.printStackTrace();
                    // System.out.println(valueRow.getCell(0).toString()+" 123");
                }
            }
        }
        try{
            newPool.invokeAll(taskList);
            newPool.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            newPool.shutdown();
        }

    }

    private int rowsNumb(String value){
        int count= (int) this.fileNameList.stream().filter(e -> e.compareTo(value) == 0).count();
        return count;
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

}
/*
  Row tokenRow = this.tokenMap.get("Токены");
                    Row valueRow = this.tokenMap.get(k);

                       this.tokenMap.keySet().forEach(k->{
            if(k.compareTo("Токены")!=0) {

    public  void replaceTokenInDocx(Map<String, ByteArrayOutputStream> fileArray, Map<String, ByteArrayOutputStream> resultDocs) throws IOException {
        this.wordReplacePackage(fileArray);
        this.result=resultDocs;

        fileArray.forEach((k, v) -> {
            if(k.contains(".docx")) {
                try {
                    this.replaceTokenByValue(v);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
 */