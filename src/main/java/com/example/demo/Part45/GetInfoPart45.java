package com.example.demo.Part45;

import com.example.demo.pasports.RowfTable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GetInfoPart45 {
    ExecutorService newTask = Executors.newFixedThreadPool(8);
    public void getInfo(Map<String, ByteArrayOutputStream> FileInput, Map<String, ByteArrayOutputStream> fileStengthCalc) throws IOException {
        Queue<RowfTable<String>> allTable1 = new ConcurrentLinkedQueue<>();
        List<ThreadTask> taskList = new ArrayList<>();
        for (Map.Entry<String, ByteArrayOutputStream> entry : FileInput.entrySet()) {
            InputStream in = new ByteArrayInputStream(entry.getValue().toByteArray());
            taskList.add(new ThreadTask(entry.getKey(), in,fileStengthCalc));
            in.close();
        }
        try {
            this.newTask.invokeAll(taskList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
