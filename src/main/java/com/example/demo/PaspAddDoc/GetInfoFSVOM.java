package com.example.demo.PaspAddDoc;

import com.example.demo.pasports.Callableclass;
import com.example.demo.pasports.CategoryCalc;
import com.example.demo.pasports.GetInfoPasp;
import com.example.demo.pasports.RowfTable;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class GetInfoFSVOM  extends GetInfoPasp   {

    public double runS(HashMap<String, ByteArrayOutputStream> FileInput, ConcurrentHashMap<String, ByteArrayOutputStream> SOMlist) throws IOException, ParseException, InvalidFormatException {

        long starttime = System.currentTimeMillis();
        this.createSOM(FileInput,SOMlist);
        System.out.println((double) System.currentTimeMillis() / 60000 - (double) starttime / 60000);
        return (double) System.currentTimeMillis() / 60000 - (double) starttime / 60000;
    }

    private void createSOM(HashMap<String, ByteArrayOutputStream> FileInput, ConcurrentHashMap<String, ByteArrayOutputStream> SOMList) throws IOException {
        ConcurrentLinkedQueue<RowfTable<String>> allTable1 = new ConcurrentLinkedQueue<>();
        List<ThreadClass> tasklist = new ArrayList<>();
        for (Map.Entry<String, ByteArrayOutputStream> entry : FileInput.entrySet()) {
            InputStream in = new ByteArrayInputStream(entry.getValue().toByteArray());
            tasklist.add(new ThreadClass(entry.getKey(), in, allTable1,SOMList));
            in.close();
        }
        ExecutorService ntask = Executors.newFixedThreadPool(8);
        try {

            ntask.invokeAll(tasklist);
            ntask.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            ntask.shutdown();
        }
    }
}

