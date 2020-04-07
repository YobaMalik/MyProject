package com.example.demo.Pasport.Titul;

import com.example.demo.Pasport.InterfacePasp.ExtractData;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class Titul implements ExtractData {
    private String numbTitul;

    public String getNumbTitul() {
        return numbTitul;
    }

    private void getNameTitul(Workbook excPasp)  {
        String nameTitul=null;
        Sheet iSheet = null;
        //FormulaEvaluator evaluator1 = excPasp.getCreationHelper().createFormulaEvaluator();

        for(int j = 0; j < excPasp.getNumberOfSheets(); ++j) {
            if (excPasp.getSheetName(j).equals("Sheet1")) {
                iSheet = excPasp.getSheetAt(j);
                break;
            }
        }

        if (iSheet != null) {

            for(int j = 0; j < iSheet.getNumMergedRegions(); ++j) {
                CellRangeAddress mergeadres = iSheet.getMergedRegion(j);
                int mergRow = mergeadres.getFirstRow();
                int mergCol = mergeadres.getFirstColumn();
                String adString = iSheet.getRow(mergRow).getCell(mergCol).toString().toUpperCase();
                if (adString.contains("ПАСПОРТ")  && adString.contains("ТРУБОПРОВОДА")&& adString.contains("№")) {
                    String aStr=adString.replace(" ", "").replaceAll("\n", "");
                    aStr=aStr.replaceAll("ПАСПОРТТРУБОПРОВОДА", "");
                    aStr=aStr.replace("№", "");
                    //String[] splt=aStr.split("№");
                    nameTitul=aStr; //splt[splt.length-1];

                }
            }
        }

        this.numbTitul=nameTitul;
    }

    @Override
    public void exctractData(Workbook excPasp) {
        this.getNameTitul(excPasp);
    }

}
