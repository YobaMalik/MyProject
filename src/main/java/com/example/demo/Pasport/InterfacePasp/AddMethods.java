package com.example.demo.Pasport.InterfacePasp;

import org.apache.poi.ss.usermodel.Workbook;

public interface AddMethods {
    default boolean checkFNIP(Workbook excPasp) {
        return excPasp.getNumberOfSheets() < 10;
    }

    default boolean checkNum(String str){
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
}
