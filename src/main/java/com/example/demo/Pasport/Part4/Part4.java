package com.example.demo.Pasport.Part4;

import com.example.demo.Pasport.InterfacePasp.AddMethods;
import com.example.demo.Pasport.InterfacePasp.ExtractData;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Part4 implements AddMethods, ExtractData {
    private String weldInfo;
    private String heatTreatment;
    private String DWGs;

    public String getHeatTreatment () {
        return heatTreatment;
    }

    public String getWeldInfo () {
        return weldInfo;
    }

    public String getDWGs() {
        return DWGs;
    }

    private void getInfoPart4(Workbook excPasp) {
        Sheet iSheet = null;
        int cIndex = 0;

        //FormulaEvaluator evaluator1 = excPasp.getCreationHelper().createFormulaEvaluator();

        for (int j = 0; j < excPasp.getNumberOfSheets(); ++j) {
            if (excPasp.getSheetName(j).equals("4")) {
                iSheet = excPasp.getSheetAt(j);
                break;
            }
        }

        if (!this.checkFNIP(excPasp) && iSheet != null) {
            CellRangeAddress mergeadres;
            int mergRow = 20;
            int mergCol = -1;
            //int firstrow=0;
            String adString = null;
            for (int j = 0; j < iSheet.getNumMergedRegions(); ++j) {
                mergeadres = iSheet.getMergedRegion(j);
                int mergRow1 = mergeadres.getFirstRow();
                int mergCol1 = mergeadres.getFirstColumn();
                adString = iSheet.getRow(mergRow1).getCell(mergCol1).toString().toLowerCase();
                if (adString.contains("номера") && adString.contains("чертежей")) {
                    mergCol = mergCol1;
                    if (mergRow > mergRow1) mergRow = mergRow1;
                }
            }
            if (mergCol != -1) {
                for (int i = mergCol + 1; i < iSheet.getRow(mergRow).getLastCellNum(); ++i) {
                    CellType checkType = iSheet.getRow(mergRow).getCell(i).getCellType();
                    if (checkType != CellType.BLANK) {
                        cIndex = i;
                        break;
                    }
                }
            }

            for (int j = 0; j < iSheet.getNumMergedRegions(); ++j) {
                mergeadres = iSheet.getMergedRegion(j);
                mergRow = mergeadres.getFirstRow();
                int mergCol2 = mergeadres.getFirstColumn();

                if (iSheet.getRow(mergRow).getCell(cIndex) != null && iSheet.getRow(mergRow).getCell(mergCol2) != null) {
                    adString = iSheet.getRow(mergRow).getCell(mergCol2).toString().toLowerCase();
                    if (adString.contains("монтаже") && adString.contains("трубопровода") && adString.contains("сварки")) {
                        this.weldInfo = iSheet.getRow(mergRow).getCell(cIndex).toString();
                    }
                }
                if (iSheet.getRow(mergRow).getCell(cIndex) != null && iSheet.getRow(mergRow).getCell(cIndex) != null) {
                    if (adString.contains("термообработке") && adString.contains("вид и режим")) {
                        this.heatTreatment = iSheet.getRow(mergRow).getCell(cIndex).toString();
                    }
                }
            }
        }
    }

    private void getDWG(Workbook excPasp) {
        Sheet iSheet = null;
        int cIndex = 0;

        //FormulaEvaluator evaluator1 = excPasp.getCreationHelper().createFormulaEvaluator();


        for(int j = 0; j < excPasp.getNumberOfSheets(); ++j) {
            if (excPasp.getSheetName(j).equals("4")) {
                iSheet = excPasp.getSheetAt(j);
                break;
            }
        }

        if (!this.checkFNIP(excPasp)&&iSheet != null) {

            CellRangeAddress mergeadres;
            int mergRow=200;
            int mergCol=-1;
            int lastrowDWG = 0;
            String adString;


            for(int j = 0; j < iSheet.getNumMergedRegions(); ++j) {
                mergeadres = iSheet.getMergedRegion(j);
                int mergRow1 = mergeadres.getFirstRow();
                int mergCol1 = mergeadres.getFirstColumn();
                adString = iSheet.getRow(mergRow1).getCell(mergCol1).toString().toLowerCase();
                if (adString.contains("номера")  && adString.contains("узловых") && adString.contains("чертежей") ) {
                    mergCol=mergCol1;
                    if(mergRow>mergRow1) mergRow=mergRow1;
                }

            }

            for(int j = 0; j < iSheet.getNumMergedRegions(); ++j) {
                mergeadres = iSheet.getMergedRegion(j);
                int mergRow1 = mergeadres.getFirstRow();
                int mergCol1 = mergeadres.getFirstColumn();
                adString = iSheet.getRow(mergRow1).getCell(mergCol1).toString().toLowerCase();
                if (adString.contains("изготовитель") ) {
                    lastrowDWG=mergRow1;
                }

            }


            for(int i = mergCol + 1; i < iSheet.getRow(mergRow).getLastCellNum(); ++i) {
                CellType checkType = iSheet.getRow(mergRow).getCell(i).getCellType();
                if (checkType != CellType.BLANK) {
                    cIndex = i;
                    break;
                }
            }

            HashMap<String,String> ISOlist=new HashMap<>();

            for (int i=mergRow;i<lastrowDWG-1;i++) {
                if (iSheet.getRow(i)!=null && iSheet.getRow(i).getCell(cIndex)!=null ) {
                    adString=iSheet.getRow(i).getCell(cIndex).toString().replaceAll("\n", "");
                    adString=adString.replaceAll(" ",";");
                    String[] DWGlst=adString.split(";");
                    for (String s : DWGlst) {
                        if (s != null && s.length() > 2) {
                            ISOlist.put(s, s);
                        }
                    }
                }
            }

            StringBuilder teststringbilder=new StringBuilder();
            ISOlist.entrySet().stream().sorted(Map.Entry.comparingByKey())
                    .forEach(e->{
                        teststringbilder.append(e.getValue());
                        teststringbilder.append(";");
                    });
            this.DWGs=teststringbilder.toString();
        }

    }

    @Override
    public void exctractData(Workbook excPasp) {
        this.getInfoPart4(excPasp);
        this.getDWG(excPasp);
    }
}