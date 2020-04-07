package com.example.demo.Part45;

import com.example.demo.Interface.Rows;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SplitResultInfo implements FillWorkbookSheet {

    private HashMap<String, List<Rows<String>>> taskList = new HashMap<>();
    private Workbook wb = new XSSFWorkbook();
    private CellStyle style;


    public SplitResultInfo(Workbook wb, List<Rows<String>> resultList) {
        this.wb = wb;
        this.style = wb.createCellStyle();
        this.style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        this.style.setFillForegroundColor(IndexedColors.GREEN.getIndex());

        this.splitInfo(resultList);
    }

    public void splitInfo(List<Rows<String>> resultList) {
        List<Rows<String>> pipes = new ArrayList<>();
        List<Rows<String>> elbows = new ArrayList<>();
        List<Rows<String>> tees = new ArrayList<>();
        List<Rows<String>> reducers = new ArrayList<>();
        List<Rows<String>> blindFl = new ArrayList<>();

        resultList.forEach(e -> {
            String elemName = e.getValue(1).toLowerCase();
            if (elemName.contains("труб") && !elemName.replace(" ", "").contains("раструб")) {
                pipes.add(e);
            }

            if (elemName.contains("отвод")) {
                elbows.add(e);
            }

            if (elemName.contains("тройн")) {
                tees.add(e);
            }

            if (elemName.contains("перех") && !elemName.replace(" ", "").contains("тройн")) {
                reducers.add(e);
            }

            if (elemName.contains("заглуш")) {
                blindFl.add(e);
            }
        });
        this.taskList.put("трубы", pipes);
        this.taskList.put("отводы", elbows);
        this.taskList.put("тройники", tees);
        this.taskList.put("переходы", reducers);
        this.taskList.put("заглушки", blindFl);
        this.taskList.put("Result", resultList);
    }

    public void fillResultDocs() {
        if (this.taskList.size() > 0) {
            this.taskList.entrySet().forEach(e -> {
                Sheet iSheet = wb.createSheet(e.getKey());
                try {
                    String[] ara = new HeadTemplateClass().getHeadTemplate(e.getKey());
                    //Arrays.stream(ara).forEach(System.out::println);
                    this.FillSheet(iSheet, e.getValue(), ara, this.style);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
        }
    }
}