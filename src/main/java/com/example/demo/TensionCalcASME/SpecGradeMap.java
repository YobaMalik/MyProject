package com.example.demo.TensionCalcASME;

import com.fasterxml.jackson.core.JsonToken;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.comparator.Comparators;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class SpecGradeMap implements InterfaceGetMap {
    private Map<String,HashMap<String,String>> test=new LinkedHashMap<>();
    private Map<String,String> gradeMap=new HashMap<>();

    private static final String DB_Path = "C:\\Users\\Yoba\\Desktop\\DATABASE.xlsx";

public SpecGradeMap() throws IOException {
    this.getGradeMap();
}

    @Override
    public void getGradeMap() throws IOException {
        Map<String,HashMap<String,String>> unsortetMap=new HashMap<>();
        try (Workbook wb = new XSSFWorkbook(new FileInputStream(new File(DB_Path)));) {
            Sheet tSheet = wb.getSheet("SigmaT");
            for (int i = 1; i < tSheet.getLastRowNum()+1; i++) {
                String specT = tSheet.getRow(i).getCell(2).getStringCellValue();
                String gradeT = tSheet.getRow(i).getCell(3).toString().replaceAll(".0","");
                if(unsortetMap.containsKey(specT)){
                    unsortetMap.get(specT).put(gradeT,gradeT);
                } else{
                    HashMap<String,String> lul=new HashMap<>();
                    lul.put(gradeT,gradeT);
                    unsortetMap.put(specT,lul);
                }
                gradeMap.putIfAbsent(gradeT,gradeT);
            }
        }

        unsortetMap.entrySet().stream().sorted(Map.Entry.comparingByKey()).
                forEach(e->this.test.put(e.getKey(), e.getValue()));

    }

    public Map<String,HashMap<String,String>> getVal() throws IOException {

        return this.test;
    }
    public Map<String,String> getGradeVal() throws IOException {

        return this.gradeMap;
    }


}