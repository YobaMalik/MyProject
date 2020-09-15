package com.example.demo.Pasport;

import com.example.demo.Interface.IResultList;
import com.example.demo.Pasport.Part2.Part2;
import com.example.demo.Pasport.Part3.Part3;
import com.example.demo.Pasport.Part4.Part4;
import com.example.demo.Pasport.Part5.MaxDN;
import com.example.demo.Pasport.Part6.Part6;
import com.example.demo.Pasport.Titul.Titul;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;


public class ExtractPasportData implements IResultList {

    Part2 part2=new Part2();
    Part3 part3=new Part3();
    Part4 part4=new Part4();
    MaxDN maxDNPart5=new MaxDN();
    Part6 part6=new Part6();
    Titul titul=new Titul();

    private String pipename;
    private String fluidCode;
    private String Hazardcode;
    private String expHazard;
    private String groupGOST;
    private String katTPTC;
    private String katGOST;
    private String corrosionRate;
    private String operationPressure;
    private String operationTemp;
    private String designTemp;
    private String factoryName;
    private String testPressHydro;
    private String testPressPnevmo;
    private String minTemp;
    private String desL;
    private String billP;
    private String groupTPTC;
    private String numbOS;
    private String desingPressure;

    private String nameTitul;
    private String leakTest;
    private String weldInfo;
    private double maxDN;
    private String DWGs;
    private String aFile;
    private String heatTreatment;

    private List<RowfTable<String>> listFromPart3=new ArrayList<>();
    private HashMap<String, String> pipeMaterial;


    private List<String> pipeLine=new ArrayList<>();
    public List<String> getPipeline(){
        return this.pipeLine;
    }
    public String getFileName(){return this.aFile;}
    public String getgroupTPTC() {
        return this.groupTPTC;
    }
    public String getnumbOS() {
        return this.numbOS;
    }

    public String getbillP() {
        return this.billP;
    }
    public String getdesL() {
        return this.desL;
    }
    public String getLeakTest() {
        return this.leakTest;
    }

    public String getFactoryName() {
        return this.factoryName;
    }
    public String getNameTitul() {
        return this.nameTitul;
    }

    public String getTestPressHydro() {
        return this.testPressHydro;
    }
    public String getTestPressPnevmo() {
        return this.testPressPnevmo;
    }

    public String getOperationTemp() {
        return this.operationTemp;
    }
    public String getDesingPressure() {
        return this.desingPressure;
    }
    public String getweldInfo() {
        return this.weldInfo;
    }
    public String getDWGs() {
        return this.DWGs;
    }
    public String getheatTreatment() {
        return this.heatTreatment;
    }
    public String getpipename() {
        return this.pipename;
    }

    public String getFluidCode() {
        return this.fluidCode;
    }

    public String getHazardcode() {
        return this.Hazardcode;
    }

    public String getExphazard() {
        return this.expHazard;
    }

    public String getGroupGOST() {
        return this.groupGOST;
    }

    public String getkatTPTC() {
        return this.katTPTC;
    }

    public String getkatGOST() {
        return this.katGOST;
    }

    public String getCorrosionRate() {
        return this.corrosionRate;
    }

    public String getOperationPressure() {
        return this.operationPressure;
    }

    public String getDesignTemp() {
        return this.designTemp;
    }

    public String getmintemp() {
        return this.minTemp;
    }

    public double getMaxDN() {
        return maxDN;
    }

    public ExtractPasportData(InputStream inStr) throws IOException {
        Workbook excPasp=new XSSFWorkbook(inStr);
        this.part2.exctractData(excPasp);
        this.part3.exctractData(excPasp);
        this.part4.exctractData(excPasp);
        this.maxDNPart5.exctractData(excPasp);
        this.part6.exctractData(excPasp);
        this.titul.exctractData(excPasp);

    }

    private void defineVariables(){
        //define from part 2
        this.pipename=this.part2.getPipename();
        this.fluidCode=this.part2.getFluidCode();
        this.Hazardcode=this.part2.getHazardcode();
        this.expHazard=this.part2.getExpHazard();
        this.groupGOST=this.part2.getGroupGOST();
        this.katTPTC=this.part2.getKatTPTC();
        this.katGOST=this.part2.getKatGOST();
        this.corrosionRate=this.part2.getCorrosionRate();
        this.operationPressure=this.part2.getOperationPressure();
        this.operationTemp=this.part2.getOperationTemp();
        this.designTemp=this.part2.getDesignTemp();
        this.factoryName=this.part2.getFactoryName();
        this.testPressHydro=this.part2.getTestPressHydro();
        this.testPressPnevmo=this.part2.getTestPressPnevmo();
        this.minTemp=this.part2.getMinTemp();
        this.desL=this.part2.getDesL();
        this.billP=this.part2.getBillP();
        this.groupTPTC=this.part2.getGroupTPTC();
        this.numbOS=this.part2.getNumbOS();
        this.desingPressure=this.part2.getDesingPressure();

        //from titul
        this.nameTitul=this.titul.getNumbTitul();
        //System.out.println( this.nameTitul+" "+this.titul.getNumbTitul());
        //from part6
        this.leakTest=this.part6.getLeakTest();

        // from part 3 and 5
        this.maxDN=this.maxDNPart5.getMaXDN();
        this.pipeMaterial=this.maxDNPart5.getPipeMaterial();

        //from part 4
        this.weldInfo=this.part4.getWeldInfo();
        this.DWGs=this.part4.getDWGs();
        this.heatTreatment=this.part4.getHeatTreatment();

        //from part3
        this.listFromPart3=this.part3.getResLinePart3(this.pipeLine);
    }

    public void fillResultQueue( String aFile, Queue<RowfTable<String>> allTable)  {
        this.aFile=aFile;
        this.defineVariables();
        this.listFromPart3.forEach(singleRow->{
            singleRow.addValue(8,this.getpipename());
            singleRow.addValue(9,this.getHazardcode());
            singleRow.addValue(10,this.getFluidCode());//error
            singleRow.addValue(11,this.getExphazard());
            singleRow.addValue(12,this.getGroupGOST());//error
            singleRow.addValue(13,this.getkatTPTC());
            singleRow.addValue(14,this.getkatGOST());
            singleRow.addValue(15,this.getCorrosionRate());
            singleRow.addValue(16,this.aFile);
            singleRow.addValue(17,this.getOperationPressure());
            singleRow.addValue(18,this.getDesingPressure());
            singleRow.addValue(19,this.getOperationTemp());
            singleRow.addValue(20,this.getDesignTemp());
       //     singleRow.addValue(21,"");
         //   singleRow.addValue(22,"");
            singleRow.addValue(21,this.getDWGs());
            singleRow.addValue(22,this.getweldInfo());
            singleRow.addValue(23, this.getTestPressHydro());
            singleRow.addValue(24, this.getTestPressPnevmo());
            // singleRow.addValue(25, this.getleaktest());
            singleRow.addValue(25, this.getFactoryName());
            singleRow.addValue(26,this.getNameTitul());
            singleRow.addValue(27, Double.toString(this.getMaxDN()));
            singleRow.addValue(28, this.pipeMaterial.getOrDefault(singleRow.getValue(5), "требуется проверка"));
            singleRow.addValue(29,this.getmintemp());
            singleRow.addValue(30, this.getbillP());
            singleRow.addValue(31, this.getdesL());
            singleRow.addValue(32, this.getnumbOS());
            singleRow.addValue(33, this.getgroupTPTC());
            allTable.add(singleRow);
        });
    }

    @Override
    public void defVariables() {
        this.defineVariables();
    }
}
