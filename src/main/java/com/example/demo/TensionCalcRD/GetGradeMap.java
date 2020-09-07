package com.example.demo.TensionCalcRD;

import com.example.demo.TensionCalcASME.InterfaceGetMap;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Service
public class GetGradeMap  implements InterfaceGetMap {

    Map<String,String> gradeMap=new HashMap<>();

    private static final String DB_Path = "C:\\Users\\Yoba\\Desktop\\DATABASE.xlsx";

    public GetGradeMap() throws IOException {
        this.getGradeMap();
    }
    @Override
    public void getGradeMap() throws IOException {
        Map<String,String> gradeMap=new HashMap<>();
        try (Workbook wb = new XSSFWorkbook(new FileInputStream(new File(DB_Path)));) {

            new SigmaRD().arrayST.keySet().forEach(key->{

                Sheet tSheet = wb.getSheet(key);
                for (int i = 1; i < tSheet.getLastRowNum(); i++) {
                    String desLife = tSheet.getRow(i).getCell(2).getStringCellValue();
                    String grade = tSheet.getRow(i).getCell(3).getStringCellValue();

                    if(!this.gradeMap.containsKey(desLife)){
                        this.gradeMap.put(grade,key);
                    }


                }
            });
        }
        this.gradeMap.entrySet().stream().sorted(Map.Entry.comparingByKey());
    }

    public Map<String,String> getGrMap(){
        return gradeMap;
    }

}
