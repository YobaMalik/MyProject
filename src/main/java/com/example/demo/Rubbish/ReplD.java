package com.example.demo.Rubbish;

//import org.apache.commons.io.FileUtils;
import com.example.demo.pasports.TestInt;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReplD implements TestInt {
	
  
	/*
	private String DesingPressure;
	private String OperationTemp;
	private String OperationPressure;
	private String Corrosionrate;
	private String DesignTemp;
	private String numbOS;
	private String desL;
	private String billP;
	private String minTemp;
	private String TestPressPnevmo;
	private String TestPressHydro;
	private String groupTPTC;
	private String katGOST;
	private String groupGOST;
	private String Exphazard;
	private String Hazardcode;
	private String Fluidcode;
	private String FactoryName;
	private String pipename;*/
	private String katTPTC;
	private int katTPTCrow;
	private int katCol;
	private String newKat;
	
	public void createBackup(String filepath) {
		File file=new File(filepath);
		String newPath=file.getParent()+"\\Backup1\\"+file.getName();
	File newFile=new File(newPath);
	/*	try {
			FileUtils.copyFile(file, newFile);
			System.out.println(newPath);			
		} catch(IOException e) {
			e.printStackTrace();
		}
		*/
	}
	 public void getinfopart2(XSSFWorkbook excPasp) {
	
    int cIndex = 0;
   // int tempIndexu=0;
   // int tempIndexd=0;
//    CellRangeAddress tempPress = null;

        XSSFSheet iSheet = null;
      // FormulaEvaluator evaluator1 = excPasp.getCreationHelper().createFormulaEvaluator();


        for(int j = 0; j < excPasp.getNumberOfSheets(); ++j) {
            if (excPasp.getSheetName(j).equals("2")) {
                iSheet = excPasp.getSheetAt(j);
                break;
            }
        }

        if (!this.checkfnip(excPasp, iSheet)&& iSheet != null) {
            CellRangeAddress mergeadres;
            int mergRow;
            int mergCol;
            String adString;
            for(int j = 0; j < iSheet.getNumMergedRegions(); ++j) {
                mergeadres = iSheet.getMergedRegion(j);
                mergRow = mergeadres.getFirstRow();
                mergCol = mergeadres.getFirstColumn();
                adString = iSheet.getRow(mergRow).getCell(mergCol).toString().toLowerCase();
                if (adString.contains("наименование")  && adString.contains("предприятия") ) {
                    for(int i = mergCol + 1; i < iSheet.getRow(mergRow).getLastCellNum(); ++i) {
                        CellType checkType = iSheet.getRow(mergRow).getCell(i).getCellType();
                        if (checkType != CellType.BLANK) {
                            cIndex = i;
                            this.katCol=i;
                            break;
                        }
                    }
                }
            }

            for(int j = 0; j < iSheet.getNumMergedRegions(); ++j) {
                mergeadres = iSheet.getMergedRegion(j);
                mergRow = mergeadres.getFirstRow();
                mergCol = mergeadres.getFirstColumn();
                adString = iSheet.getRow(mergRow).getCell(mergCol).toString().toLowerCase();
              
                if (iSheet.getRow(mergRow).getCell(cIndex)!=null) {
                	/*  if (adString.contains("наименование") && adString.contains("трубопровода")) {
                    	if(iSheet.getRow(mergRow).getCell(cIndex)!=null) {
                        this.pipename = iSheet.getRow(mergRow).getCell(cIndex).toString();
                    	}else {
                    		this.pipename="Н/д";
                    		}
                    }
                    
                    if (adString.contains("цех") && adString.contains("установка")) {
                    	if(iSheet.getRow(mergRow).getCell(cIndex)!=null) {
                        this.FactoryName = iSheet.getRow(mergRow).getCell(cIndex).toString();
                    	} else {
                    		this.FactoryName="Н/д";
                    	}
                    }

                    if (adString.contains("наименование") && adString.contains("среды")) {
                        this.Fluidcode = iSheet.getRow(mergRow).getCell(cIndex).toString();
                    }

                    if (adString.contains("класс") && adString.contains("опасности") && adString.contains("гост")) {
                        this.Hazardcode = iSheet.getRow(mergRow).getCell(cIndex).toString();
                    }

                    if (adString.contains("взрывоопасность")) {
                        this.Exphazard = iSheet.getRow(mergRow).getCell(cIndex).toString();
                    }

                    if (adString.contains("группа") && adString.contains("среды") && adString.contains("32569")) {
                        this.groupGOST = iSheet.getRow(mergRow).getCell(cIndex).toString();
                    }
*/
                    if (adString.contains("трубопровода") && adString.contains("категория") && adString.contains("032/2013")) {
                        this.katTPTC = iSheet.getRow(mergRow).getCell(cIndex).toString();
                        this.katTPTCrow=mergRow;
                    }
/*
                    if (adString.contains("трубопровода") && adString.contains("категория") && adString.contains("32569")) {
                        this.katGOST = iSheet.getRow(mergRow).getCell(cIndex).toString();
                    }
                    
                    if (adString.contains("группа") && adString.contains("среды") && adString.contains("032/2013")) {
                        this.groupTPTC = iSheet.getRow(mergRow).getCell(cIndex).toString();
                    }
                    
                    if (adString.contains("гидравлического")) {
                        this.TestPressHydro = iSheet.getRow(mergRow).getCell(cIndex).toString();
                    }
                    
                    if (adString.contains("пневматического")) {
                        this.TestPressPnevmo = iSheet.getRow(mergRow).getCell(cIndex).toString();
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
                            if ( !(corRate.getStringValue() instanceof String)) {
                                this.Corrosionrate = Double.toString(corRate.getNumberValue());
                            } else {
                                this.Corrosionrate = corRate.getStringValue();
                            }
                        }
                    }
                    if (adString.trim().contains("давление,") && adString.trim().contains("мпа")) {
                        this.OperationPressure = iSheet.getRow(mergRow).getCell(cIndex).toString();
                        tempPress=mergeadres;
                    }

                    if (adString.trim().contains("температура,") ) {
                        tempIndexu=mergeadres.getFirstRow();
                        tempIndexd=mergeadres.getLastRow();
                        this.OperationTemp=iSheet.getRow(mergRow).getCell(cIndex).toString();
                    }
                   */
                }
            }
            /*
            for (int j=tempPress.getFirstRow();j<tempPress.getLastRow();j++) {
            	CellType checkType = iSheet.getRow(j).getCell(cIndex).getCellType();
            	if (j!=tempPress.getFirstRow() &&checkType != CellType.BLANK ) {
            	
            		this.DesingPressure=iSheet.getRow(j).getCell(cIndex).toString();
            	}
            }
            for(int j = 0; j < iSheet.getNumMergedRegions(); ++j) {
                mergeadres = iSheet.getMergedRegion(j);
                mergRow = mergeadres.getFirstRow();
                mergCol = mergeadres.getFirstColumn();
                adString = iSheet.getRow(mergRow).getCell(mergCol).toString().toLowerCase();

                if (adString.contains("расчетная") && tempIndexu<= mergeadres.getFirstRow()&& tempIndexd >mergeadres.getFirstRow()) {
                  this.DesignTemp=iSheet.getRow(mergRow).getCell(cIndex).toString();
                }

                if (adString.contains("расчетная") && tempIndexu<= mergeadres.getFirstRow()&& tempIndexd >mergeadres.getFirstRow()) {
                    this.DesignTemp=iSheet.getRow(mergRow).getCell(cIndex).toString();
                }
            }
            */
        }
}
	 public void replaceCat(String wbk, String sName) throws IOException {
		 XSSFWorkbook wb;
		 FileOutputStream newStr = null;
		 FileInputStream src=null;
		try {
			src=new FileInputStream(wbk);
			this.createBackup(wbk);
			wb = new XSSFWorkbook(src);
			 this.getinfopart2(wb);
			 XSSFSheet sheet=wb.getSheet(sName);
			 sheet.getRow(this.katTPTCrow).getCell(this.katCol).setCellValue(this.newKat.toString().replace(".0", "")); 
			 System.out.println(this.katTPTC);
			 newStr=new FileOutputStream(wbk);
			 
			 wb.write(newStr);
			 wb.close();
			 newStr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (newStr!=null) {
				newStr.close();
			}
		}
		
		 
	 }
	 
	 public void getpaspnameANDkat(XSSFWorkbook wbk,String sheet, int row,int col) {
		 XSSFSheet sheet1=wbk.getSheet(sheet);
		 this.newKat=sheet1.getRow(row).getCell(col).toString();
	 }
	 
}
