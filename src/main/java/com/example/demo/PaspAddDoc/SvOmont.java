package com.example.demo.PaspAddDoc;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;

public class SvOmont {
    private String pipingName;
    private String katGOST;
    private String desingPress;
    private String desingTemp;
    private String dwgList;
    private String fluidCode;
    private String weldInfo;
    private String NameTit;
    private String heatTreatment;
    private List<String> lineList=new ArrayList<>();
    private int count=59;
    private int i=0;
   
   public void setNameTit(String NameTit) {
   	this.NameTit=NameTit;
   }
    public void setHeatTreatment(String heatTreatment) {
        this.heatTreatment=heatTreatment;
    }

    public void setWeldIfno(String wldInfo) {
    	this.weldInfo=wldInfo;
    }
    public void setDesingTemp(String desingTemp){
        this.desingTemp=desingTemp;
    }
    public void setDesingPress(String desingPress){
        this.desingPress=desingPress;
    }
    public void setKatGOST(String katGOST){
        this.katGOST=katGOST;
    }
    public void setPipingName(String pipingName){
        this.pipingName=pipingName.replaceAll("\n", "");
    }
    public void setFluidCode(String fluidCode){
        this.fluidCode=fluidCode;
    }
    public void setDWGlist(String dwgList){
        this.dwgList=rewriteDWGlist(dwgList);
    }
    public void setLinelist(List<String> pipeline){
        this.lineList.addAll(pipeline);
    }

    private String rewriteDWGlist (String DWGlist){
       String[] splt=DWGlist.replaceAll(" ","").split(";");
       StringBuilder rDWGlist=new StringBuilder();
       Arrays.stream(splt).forEach(e->{
    	   this.i++;
           rDWGlist.append(e);
           if(this.i!=splt.length) {
               if (this.i >2 && this.i % 3 == 0) {
                   rDWGlist.append("; " + "\n");
               } else {
                   rDWGlist.append("; ");
               }
           }
           
        });
       this.i=1;
       return rDWGlist.toString();
    }



    public void createSOM(String filepath, Map<String, ByteArrayOutputStream> somList) throws IOException {

        try(FileInputStream fil=new FileInputStream(filepath);
            ByteArrayOutputStream outStr=new ByteArrayOutputStream();
            Workbook somWorkbook=new XSSFWorkbook(fil)){
            String SVMname="Свидетельство о монтаже №"+ this.NameTit+".xlsx";
            Sheet iSheet=somWorkbook.getSheet("Лист1");
            Font font=somWorkbook.createFont();
            short fontHeight=200;
            font.setFontHeight(fontHeight);
            font.setFontName("Times New Roman");
            CellStyle myStyle=somWorkbook.createCellStyle();
            myStyle.setFont(font);
            myStyle.setAlignment(LEFT);
            /*
            Cell style and font
             */

            String nameandparapms=this.fluidCode+","+"\n"+"Ррасч.(МПа) = "+this.desingPress+","+"\n"+"Трасч.(°C) ="+this.desingTemp;
            iSheet.getRow(14).getCell(0).setCellValue(nameandparapms);
            iSheet.getRow(14).setHeightInPoints(24+this.desingTemp.length()/114*12+12);
           // System.out.println((short)(24+this.desingTemp.length()/10));
              /*
            fluid code and design pressure and temperature
             */

            String nameandgost=this.pipingName+", "+this.katGOST;
            iSheet.getRow(8).getCell(0).setCellValue(nameandgost);
            CellStyle wrapCEll=iSheet.getRow(8).getCell(0).getCellStyle();
            wrapCEll.setWrapText(true);
            iSheet.getRow(8).getCell(0).setCellStyle(wrapCEll);
            /*
            Pipeline info (group gost, name, purpose)
            */



          iSheet.getRow(28).getCell(0).setCellValue(this.dwgList);
          //DWG

          iSheet.getRow(44).getCell(0).setCellValue(this.heatTreatment);
          CellStyle wrapCEll1=iSheet.getRow(44).getCell(0).getCellStyle();
          wrapCEll1.setWrapText(true);
          iSheet.getRow(44).getCell(0).setCellStyle(wrapCEll1);
          //heatTreatment

          iSheet.getRow(2).getCell(0).setCellValue("СВИДЕТЕЛЬСТВО № "+this.NameTit);
          // Number from titul
         
          iSheet.getRow(34).getCell(10).setCellValue(this.weldInfo);
          // welding


         StringBuilder allLine=new StringBuilder();
         iSheet.createRow(58);
         this.lineList.forEach( e->{
             allLine.append(e.replaceAll("\n", ""));
             if (this.i!=this.lineList.size()) {
                 if (this.i >= 2 && this.i % 2 == 0) {
                     allLine.append(";" + "\n");
                 } else {
                     allLine.append("; ");
                 }
             }
        	iSheet.createRow(count).createCell(0).setCellValue((count-58)+". Свидетельство о монтаже технологических трубопроводов "+ e);
        	iSheet.getRow(count).getCell(0).setCellStyle(myStyle);
        	iSheet.addMergedRegion(new CellRangeAddress(count,count,0,17));

        	count++;
        	this.i++;
         });
         iSheet.getRow(10).getCell(0).setCellValue(allLine.toString());
         /*
         pipeline section
          */

         iSheet.getRow(34).getCell(10).setCellValue(this.weldInfo);
         //welding (gtaw, etc....)
         
         iSheet.createRow(count+1).createCell(0).setCellValue("От изготовителя:"+"\n"+"Менеджер ");
         iSheet.getRow(count+1).getCell(0).setCellStyle(myStyle);
         iSheet.addMergedRegion(new CellRangeAddress(count+1,count+1,0,17));
         
         iSheet.createRow(count+2).createCell(0).setCellValue("_________________ /  Иванов Иван Иванович ____________________________________«11» декабря 1111 г");
         iSheet.getRow(count+2).getCell(0).setCellStyle(myStyle);
         iSheet.addMergedRegion(new CellRangeAddress(count+2,count+2,0,17));
         
         iSheet.createRow(count+3).createCell(0).setCellValue("                                 (подпись / Ф.И.О.)");
         iSheet.getRow(count+3).getCell(0).setCellStyle(myStyle);
         iSheet.addMergedRegion(new CellRangeAddress(count+3,count+3,0,17));
         
         iSheet.createRow(count+4).createCell(0).setCellValue("                                                                                                                        М.П.");
         iSheet.getRow(count+4).getCell(0).setCellStyle(myStyle);
         iSheet.addMergedRegion(new CellRangeAddress(count+4,count+4,0,17));
         /*
         previous code 4 blocks is fucking retarded peace of shit
          */



            somWorkbook.write(outStr);
            fil.close();
         if(!somList.containsKey(SVMname)){
             somList.put(SVMname,outStr);
         }
        }
        }

    }



