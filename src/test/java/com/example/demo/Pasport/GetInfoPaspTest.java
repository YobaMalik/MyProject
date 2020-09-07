package com.example.demo.Pasport;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.Assert;
import static org.junit.jupiter.api.Assertions.*;

class GetInfoPaspTest extends Assert {
    Map<String, ByteArrayOutputStream> expected=new HashMap<>();
    Map<String, ByteArrayOutputStream> actual=new HashMap<>();
    GetInfoPasp test=new GetInfoPasp();
    public GetInfoPaspTest() throws IOException {
        ExecutorService newTask = Executors.newFixedThreadPool(8);
        this.getExpected();
        test.createDocs(this.fillMap(),this.actual, newTask);
        newTask.shutdown();
    }

    private  Map<String, ByteArrayOutputStream> fillMap() throws IOException {
        Map<String, ByteArrayOutputStream> fileArray = new HashMap<>();

        String path="C:\\Users\\Yoba\\Desktop\\Паспорта по полиэтилену 18.10.2019";
        System.out.println(path);
        File[] fls = new File(path).listFiles();

        for (int i = 0; i < fls.length; i++) {
            ByteArrayInputStream bin = new ByteArrayInputStream(FileUtils.readFileToByteArray(fls[i]));
            ByteArrayOutputStream bot = new ByteArrayOutputStream();
            int count = 0;
            byte[] bts = new byte[8192];
            while ((count = bin.read(bts)) != -1) {
                bot.write(bts, 0, count);
            }
            fileArray.put(fls[i].getName(), bot);
        }
        return fileArray;
    }
    private void getExpected() throws IOException {
        Map<String, ByteArrayOutputStream> expected=new HashMap<>();
        ByteArrayInputStream instr= new ByteArrayInputStream(FileUtils.readFileToByteArray(new File("C:\\Users\\Yoba\\Desktop\\с мобилы\\ResultTable.xlsx")));
        ByteArrayOutputStream outstr=new ByteArrayOutputStream();
        int count = 0;
        byte[] bts = new byte[8192];
        while ((count = instr.read(bts)) != -1) {
            outstr.write(bts, 0, count);
        }
        this.expected.putIfAbsent("ResultTable.xlsx",outstr);
    }

    @Test
    void getMap() {
        Assert.assertEquals(expected,test.getMap());
    }
}