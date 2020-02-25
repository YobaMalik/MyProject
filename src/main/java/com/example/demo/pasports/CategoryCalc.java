package com.example.demo.pasports;

import com.example.demo.PaspAddDoc.SvOmont;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;


public class CategoryCalc implements TestInt{
	
   // private double OperPress;
    //private double OperTemp;
    private int otbThikn = -1;
   // private int posnumber = -1;
    private int diams = -1;
    private int linesANDzone = -1;
    private int lastcell = -1;
    private int lastLAZcell = -1;
    private int lastRow = -1;
    private int[] params = new int[4];
    private String pipename;
    private String Fluidcode;
    private String Hazardcode;
    private String Exphazard;
    private String groupGOST;
    private String katTPTC;
    private String katGOST;
    private String Corrosionrate;
    private String OperationPressure;
    private String OperationTemp;
    private String DesignTemp;
    private String weldInfo;
    private double MaXDN;
    private String DWGs;
    private String aFile;
    private String heatTreatment;
    private String DesingPressure;
	private List<String> Pipeline=new ArrayList<>();;
	private String TestPressHydro;
	private String TestPressPnevmo;
	private String nameTitul;
	private String FactoryName;
	private String leaktest;
	private String minTemp;
    private String desL;
    private String billP;
    private String numbOS;
    private String groupTPTC;
    public List<String> getPipeline(){
        return this.Pipeline;
    }
    public String getaFile(){return this.aFile;}
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
    public String getleaktest() {
        return this.leaktest;
    }
	
	public String getFactoryName() {
		  return this.FactoryName;
	  }
	public String getnameTitul() {
		  return this.nameTitul;
	  }
	
	 public String getTestPressHydro() {
		  return this.TestPressHydro;
	  }
	 public String getTestPressPnevmo() {
		  return this.TestPressPnevmo;
	  }
	
  public String getOperationTemp() {
	  return this.OperationTemp;
  }
  public String getDesingPressure() {
	  return this.DesingPressure;
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

    public String getFluidcode() {
        return this.Fluidcode;
    }

    public String getHazardcode() {
        return this.Hazardcode;
    }

    public String getExphazard() {
        return this.Exphazard;
    }

    public String getgroupGOST() {
        return this.groupGOST;
    }

    public String getkatTPTC() {
        return this.katTPTC;
    }

    public String getkatGOST() {
        return this.katGOST;
    }

    public String getCorrosionrate() {
        return this.Corrosionrate;
    }

    public String getOperationPressure() {
        return this.OperationPressure;
    }

    public String getDesignTemp() {
        return this.DesignTemp;
    }

    public String getmintemp() {
        return this.minTemp;
    }
    public void getNameTitul(Workbook excPasp)  {

            Sheet iSheet = null;
            //FormulaEvaluator evaluator1 = excPasp.getCreationHelper().createFormulaEvaluator();

            int j;
            for(j = 0; j < excPasp.getNumberOfSheets(); ++j) {
                if (excPasp.getSheetName(j).equals("Sheet1")) {
                    iSheet = excPasp.getSheetAt(j);
                    break;
                }
            }
            if (iSheet != null) {
                CellRangeAddress mergeadres;
                int mergRow;
                int mergCol;
                String adString;
                for(j = 0; j < iSheet.getNumMergedRegions(); ++j) {
                    mergeadres = iSheet.getMergedRegion(j);
                    mergRow = mergeadres.getFirstRow();
                    mergCol = mergeadres.getFirstColumn();
                    adString = iSheet.getRow(mergRow).getCell(mergCol).toString().toUpperCase();
                    if (adString.contains("ПАСПОРТ")  && adString.contains("ТРУБОПРОВОДА")&& adString.contains("№")) {
                       String aStr=adString.replace(" ", "").replaceAll("\n", "");   
                       aStr=aStr.replaceAll("ПАСПОРТТРУБОПРОВОДА", "");
                       aStr=aStr.replace("№", "");
                       //String[] splt=aStr.split("№");
                       this.nameTitul=aStr; //splt[splt.length-1];
                       
                        }
                    }
                }
            }
    
    public void getinfopart2(Workbook excPasp)  {
    	
        int cIndex = 0;
        int tempIndexu=0;
        int tempIndexd=0;
        CellRangeAddress tempPress = null;

            Sheet iSheet = null;
            FormulaEvaluator evaluator1 = excPasp.getCreationHelper().createFormulaEvaluator();

            int j;
            for(j = 0; j < excPasp.getNumberOfSheets(); ++j) {
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
                for(j = 0; j < iSheet.getNumMergedRegions(); ++j) {
                    mergeadres = iSheet.getMergedRegion(j);
                    mergRow = mergeadres.getFirstRow();
                    mergCol = mergeadres.getFirstColumn();
                    adString = iSheet.getRow(mergRow).getCell(mergCol).toString().toLowerCase();
                    if (adString.contains("наименование")  && adString.contains("предприятия") ) {
                        for(int i = mergCol + 1; i < iSheet.getRow(mergRow).getLastCellNum(); ++i) {
                            CellType checkType = iSheet.getRow(mergRow).getCell(i).getCellType();
                            if (checkType != CellType.BLANK) {
                                cIndex = i;
                                break;
                            }
                        }
                    }
                }

                for(j = 0; j < iSheet.getNumMergedRegions(); ++j) {
                    mergeadres = iSheet.getMergedRegion(j);
                    mergRow = mergeadres.getFirstRow();
                    mergCol = mergeadres.getFirstColumn();
                    adString = iSheet.getRow(mergRow).getCell(mergCol).toString().toLowerCase();
                  
                    if (iSheet.getRow(mergRow).getCell(cIndex)!=null) {
                        if (adString.contains("наименование") && adString.contains("трубопровода")) {
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
                                if  (corRate.getStringValue()!=null) {
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
                       
                    }
                }
                for (j=tempPress.getFirstRow();j<tempPress.getLastRow();j++) {
                	CellType checkType = iSheet.getRow(j).getCell(cIndex).getCellType();
                	if (j!=tempPress.getFirstRow() &&checkType != CellType.BLANK ) {
                	
                		this.DesingPressure=iSheet.getRow(j).getCell(cIndex).toString();
                	}
                }
                for(j = 0; j < iSheet.getNumMergedRegions(); ++j) {
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
            }

    }

    public void calc(Workbook excPasp, String aFile, Queue<RowfTable<String>> allTable)  {
   //     System.out.println(this.diams+" "+ this.linesANDzone +" "+ this.lastcell +" "+ this.lastLAZcell);
        this.aFile=aFile;
    	this.getinfopart4(excPasp);
    	this.getDWG(excPasp);
    	this.getNameTitul(excPasp);
    	this.getinfopart6(excPasp);

        int tempIndex = 0;
            Sheet iSheet = null;
  
            FormulaEvaluator evaluator1 = excPasp.getCreationHelper().createFormulaEvaluator();
            for(int i = 0; i < excPasp.getNumberOfSheets(); ++i) {	
                if (excPasp.getSheetName(i).trim().equals("3")) {
                    iSheet = excPasp.getSheetAt(i);
                    break;
                }
            }
     
         
            if (!this.checkfnip(excPasp, iSheet) && iSheet != null) { 
                HashMap<String, String> part51 =new HashMap<>();
                
                part51=this.getPipeInfo(excPasp);
           
                this.getMAXdn(part51,excPasp);
               
               // int adINT = 0;

                for(int adINT = 0; adINT < iSheet.getNumMergedRegions(); ++adINT) {
               
                    CellRangeAddress mergeadres = iSheet.getMergedRegion(adINT);
    
                    int mergRow = mergeadres.getFirstRow();
                    int mergCol = mergeadres.getFirstColumn();
                    String adString = iSheet.getRow(mergRow).getCell(mergCol).toString().toLowerCase();
                   
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
/*/*
                    if (adString.contains("участков") && adString.contains("п/п")) {
                        this.posnumber = mergeadres.getFirstColumn();
                        
                    }*/

                    if (adString.contains("перечень схем")) {
                        this.lastRow = mergeadres.getFirstRow();
                    } else{
                        this.lastRow=iSheet.getLastRowNum();
                    }
                   
                }
              


                CellType checkType;
                for(int adINT = this.linesANDzone; adINT <= this.lastcell; adINT++) {
                    checkType = iSheet.getRow(this.lastLAZcell).getCell(adINT).getCellType();
                    if (checkType != CellType.BLANK && adINT > this.linesANDzone && adINT < this.diams) {
                        this.params[tempIndex] = adINT;
                        ++tempIndex;
                    }
                }
   
                for(int adINT = this.lastRow; adINT >= 0; adINT--) {
                    if (iSheet.getRow(adINT) != null && iSheet.getRow(adINT).getCell(this.diams) != null) {
                        checkType = iSheet.getRow(adINT).getCell(this.diams).getCellType();
                        if (checkType != CellType.BLANK && this.lastRow == -1) {
                            this.lastRow = adINT;
                            
                            break;
                        }
                    }
                }
              
            	
                if (this.diams != -1 && this.linesANDzone != -1 && this.lastcell != -1 && this.lastLAZcell != -1) {
                
                    int adINT = -1;

                    for(int i = this.lastLAZcell; i <= this.lastRow + 1; ++i) {

                        if (iSheet.getRow(i) != null && iSheet.getRow(i).getCell(this.diams) != null && iSheet.getRow(i).getCell(this.diams).toString().length()>0) {
                        /*	System.out.println(Integer.toString(i+1)+" далее строка "+iSheet.getRow(i).getCell(this.linesANDzone).getCellType().toString()+
                        			" длина "+iSheet.getRow(i).getCell(this.linesANDzone).toString().length());
*/
                           // Row newrow = newshit.createRow(check);
                            CellType checkType1 = iSheet.getRow(i).getCell(this.diams).getCellType();
                            if (checkType1 != CellType.BLANK) {
                            	
                            	
                            	
                                RowfTable<String> singleRow=new RowfTable<>();
                                CellValue newvalue = evaluator1.evaluate(iSheet.getRow(i).getCell(this.lastcell));
                               

                                CellType cType = iSheet.getRow(i).getCell(this.linesANDzone).getCellType();
                                if (cType != CellType.BLANK && iSheet.getRow(i).getCell(this.linesANDzone).toString().length()>0) {
                               
                                	this.Pipeline.add(iSheet.getRow(i).getCell(this.linesANDzone).toString());
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
                                singleRow.addValue(8,this.getpipename());
                                singleRow.addValue(9,this.getHazardcode());
                                singleRow.addValue(10,this.getFluidcode());
                                singleRow.addValue(11,this.getExphazard());
                                singleRow.addValue(12,this.getgroupGOST());
                                singleRow.addValue(13,this.getkatTPTC());
                                singleRow.addValue(14,this.getkatGOST());
                                singleRow.addValue(15,this.getCorrosionrate());
                                singleRow.addValue(16,this.aFile);
                                singleRow.addValue(17,this.getOperationPressure());
                                singleRow.addValue(18,this.getDesingPressure());
                                singleRow.addValue(19,this.getOperationTemp());
                                singleRow.addValue(20,this.getDesignTemp());
                                singleRow.addValue(21,"");
                                singleRow.addValue(22,"");
                                singleRow.addValue(21,this.getDWGs());
                                singleRow.addValue(22,this.getweldInfo());
                                singleRow.addValue(23, this.getTestPressHydro());
                                singleRow.addValue(24, this.getTestPressPnevmo());
                               // singleRow.addValue(25, this.getleaktest());
                                singleRow.addValue(25, this.getFactoryName());
                                if (this.getnameTitul()!=null) {
                                	singleRow.addValue(26,this.getnameTitul());
                                } else {
                                	singleRow.addValue(26,"");
                                }
                                
                                singleRow.addValue(27, Double.toString(this.MaXDN));
                          //   String test123=iSheet.getRow(i).getCell(this.diams).toString();
                           
                            
                                if ( part51.containsKey(iSheet.getRow(i).getCell(this.diams).toString())){
                                    singleRow.addValue(28, part51.get(iSheet.getRow(i).getCell(this.diams).toString()));
                                } else {
                                	singleRow.addValue(28,"проверка Z");
                                }
                                
                             
                                singleRow.addValue(29,this.getmintemp());
                                singleRow.addValue(30, this.getbillP());
                                singleRow.addValue(31, this.getdesL());
                                singleRow.addValue(32, this.getnumbOS());
                                singleRow.addValue(33, this.getgroupTPTC());
                               // System.out.println(Double.toString(this.MaXDN));
                               /* if (part51.containsKey(iSheet.getRow(i).getCell(this.diams).toString())) {
                                    singleRow.addValue(19,part51.get(iSheet.getRow(i).getCell(this.diams).toString()));
                                }*/
                                allTable.add(singleRow);
                                
                            }
                        }
                    }
                }
            }
            else {
            	 RowfTable<String> singleRow=new RowfTable<>();
            	 for (int i=0;i<26;i++) {
            		
            	if (i==16) { 	 singleRow.addValue(16,this.aFile);
            	 } else {
            		 singleRow.addValue(i,"");  
            	 }
            	 }
            	 singleRow.addValue(26,this.getnameTitul());
            	 
            	 allTable.add(singleRow);
            }
            
        }


    public HashMap<String, String>  getPipeInfo(Workbook excPasp)  {
    	HashMap<String, String> pipeadnmaterial=new HashMap<> ();

    	Sheet iSheet = null;

        
            for(int diam = 0; diam < excPasp.getNumberOfSheets(); ++diam) {
                if (excPasp.getSheetName(diam).equals("5.1")) {
                    iSheet = excPasp.getSheetAt(diam);
                    break;
                }
            }

            if (iSheet != null) {
                int diam = 0;
                int material = 0;
                int firstrow = 0;

                int i;
                for(i = 0; i < iSheet.getNumMergedRegions(); ++i) {
                    CellRangeAddress mergeadres = iSheet.getMergedRegion(i);
                    int mergRow = mergeadres.getFirstRow();
                    int mergCol = mergeadres.getFirstColumn();
                    String adString = iSheet.getRow(mergRow).getCell(mergCol).toString().toLowerCase();
                    if (adString.contains("диаметр")  && adString.contains("толщина") ) {
                        diam = mergeadres.getFirstColumn();
                        firstrow = mergeadres.getLastRow();
                    }

                    if (adString.contains("марка")  && adString.contains("стали") ) {
                        material = mergeadres.getFirstColumn();
                    }
                }
       
                for(i = firstrow + 1; i < iSheet.getLastRowNum(); ++i) {
                    if (iSheet.getRow(i) != null && iSheet.getRow(i).getCell(diam) != null) {
                        CellType checkType = iSheet.getRow(i).getCell(diam).getCellType();
                        if (checkType != CellType.BLANK) {
                            CellType checkTyp = iSheet.getRow(i).getCell(diam).getCellType();
                            if (checkTyp != CellType.BLANK) {
                                pipeadnmaterial.put(iSheet.getRow(i).getCell(diam).toString(), iSheet.getRow(i).getCell(material).toString());
                            }
                        }
                    }
                }
            }
       return pipeadnmaterial;            
    }

    public void getMAXdn(HashMap<String,String> getPipeInfo, Workbook wbk) {
       
        double maxDN = 0;
        HashMap<String,String> pipesize=new HashMap<>();
        pipesize=getPipeInfo;
       
        for(Map.Entry<String,String> entry:pipesize.entrySet()) {

            String aStr =  (entry.getKey()).trim().toLowerCase().replaceAll("х", "x");
            aStr = aStr.replaceAll(",", ".");
            String[] diams = aStr.split("x");
            
            if (this.checkmun(diams[0]) && Double.parseDouble(diams[0]) > maxDN) {
                maxDN = Double.parseDouble(diams[0]);
            }  
            
        }
        Sheet iSheet = null;
        int iDN = -1;
        int firstcell = -1;

      
        for(int i = 0; i < wbk.getNumberOfSheets(); ++i) {
            if (wbk.getSheetName(i).contains("5.4")) {
                iSheet = wbk.getSheetAt(i);
                break;
            }
        }

        for(int i = 0; i < iSheet.getNumMergedRegions(); ++i) {
            CellRangeAddress mergeadres = iSheet.getMergedRegion(i);
            int mergRow = mergeadres.getFirstRow();
            int mergCol = mergeadres.getFirstColumn();
            String adString = iSheet.getRow(mergRow).getCell(mergCol).toString().toLowerCase();
            if (adString.contains("наружный") && adString.contains("диаметр") ) {
                iDN = mergeadres.getFirstColumn();
                firstcell = mergeadres.getLastRow();
            }
        }
       
        for(int i = firstcell; i < iSheet.getLastRowNum(); ++i) {
            if (iSheet.getRow(i) != null&& iSheet.getRow(i).getCell(iDN)!=null) {
                CellType checkType = iSheet.getRow(i).getCell(iDN).getCellType();
                if (checkType != CellType.BLANK) {
                    iSheet.getRow(i).getCell(iDN).getCellType();
                    String str = iSheet.getRow(i).getCell(iDN).toString();
                    if (str.length() > 2) {
                        str = str.replaceAll(",", ".");
                        str = str.replaceAll("х", "x");
                        str = str.replaceAll("/", "x");
                        str = str.replaceAll("-", "x");
                        String[] diams = str.split("x");
                        for (int j=0;j<diams.length;j++){
                            if (checkmun(diams[j]) && Double.parseDouble(diams[j]) > maxDN) {
                                maxDN = Double.parseDouble(diams[j]);
                            }
                        }
                    
                    }
                }
            }
        }
       // pipesize.clear();
         this.MaXDN=maxDN;
    }
/*
    private boolean checkmun(String str){
        if (str==null) return false;
        return str.matches("^-?\\d+$");

    }
    */
    public void getinfopart4(Workbook excPasp) {
    	 Sheet iSheet = null;
    	 int cIndex = 0;

             //FormulaEvaluator evaluator1 = excPasp.getCreationHelper().createFormulaEvaluator();

             int j;
             for(j = 0; j < excPasp.getNumberOfSheets(); ++j) {
                 if (excPasp.getSheetName(j).equals("4")) {
                     iSheet = excPasp.getSheetAt(j);
                     break;
                 }
             }

             if (!this.checkfnip(excPasp, iSheet)&&iSheet != null) {
                 CellRangeAddress mergeadres;
                 int mergRow=20;
                 int mergCol=-1;
                 //int firstrow=0;
                 String adString = null;
                 for(j = 0; j < iSheet.getNumMergedRegions(); ++j) {
                     mergeadres = iSheet.getMergedRegion(j);
                    int mergRow1 = mergeadres.getFirstRow();
                    int mergCol1 = mergeadres.getFirstColumn();
                     adString = iSheet.getRow(mergRow1).getCell(mergCol1).toString().toLowerCase();
                     if (adString.contains("номера")  && adString.contains("чертежей") ) {
                         mergCol=mergCol1;
                        if(mergRow>mergRow1) mergRow=mergRow1;
                     }
                 }
                 if (mergCol!=-1) {
                 for(int i = mergCol + 1; i < iSheet.getRow(mergRow).getLastCellNum(); ++i) {
                     CellType checkType = iSheet.getRow(mergRow).getCell(i).getCellType();
                     if (checkType != CellType.BLANK) {
                         cIndex = i;
                         break;
                     }
                 }
                 }
                 
                 for(j = 0; j < iSheet.getNumMergedRegions(); ++j) {
                     mergeadres = iSheet.getMergedRegion(j);
                     mergRow = mergeadres.getFirstRow();
                     int mergCol2 = mergeadres.getFirstColumn();
                     
                     if (iSheet.getRow(mergRow).getCell(cIndex)!=null && iSheet.getRow(mergRow).getCell(mergCol2)!=null) {
                    	 adString = iSheet.getRow(mergRow).getCell(mergCol2).toString().toLowerCase();
                         if (adString.contains("монтаже") && adString.contains("трубопровода") &&adString.contains("сварки")) {
                             this.weldInfo = iSheet.getRow(mergRow).getCell(cIndex).toString();
                         }
                     }
                     if (iSheet.getRow(mergRow).getCell(cIndex)!=null && iSheet.getRow(mergRow).getCell(cIndex)!=null) {
                         if (adString.contains("термообработке") && adString.contains("вид и режим")) {
                             this.heatTreatment = iSheet.getRow(mergRow).getCell(cIndex).toString();
                         }
                     }
                 }
             /*    HashMap<String,String> ISOlist=new HashMap<>();

                 for (int i=0;i<=iSheet.getLastRowNum(); i++) {
                	if (iSheet.getRow(i)!=null&&iSheet.getRow(i).getCell(firstrow)!=null) {
                	 adString = iSheet.getRow(i).getCell(firstrow).toString().toLowerCase();

                	 if (iSheet.getRow(i).getCell(cIndex)!=null && adString.contains("номера")  && adString.contains("чертежей") ) {
                        adString=iSheet.getRow(i).getCell(cIndex).toString().replaceAll("\n", "");
                         adString=adString.replaceAll(" ","");
                         adString=adString.replaceAll(" ",";");
                         String[] DWGlst=adString.split(";");
                         for (int z=0;z<DWGlst.length;z++){
                             if (DWGlst[z]!=null){
                                 ISOlist.put(DWGlst[z],DWGlst[z]);
                             }
                         }
                     }
                 }
                 }
                 StringBuilder teststringbilder=new StringBuilder();
                 TreeMap<String,String> testTree=new TreeMap<>(ISOlist);
                 for (Map.Entry<String,String> entry: testTree.entrySet()) {
                     //System.out.println(entry.getValue());
                     teststringbilder.append(entry.getValue().toString());
                     teststringbilder.append(";");
                 }
                 StringBuilder teststringbilder=new StringBuilder();
                 ISOlist.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey))
                 .forEach(e->{
                     teststringbilder.append(e.getValue());
                     teststringbilder.append(";");
                 });
                 for(Map.Entry<String,String> entry:ISOlist.entrySet()){
                     teststringbilder.append(entry.getValue().toString());
                     teststringbilder.append(";");
                     System.out.println(entry.getValue());
                 }

                 this.DWGs=teststringbilder.toString();
                // System.out.println(teststringbilder);
*/
             }
        	 
    }
    
    public void getDWG(Workbook excPasp) {
   	 Sheet iSheet = null;
   	 int cIndex = 0;

            //FormulaEvaluator evaluator1 = excPasp.getCreationHelper().createFormulaEvaluator();

            int j;
            for(j = 0; j < excPasp.getNumberOfSheets(); ++j) {
                if (excPasp.getSheetName(j).equals("4")) {
                    iSheet = excPasp.getSheetAt(j);
                    break;
                }
            }

            if (!this.checkfnip(excPasp, iSheet)&&iSheet != null) {
            	
                CellRangeAddress mergeadres;
                int mergRow=20;
                int mergCol=-1;
                int lastrowDWG = 0;
                String adString = null;
         
         
                for(j = 0; j < iSheet.getNumMergedRegions(); ++j) {
                    mergeadres = iSheet.getMergedRegion(j);
                   int mergRow1 = mergeadres.getFirstRow();
                   int mergCol1 = mergeadres.getFirstColumn();
                    adString = iSheet.getRow(mergRow1).getCell(mergCol1).toString().toLowerCase();
                    if (adString.contains("номера")  && adString.contains("узловых") && adString.contains("чертежей") ) {
                        mergCol=mergCol1;
                       if(mergRow>mergRow1) mergRow=mergRow1;
                    }

                }
                
                for(j = 0; j < iSheet.getNumMergedRegions(); ++j) {
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
                         for (int z=0;z<DWGlst.length;z++){
                             if (DWGlst[z]!=null&& DWGlst[z].length()>2 ){
                                 ISOlist.put(DWGlst[z],DWGlst[z]);
                             }
                         }
                     }               	
                }
            
/*
                for (int i=0;i<=iSheet.getLastRowNum(); i++) {
               	if (iSheet.getRow(i)!=null&&iSheet.getRow(i).getCell(firstrow)!=null) {
               	 adString = iSheet.getRow(i).getCell(firstrow).toString().toLowerCase();

               	 if (iSheet.getRow(i).getCell(cIndex)!=null && adString.contains("номера")  && adString.contains("чертежей") ) {
                       adString=iSheet.getRow(i).getCell(cIndex).toString().replaceAll("\n", "");
                        adString=adString.replaceAll(" ",";");
                        String[] DWGlst=adString.split(";");
                        for (int z=0;z<DWGlst.length;z++){
                            if (DWGlst[z]!=null&& DWGlst[z].length()>2 ){
                                ISOlist.put(DWGlst[z],DWGlst[z]);
                            }
                        }
                    }
                }
                }
                
                
                mergRow++;
               

                while (iSheet.getRow(mergRow).getCell(mergCol)!=null && !iSheet.getRow(mergRow).getCell(mergCol).toString().contains("зготовитель")) {
                	// CellType checkType = iSheet.getRow(mergRow).getCell(cIndex).getCellType();
                	adString=iSheet.getRow(mergRow).getCell(cIndex).toString();
                	if (adString.length()>2) {
                    	 
                    	 adString=adString.replaceAll(" ","");
                    	 String[] DWGlst=adString.split(";");
                         for (int z=0;z<DWGlst.length;z++){
                             if (DWGlst[z]!=null&& DWGlst[z].length()>2 ){
                                 ISOlist.put(DWGlst[z],DWGlst[z]);
                             }
                         }
                             	 
                     }
                     mergRow++;
                }           */
                
                
                StringBuilder teststringbilder=new StringBuilder();
                ISOlist.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey))
                .forEach(e->{
                    teststringbilder.append(e.getValue());
                    teststringbilder.append(";");
                });
             
          

                this.DWGs=teststringbilder.toString();
            }
       	 
   }
    public void getinfopart6(Workbook excPasp){
        Sheet iSheet = null;

        for(int j = 0; j < excPasp.getNumberOfSheets(); ++j) {
            if (excPasp.getSheetName(j).equals("6")) {
                iSheet = excPasp.getSheetAt(j);
                break;
            }
        }
        if (iSheet != null) {
            int leaktestrow=0;
            CellRangeAddress mergeadres;
            int mergRow;
            int mergCol;
            String adString;
            for(int j = 0; j < iSheet.getNumMergedRegions(); ++j) {
                mergeadres = iSheet.getMergedRegion(j);
                mergRow = mergeadres.getFirstRow();
                mergCol = mergeadres.getFirstColumn();
                adString = iSheet.getRow(mergRow).getCell(mergCol).toString().toLowerCase();
                if (adString.contains("герметичность") ) {
                    leaktestrow=mergRow;
                }
            }

            adString="";
            for(int i = 0; i < iSheet.getRow(leaktestrow).getLastCellNum(); i++) {
                CellType checkType = iSheet.getRow(leaktestrow).getCell(i).getCellType();

                if (checkType != CellType.BLANK) {
                    adString = iSheet.getRow(leaktestrow).getCell(i).toString().toLowerCase();
                    if (!adString.contains("герметичность")||!adString.contains("испытаний")){
                        this.leaktest=iSheet.getRow(leaktestrow).getCell(i).toString();

                    }
                }
            }

        }
    }
}
