package com.example.demo.OBRE;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.util.Iterator;
import java.util.List;

public interface GetTableNumber {
     default XWPFTable getTableIter(XWPFDocument testdoc, int part)  {
        XWPFTable sTable = null;

        //int desingpressure=0;
        Iterator<XWPFTable> iter = testdoc.getTablesIterator();
        while (iter.hasNext()) {
            XWPFTable newtable = iter.next();

            //find desing pressure and pipesize
            List<XWPFTableRow> rowiter = newtable.getRows();
            for (int i = 0; i < rowiter.size(); i++) {
                List<XWPFTableCell> cellIter = newtable.getRow(i).getTableCells();
                for (int j = 0; j < cellIter.size(); j++) {
                    if (newtable.getRow(i).getCell(j).getText().toLowerCase().contains("наименован") &&
                            newtable.getRow(i).getCell(j).getText().toLowerCase().contains("участк") && part ==3) {
                        sTable = newtable;
                    }

                    if (newtable.getRow(i).getCell(j).getText().toLowerCase().contains("наименование")&&
                            newtable.getRow(i).getCell(j).getText().toLowerCase().contains("параметра")&& part==2) {
                        sTable = newtable;
                    }
                   /*  if (newtable.getRow(i).getCell(j).getText().toLowerCase().indexOf("расчетные") != -1) {
                         desingpressure = j;
                     }*/
                }
            }
        }
        return sTable;
    }
}
