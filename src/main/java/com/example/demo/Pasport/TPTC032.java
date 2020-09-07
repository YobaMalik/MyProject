package com.example.demo.Pasport;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Iterator;
import java.util.Locale;

public interface TPTC032 {
	  default boolean checkmun(String str){
		 /*
	        if (str==null) return false;
	        return str.matches("^-?\\d+$");*/
		  try {
		        Double.parseDouble(str);
		        return true;
		    } catch (NumberFormatException e) {
		        return false;
		    }
	    }
	  default boolean checkfnip(Workbook excPasp) {
	        //boolean b = false;
	        if (excPasp.getNumberOfSheets() < 10) {
	          return true;
	        }
	        return false;
	    }
	 
	  default String calcCategory(String TPTCcode, double operPress, double designtTemp, double maxdn) {
		 int  paspCat=0;
		 String dApp=null;
		 if (TPTCcode.toLowerCase().equals("г1")) {
			 if (maxdn <= 100.0 & maxdn>25.0) {
				 if ((operPress*maxdn<=100.0 & operPress >1.0 & operPress <=3.5) ||
						 (operPress >0.05 & operPress<=1.0) )
				 {
					 paspCat=1;
				 }
			 }
			 
			 if ((maxdn>100.0 & maxdn<=350.0 & operPress > 0.05 & operPress<=1.0)||
					 (maxdn>25.0 & maxdn<=350.0 & operPress*maxdn>100.0 &
							 operPress*maxdn <=350.0 & operPress > 1.0 & operPress<=3.5)||
					 (maxdn>25.0 & maxdn<=100.0 & operPress > 3.5) ) {
				 paspCat=2;
			 }
			 
			 if ((maxdn>350.0  & operPress > 0.05 & operPress<=1.0)||
					 (maxdn>100.0 & maxdn<=350.0 & operPress*maxdn>350.0 & operPress>1.0 & operPress<=3.5)||
					 ( maxdn>100.0 & operPress > 3.5) ) {
				 paspCat=3;
			 }
			 
			 if (operPress<=0.05&& maxdn<=32.0&& maxdn*operPress<=100.0) {
				 dApp="не распространяется";
			 }
			 
		 }

		 if (TPTCcode.toLowerCase().equals("г2")) {
			 if ((maxdn>32.0 & operPress > 0.05 & operPress<=3.2 & operPress*maxdn>100.0 &
					 operPress*maxdn<=350.0)||
					 (maxdn>32.0 & maxdn<=100.0 & operPress>3.2) ) {
				 paspCat=1;
			 }
			 
			 if ((maxdn>100.0 & operPress > 0.05 & operPress<=3.2 & operPress*maxdn>350.0 &
					 operPress*maxdn<=500.0)||
					 (maxdn>100.0 & maxdn<=250.0 & operPress>3.2) ) {
				 paspCat=2;
			 }
			 if ((maxdn>250.0 & operPress>3.2)||
					 (maxdn>250.0 & operPress*maxdn>500.0 & operPress>0.05&operPress<=3.2) ) {
				 paspCat=3;
			 }
			 
			 if (operPress<=0.05&& maxdn<=25.0&& maxdn*operPress<=200.0) {
				 dApp="не распространяется";
			 }
		 }
		 
		 if (TPTCcode.toLowerCase().equals("ж1")) {
			if (maxdn>25.0 & operPress > 0.05 & operPress<=1 & maxdn*operPress>200){
				 paspCat=1;	 
			 }
			if ((maxdn>25.0 & operPress > 1.0 & operPress<=8.0 & maxdn*operPress>200)||
					(maxdn>25.0 & operPress > 8.0 & operPress<=50.0 & maxdn*operPress>350)){
				 paspCat=2;	 
			 }
			if (maxdn>25.0 & operPress > 50.0){
				 paspCat=3;	 
			 }
			
			 if (operPress<=0.05 && maxdn<=25.0 && maxdn*operPress<=200.0) {
				 dApp="не распространяется";
			 }
		 }
		 
		 if (TPTCcode.toLowerCase().equals("ж2")) {
			 if (maxdn>200.0 & operPress > 1.0 & operPress<=50.0 & operPress*maxdn>500.0 ) {
				 paspCat=1;
			 }
			 if (maxdn>200.0 &  operPress>50.0) {
				 paspCat=2;
			 }
			 
			 if (operPress<=1.0&& maxdn<=200.0&& maxdn*operPress<=500.0) {
				 dApp="не распространяется";
			 }
		 }
		 
		 
		 if (paspCat<4 && designtTemp>400) {
			 paspCat++;
		 }
		 
		 if(dApp!=null) {
			 return dApp;
		 }else		 
		 return Integer.toString(paspCat);
	 }
	  default void calculateTPTC(String filePath, String eList, int rowN, int TPTRcol, int opCol,
								 int dtCol, int MDCol) throws ParseException {
		 try {
		 XSSFWorkbook testwb=new XSSFWorkbook(new FileInputStream(new File(filePath)));
	     XSSFSheet shit=testwb.getSheet(eList);
	     int lastcolumn=0;
	     Iterator<Row> iter=shit.iterator();
	     while (iter.hasNext()) {
	    	 Row sRow=iter.next();
	    	 if (sRow.getLastCellNum()>lastcolumn) {
	    		 lastcolumn= sRow.getLastCellNum();
	    	 }
	     }
	    int firstrow=shit.getRow(rowN).getFirstCellNum();
	     for (int i=firstrow;i<shit.getRow(rowN).getLastCellNum();i++) {

	    		 if (shit.getRow(i)!=null&& shit.getRow(i).getCell(TPTRcol)!=null &&shit.getRow(i)
						 .getCell(opCol)!=null&& shit.getRow(i).getCell(dtCol)!=null
	    				 && shit.getRow(i).getCell(MDCol)!=null) {
	     	String TPTCcode=shit.getRow(i).getCell(TPTRcol).toString();
	     	double d=this.parseNumb(shit.getRow(i).getCell(opCol).toString()).doubleValue();
	    	double designtTemp=	this.parseNumb((shit.getRow(i).getCell(dtCol).toString())).doubleValue();
	     	double maxdn=this.parseNumb((shit.getRow(i).getCell(MDCol).toString())).doubleValue();
	     	String cat=this.calcCategory(TPTCcode, d, designtTemp, maxdn);
	     	shit.getRow(i).createCell(60).setCellValue(cat);
	    		 }


	     }
	    testwb.write(new FileOutputStream(new File(filePath+".xlsx")));
	    testwb.close();
		 } catch (IOException e) {
			 e.printStackTrace();
		 } finally {

		 }
	 }
	 default Number parseNumb(String numb) throws ParseException {
		 NumberFormat format=NumberFormat.getInstance(Locale.FRANCE);
		 Number number=format.parse(numb);
		return number;
	 }
	 
}