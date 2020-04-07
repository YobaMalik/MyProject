package com.example.demo.Pasport.Part3;


import com.example.demo.Pasport.InterfacePasp.ExtractData;
import com.example.demo.Pasport.RowfTable;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import java.util.ArrayList;
import java.util.List;

public class Part3 implements ExtractData {
    private int otbThikn = -1;
    private int diams = -1;
    private int linesANDzone = -1;
    private int lastcell = -1;
    private int lastLAZcell = -1;
    private int lastRow = -1;

    private int[] params = new int[4];

    private List<String> pipeLine=new ArrayList<>();

    private Sheet iSheet;

   private List<RowfTable<String>> resLinePart3=new ArrayList<>();

   public List<RowfTable<String>> getResLinePart3(List<String> pipeLine){
      pipeLine.addAll( this.pipeLine);
       return this.resLinePart3;
   }

    private void getCoordinate(Workbook excPasp){
        for(int i = 0; i < excPasp.getNumberOfSheets(); ++i) {
            if (excPasp.getSheetName(i).trim().equals("3")) {
                this.iSheet = excPasp.getSheetAt(i);
                break;
            }
        }

        for(int i = 0; i < this.iSheet.getNumMergedRegions(); i++) {

            CellRangeAddress mergeadres = this.iSheet.getMergedRegion(i);

            int mergRow = mergeadres.getFirstRow();
            int mergCol = mergeadres.getFirstColumn();
            String adString = this.iSheet.getRow(mergRow).getCell(mergCol).toString().toLowerCase();

            if (adString.contains("отбраковочная") && adString.contains("толщина")) {
                this.otbThikn = mergeadres.getFirstColumn();
            }

            if (adString.contains("наружный") && adString.contains("диаметр") && adString.contains("толщина") && adString.contains("стенки")) {
                this.diams = mergeadres.getFirstColumn();

            }

            if (adString.contains("наименование")  && adString.contains("участка") && adString.contains("обозначение")  && adString.contains("схеме")) {
                this.linesANDzone = mergeadres.getFirstColumn();
                this.lastLAZcell = mergeadres.getLastRow() + 1;

            }

            if (adString.contains("участков")  && adString.contains("трубопровода")) {
                this.lastcell = mergeadres.getFirstColumn();

            }

            if (adString.contains("перечень схем")) {
                this.lastRow = mergeadres.getFirstRow();
            } else{
                this.lastRow=iSheet.getLastRowNum();
            }

        }

        int tempIndex=0;
        for(int adINT = this.linesANDzone; adINT <= this.lastcell; adINT++) {
            CellType checkType = this.iSheet.getRow(this.lastLAZcell).getCell(adINT).getCellType();
            if (checkType != CellType.BLANK && adINT > this.linesANDzone && adINT < this.diams) {
                this.params[tempIndex] = adINT;
                tempIndex++;
            }
        }

        for(int adINT = this.lastRow; adINT >= 0; adINT--) {
            if (this.iSheet.getRow(adINT) != null && iSheet.getRow(adINT).getCell(this.diams) != null) {
                CellType checkType = this.iSheet.getRow(adINT).getCell(this.diams).getCellType();
                if (checkType != CellType.BLANK && this.lastRow == -1) {
                    this.lastRow = adINT;
                    break;
                }
            }
        }
    }

    private  void getInfoPart3(Workbook excPasp){

        this.getCoordinate(excPasp);

        if (this.diams != -1 && this.linesANDzone != -1 && this.lastcell != -1 && this.lastLAZcell != -1) {

            int adINT = -1;
            for(int i = this.lastLAZcell; i <= this.lastRow + 1; ++i) {
                if (iSheet.getRow(i) != null && iSheet.getRow(i).getCell(this.diams) != null && iSheet.getRow(i).getCell(this.diams).toString().length()>0) {
                    CellType checkType1 = iSheet.getRow(i).getCell(this.diams).getCellType();
                    if (checkType1 != CellType.BLANK) {
                        RowfTable<String> singleRow=new RowfTable<>();
                        FormulaEvaluator evaluator1 = excPasp.getCreationHelper().createFormulaEvaluator();
                        CellValue newvalue = evaluator1.evaluate(iSheet.getRow(i).getCell(this.lastcell));
                        CellType cType = iSheet.getRow(i).getCell(this.linesANDzone).getCellType();
                        if (cType != CellType.BLANK && iSheet.getRow(i).getCell(this.linesANDzone).toString().length()>0) {

                            this.pipeLine.add(iSheet.getRow(i).getCell(this.linesANDzone).toString());
                            singleRow.addValue(0,iSheet.getRow(i).getCell(this.linesANDzone).toString());
                            singleRow.addValue(1,iSheet.getRow(i).getCell(this.params[0]).toString());
                            singleRow.addValue(2,iSheet.getRow(i).getCell(this.params[1]).toString());
                            singleRow.addValue(3,iSheet.getRow(i).getCell(this.params[2]).toString());
                            singleRow.addValue(4,iSheet.getRow(i).getCell(this.params[3]).toString());
                            adINT = i;
                        } else {
                            singleRow.addValue(0,iSheet.getRow(adINT).getCell(this.linesANDzone).toString());
                            singleRow.addValue(1,iSheet.getRow(adINT).getCell(this.params[0]).toString());
                            singleRow.addValue(2,iSheet.getRow(adINT).getCell(this.params[1]).toString());
                            singleRow.addValue(3,iSheet.getRow(adINT).getCell(this.params[2]).toString());
                            singleRow.addValue(4,iSheet.getRow(adINT).getCell(this.params[3]).toString());
                        }
                        singleRow.addValue(5,iSheet.getRow(i).getCell(this.diams).toString());

                        singleRow.addValue(6,iSheet.getRow(i).getCell(this.otbThikn).toString());
                        if (newvalue != null) {
                            singleRow.addValue(7,Double.toString(newvalue.getNumberValue()));
                        } else {
                            singleRow.addValue(7,iSheet.getRow(i).getCell(this.lastcell).toString());
                        }
                        this.resLinePart3.add(singleRow);
                    }
                }
            }
        }
    }

    @Override
    public void exctractData(Workbook excPasp) {
        this.getInfoPart3(excPasp);
    }
}
