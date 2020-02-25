package com.example.demo.OBRE;
import com.example.demo.pasports.CategoryCalc;
import com.example.demo.pasports.RowfTable;
import org.apache.poi.ss.usermodel.Workbook;
import java.util.Queue;

public class PaspInfo extends CategoryCalc {
    @Override
    public void calc(Workbook excPasp, String aFile, Queue<RowfTable<String>> allTable) {
        super.getinfopart2(excPasp);
        super.calc(excPasp, aFile, allTable);
    }
}
