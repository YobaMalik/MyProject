package com.example.demo.PaspAddDoc;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;

public class SvOmont {
    private String pipingName;
    private String katGOST;
    private String desingPress;
    private String desingTemp;
    private String DWGlist;
    private String fluidCode;
    private String weldinfo;
    private String NameTit;
    private String heatTreatment;
    private List<String> linelist=new ArrayList<>();
   private int count=59;
   private int i=0;
   
   public void setNameTit(String NameTit) {
   	this.NameTit=NameTit;
   	//System.out.println(NameTit);
   }
    public void setheatTreatment(String heatTreatment) {
        this.heatTreatment=heatTreatment;
    }

    public void setWeldIfno(String wldinfo) {
    	this.weldinfo=wldinfo;
    }
    public void setdesingTemp(String desingTemp){
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
    public void setDWGlist(String DWGlist){
        this.DWGlist=rewriteDWGlist(DWGlist);
    }
    public void setLinelist(List<String> pipeline){
        this.linelist.addAll(pipeline);
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



    public void createSOM(String filepath,String filename, ConcurrentHashMap<String, ByteArrayOutputStream> SOMList) throws IOException {
        FileInputStream fil=null;
        ByteArrayOutputStream outStr=null;
        try{
            String SVMname="Свидетельство о монтаже №"+ this.NameTit+".xlsx";
            fil=new FileInputStream(filepath);
            //bufStrm=new BufferedInputStream(fil);
            outStr=new ByteArrayOutputStream();
           // bufOutStrm=new BufferedOutputStream(outStr);
            XSSFWorkbook somWorkbook=new XSSFWorkbook(fil);
            XSSFSheet iSheet=somWorkbook.getSheet("Лист1");
           
            XSSFFont font=somWorkbook.createFont();
            font.setFontHeight(10);
            font.setFontName("Times New Roman");
            XSSFCellStyle myStyle=somWorkbook.createCellStyle();
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



          iSheet.getRow(28).getCell(0).setCellValue(this.DWGlist);
          //DWG

          iSheet.getRow(44).getCell(0).setCellValue(this.heatTreatment);
          CellStyle wrapCEll1=iSheet.getRow(44).getCell(0).getCellStyle();
          wrapCEll1.setWrapText(true);
          iSheet.getRow(44).getCell(0).setCellStyle(wrapCEll1);
          //heatTreatment

          iSheet.getRow(2).getCell(0).setCellValue("СВИДЕТЕЛЬСТВО № "+this.NameTit);
          // Number from titul
         
          iSheet.getRow(34).getCell(10).setCellValue(this.weldinfo);
          // welding


         StringBuilder allLine=new StringBuilder();
         iSheet.createRow(58);
         this.linelist.forEach( e->{
             allLine.append(e.replaceAll("\n", ""));
             if (this.i!=this.linelist.size()) {
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

         iSheet.getRow(34).getCell(10).setCellValue(this.weldinfo);
         //welding (gtaw, etc....)
         
         iSheet.createRow(count+1).createCell(0).setCellValue("От изготовителя:"+"\n"+"Менеджер ООО «ЗапСибНефтехим»");
         iSheet.getRow(count+1).getCell(0).setCellStyle(myStyle);
         iSheet.addMergedRegion(new CellRangeAddress(count+1,count+1,0,17));
         
         iSheet.createRow(count+2).createCell(0).setCellValue("_________________ /  Тайшева Елена Валерьевна ____________________________________«05» августа 2019 г");
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


            outStr=new ByteArrayOutputStream();
            somWorkbook.write(outStr);
       /*  int count=0;
         byte[] bts=new byte[8192];
         while((count=fil.read(bts))!=-1){
             outStr.write(bts,0,count);
         }*/
            fil.close();
         if(!SOMList.containsKey(SVMname)){
             SOMList.put(SVMname,outStr);
         }
         somWorkbook.close();

            outStr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            if (fil!=null){
                fil.close();
            }
        }

    }

}

