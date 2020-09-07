package com.example.demo.TensionCalcASME;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SigmaASME implements Interpolation{
    private static final String TABLE_SIGMA_B="SigmaB";
    private static final String TABLE_SIGMA_T="SigmaT";
    private static final String DB_Path="C:\\Users\\Yoba\\Desktop\\DATABASE.xlsx";

    private final double arrayT[]={40, 65, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375,
            400, 425, 450, 475, 500, 525
    };
    private final double arrayB[]={40, 100, 150, 200, 250, 300, 325, 350, 375, 400, 425, 450, 475,
            500, 525
    };


    private double getSigmaB(String spec, String grade, double temp) throws IOException {
        double sigmaB=this.calculateSigmas( spec,  grade,  temp,this.arrayB,SigmaASME.TABLE_SIGMA_B);
        return sigmaB;
    }

    private double getSigmaT(String spec, String grade, double temp) throws IOException {
        double sigmaT=this.calculateSigmas( spec,  grade,  temp,this.arrayT,SigmaASME.TABLE_SIGMA_T);
        return sigmaT;
    }

    protected double calculateSigmas(String spec, String grade, double temp,double[] array,String tableSigma)
            throws IOException {
        try(Workbook wb=new XSSFWorkbook(new FileInputStream(new File(SigmaASME.DB_Path)));){
            int countT=-10;
            Sheet tSheet=wb.getSheet(tableSigma);
            for (int i=1;i<tSheet.getLastRowNum()+1;i++){
                String specT=tSheet.getRow(i).getCell(2).getStringCellValue();
              //  String gradeT=tSheet.getRow(i).getCell(3).getStringCellValue();

                String gradeT=tSheet.getRow(i).getCell(3).toString();
                if(specT.equals(spec) && gradeT.equals(grade)){
                    countT=i;
                }
            }
            double resValue=0;

            for (int i=0;i<array.length-1;i++){
                if(array[i]==temp) {
                    resValue=tSheet.getRow(countT).getCell(i+5).getNumericCellValue();
                }
                if(array[i]<temp&&array[i+1]>temp) {
                    resValue = this.interpolation(tSheet.getRow(0).getCell(i + 5).getNumericCellValue(),
                            tSheet.getRow(0).getCell(i + 6).getNumericCellValue(), tSheet.getRow(countT)
                                    .getCell(i + 5).getNumericCellValue(), tSheet.getRow(countT).getCell(i + 6)
                                    .getNumericCellValue(),
                            temp);
                }

            }
            if(array[0]>=temp) resValue=tSheet.getRow(countT).getCell(5).getNumericCellValue();
            if(array[array.length-1]<=temp) resValue=tSheet.getRow(countT).getCell(array.length+4)
                    .getNumericCellValue();
            return resValue;
        }
    }

    public double getTension(String spec, String grade, double temp) throws IOException {

            return Math.min(this.getSigmaT(spec, grade, temp) / 1.5,
                    this.getSigmaB(spec, grade, temp) / 2.5);

    }
}
