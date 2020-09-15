package com.example.demo.WordReplacePackage;

import org.apache.poi.ss.usermodel.*;

import java.util.List;
import java.util.Map;

public class Token {
    public void setTokenMap(Workbook wb, Map<String, Row> tokenMap, List<String> fileNameList){
        Sheet iSheet= wb.getSheet("Токены");
        tokenMap.put("Токены",iSheet.getRow(0));
        FormulaEvaluator evaluator1 = wb.getCreationHelper().createFormulaEvaluator();
        for (int i=1;i<=iSheet.getLastRowNum();i++){
            fileNameList.add(iSheet.getRow(i).getCell(0).toString());
            if(tokenMap.containsKey(iSheet.getRow(i).getCell(0).toString())) {

                Row existRow=tokenMap.get(iSheet.getRow(i).getCell(0).toString());

                int minCellIndex=iSheet.getRow(i).getLastCellNum()<
                        existRow.getLastCellNum()?iSheet.getRow(i).getLastCellNum():
                        existRow.getLastCellNum();

                for (int j=0;j<iSheet.getRow(i).getLastCellNum();j++) {
                    String newValue;
                    String valInExtistRow="";
                    String addValue="";

                    if(existRow.getCell(j)!=null) {

                        CellValue value=evaluator1.evaluate(existRow.getCell(j));
                        if(value!=null) {
                            if(value.getStringValue()!=null){
                                valInExtistRow=value.getStringValue();
                            } else {
                                valInExtistRow=existRow.getCell(j).toString();
                            }
                        }
                    }

                    if(iSheet.getRow(i).getCell(j)!=null) {
                        CellValue value= evaluator1.evaluate(iSheet.getRow(i).getCell(j));
                        if(value!=null) {
                            if(value.getStringValue()!=null){
                                addValue= value.getStringValue();
                            } else {
                                addValue = iSheet.getRow(i).getCell(j).toString();
                            }
                        }
                    }


                    if (this.stringToDouble(valInExtistRow)) valInExtistRow=valInExtistRow.replace(".0","");
                    if (this.stringToDouble(addValue)) addValue=addValue.replace(".0","");
                    newValue=valInExtistRow;

                    if (!valInExtistRow.contains(addValue)) newValue=valInExtistRow+"; "+addValue;

                    existRow.createCell(j).setCellValue(newValue);
                }
            }else {
                tokenMap.put(iSheet.getRow(i).getCell(0).toString(),iSheet.getRow(i));
            }

        }
    }

    private boolean stringToDouble(String value){
        try {
            double d=Double.parseDouble(value);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }
}
