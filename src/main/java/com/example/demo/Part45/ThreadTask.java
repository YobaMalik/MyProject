package com.example.demo.Part45;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadTask implements Callable<Void>,GetRejThickness {
    private InputStream fFile;
    private String fileName;
    Map<String, ByteArrayOutputStream> fileStengthCalc;
    public ThreadTask(String fileName, InputStream fFile, Map<String, ByteArrayOutputStream> fileStengthCalc){
        this.fFile=fFile;
        this.fileName=fileName;
        this.fileStengthCalc=fileStengthCalc;
    }



    @Override
    public Void call() throws Exception {
        try(Workbook wb=new XSSFWorkbook(fFile);
            Workbook resultWb=new XSSFWorkbook();
            ByteArrayOutputStream bStream=new ByteArrayOutputStream()
        )
        {
            List<NewRow<String>> resultList=new ArrayList<>();
            PaspInfoPart45 wtf=new PaspInfoPart45(wb,"5.1",this.fileName);
            wtf.FillTable(resultList);
            PaspInfoPart45 wtf1=new PaspInfoPart45(wb,"5.4",this.fileName);
            wtf1.FillTable(resultList);

            SplitResultInfo result=new SplitResultInfo(resultWb,resultList);
           result.fillResultDocs();

          //  Sheet iSheet= resultWb.createSheet("result");
         //   this.createHead(iSheet,resultWb);

/*
            for (int i=0;i<resultList.size();i++) {
                Row row=iSheet.createRow(i+1);
                String outD = null;
                String outD1 = null;
                String eThickness = null;
                for(int j=0;j<resultList.get(i).getSize();j++) {
                    row.createCell(j).setCellValue(resultList.get(i).getValue(j));

                }
                String outDiam=resultList.get(i).getValue(2).replace("х","x").replace("/n", "");
                if (outDiam.toLowerCase().contains("x")) {
                    List<String> splitList=GetDiamAndThik(outDiam);
                    try {
                        for (int j=0;j<splitList.size();j++) {
                            row.createCell(j+7).setCellValue(splitList.get(j));
                            outD=splitList.get(0);
                            eThickness=splitList.get(1);
                            outD1=splitList.size()>2?splitList.get(2):null;
                        }


                        row.createCell(11).setCellValue(new NewRow<String>().getRejThick(outD.replace(",",".")));
                        if(outD1!=null && checkParse(outD1.replace(",","."))) {
                            if(outD1!=null) row.createCell(12).setCellValue(new NewRow<String>().getRejThick(outD1.replace(",",".")));

                            if(row.getCell(1).toString().toLowerCase().contains("перех")&&!row.getCell(1).toString().toLowerCase().contains("тройн")) {
                                row.createCell(13).setCellValue(new NewRow<String>().getEndToEndSizeASME169(outD.replace(",",".")));
                                row.createCell(14).setCellValue(new NewRow<String>().getReducerType(row.getCell(1).toString()));
                            }
                        }
                    }catch(Exception z1) {
                        z1.printStackTrace();
                        System.out.println(row.getCell(0).toString());
                    }
                }

            }
            */
            resultWb.write(bStream);
            fileStengthCalc.putIfAbsent(fileName,bStream);
        }
        return null;
    }
}
