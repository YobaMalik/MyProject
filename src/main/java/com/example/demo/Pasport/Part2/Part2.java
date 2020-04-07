package com.example.demo.Pasport.Part2;


import com.example.demo.Pasport.InterfacePasp.AddMethods;
import com.example.demo.Pasport.InterfacePasp.ExtractData;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

public class Part2 implements AddMethods, ExtractData {

    private String pipeName;
    private String fluidCode;
    private String hazardCode;
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

    public String getPipename() {
        return pipeName;
    }

    public String getFluidCode() {
        return fluidCode;
    }

    public String getHazardcode() {
        return hazardCode;
    }

    public String getExpHazard() {
        return expHazard;
    }

    public String getGroupGOST() {
        return groupGOST;
    }

    public String getKatTPTC() {
        return katTPTC;
    }

    public String getKatGOST() {
        return katGOST;
    }

    public String getCorrosionRate() {
        return corrosionRate;
    }

    public String getOperationPressure() {
        return operationPressure;
    }

    public String getOperationTemp() {
        return operationTemp;
    }

    public String getDesignTemp() {
        return designTemp;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public String getTestPressHydro() {
        return testPressHydro;
    }

    public String getTestPressPnevmo() {
        return testPressPnevmo;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public String getDesL() {
        return desL;
    }

    public String getBillP() {
        return billP;
    }

    public String getGroupTPTC() {
        return groupTPTC;
    }

    public String getNumbOS() {
        return numbOS;
    }

    public String getDesingPressure() {
        return desingPressure;
    }

    private void getInfoPart2(Workbook excPasp)  {
        int cIndex = new CellsCoordinate().getCoordinate(excPasp);
        int tempIndexu=0;
        int tempIndexd=0;
        CellRangeAddress tempPress = null;
        Sheet iSheet = excPasp.getSheet("2");
        FormulaEvaluator evaluator1 = excPasp.getCreationHelper().createFormulaEvaluator();


        if (!this.checkFNIP(excPasp)&& iSheet != null) {
            CellRangeAddress mergeadres;
            int mergRow;
            int mergCol;
            String adString;


            for(int j = 0; j < iSheet.getNumMergedRegions(); ++j) {

                mergeadres = iSheet.getMergedRegion(j);
                mergRow = mergeadres.getFirstRow();
                mergCol = mergeadres.getFirstColumn();
                adString = iSheet.getRow(mergRow).getCell(mergCol).toString().toLowerCase();


                if (iSheet.getRow(mergRow).getCell(cIndex)!=null) {

                    if (adString.contains("наименование") && adString.contains("трубопровода")) {
                        if(iSheet.getRow(mergRow).getCell(cIndex)!=null) {
                            this.pipeName = iSheet.getRow(mergRow).getCell(cIndex).toString();
                        }else {
                            this.pipeName="Н/д";
                        }
                    }

                    if (adString.contains("цех") && adString.contains("установка")) {
                        if(iSheet.getRow(mergRow).getCell(cIndex)!=null) {
                            this.factoryName = iSheet.getRow(mergRow).getCell(cIndex).toString();
                        } else {
                            this.factoryName="Н/д";
                        }
                    }

                    if (adString.contains("наименование") && adString.contains("среды")) {
                        this.fluidCode = iSheet.getRow(mergRow).getCell(cIndex).toString();
                    }

                    if (adString.contains("класс") && adString.contains("опасности") && adString.contains("гост")) {
                        this.hazardCode = iSheet.getRow(mergRow).getCell(cIndex).toString();
                    }

                    if (adString.contains("взрывоопасность")) {
                        this.expHazard = iSheet.getRow(mergRow).getCell(cIndex).toString();
                    }

                    if (adString.contains("группа") && adString.contains("среды") && adString.contains("32569")) {
                        this.groupGOST = iSheet.getRow(mergRow).getCell(cIndex).toString();
                    }

                    if (adString.contains("трубопровода") && adString.contains("категория") && adString.contains("032/2013")) {
                        this.katTPTC = iSheet.getRow(mergRow).getCell(cIndex).toString();
                    }

                    if (adString.contains("трубопровода") && adString.contains("категория") && adString.contains("32569")) {
                        this.katGOST = iSheet.getRow(mergRow).getCell(cIndex).toString();
                    }

                    if (adString.contains("группа") && adString.contains("среды") && adString.contains("032/2013")) {
                        this.groupTPTC = iSheet.getRow(mergRow).getCell(cIndex).toString();
                    }

                    if (adString.contains("гидравлического")) {
                        this.testPressHydro = iSheet.getRow(mergRow).getCell(cIndex).toString();
                    }

                    if (adString.contains("пневматического")) {
                        this.testPressPnevmo = iSheet.getRow(mergRow).getCell(cIndex).toString();
                    }

                    if (adString.contains("минимально")&& adString.contains("допустимая")) {
                        this.minTemp = iSheet.getRow(mergRow).getCell(cIndex).toString();
                    }

                    if (adString.contains("расчетный")&& adString.contains("ресурс")) {
                        this.desL = iSheet.getRow(mergRow).getCell(cIndex).toString();
                    }

                    if (adString.contains("расчетный")&& adString.contains("службы")) {
                        this.billP = iSheet.getRow(mergRow).getCell(cIndex).toString();
                    }

                    if (adString.contains("расчетный")&& adString.contains("ресурс")) {
                        this.desL = iSheet.getRow(mergRow).getCell(cIndex).toString();
                    }

                    if (adString.contains("расчетное")&& adString.contains("количество")) {
                        this.numbOS = iSheet.getRow(mergRow).getCell(cIndex).toString();
                    }


                    if (adString.contains("коррозии")) {
                        CellValue corRate = evaluator1.evaluate(iSheet.getRow(mergRow).getCell(cIndex));
                        if (corRate!=null) {
                            if  (corRate.getStringValue()==null) {
                                this.corrosionRate = Double.toString(corRate.getNumberValue());

                            } else {
                                this.corrosionRate = corRate.getStringValue();
                            }
                        } else {
                            this.corrosionRate=iSheet.getRow(mergRow).getCell(cIndex).toString();

                        }


                    }
                    if (adString.trim().contains("давление,") && adString.trim().contains("мпа")) {
                        this.operationPressure = iSheet.getRow(mergRow).getCell(cIndex).toString();
                        tempPress=mergeadres;
                    }

                    if (adString.trim().contains("температура,") ) {
                        tempIndexu=mergeadres.getFirstRow();
                        tempIndexd=mergeadres.getLastRow();
                        this.operationTemp=iSheet.getRow(mergRow).getCell(cIndex).toString();
                    }

                }

            }


            for (int j=tempPress.getFirstRow();j<tempPress.getLastRow();j++) {
                CellType checkType = iSheet.getRow(j).getCell(cIndex).getCellType();
                if (j!=tempPress.getFirstRow() &&checkType != CellType.BLANK ) {

                    this.desingPressure=iSheet.getRow(j).getCell(cIndex).toString();
                }
            }
            for(int j = 0; j < iSheet.getNumMergedRegions(); ++j) {
                mergeadres = iSheet.getMergedRegion(j);
                mergRow = mergeadres.getFirstRow();
                mergCol = mergeadres.getFirstColumn();
                adString = iSheet.getRow(mergRow).getCell(mergCol).toString().toLowerCase();

                if (adString.contains("расчетная") && tempIndexu<= mergeadres.getFirstRow()&& tempIndexd >mergeadres.getFirstRow()) {
                    this.designTemp=iSheet.getRow(mergRow).getCell(cIndex).toString();
                }

                if (adString.contains("расчетная") && tempIndexu<= mergeadres.getFirstRow()&& tempIndexd >mergeadres.getFirstRow()) {
                    this.designTemp=iSheet.getRow(mergRow).getCell(cIndex).toString();
                }
            }
        }

    }

    @Override
    public void exctractData(Workbook excPasp) {
        this.getInfoPart2(excPasp);
    }
}
