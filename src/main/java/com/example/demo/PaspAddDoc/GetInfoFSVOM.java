package com.example.demo.PaspAddDoc;
import com.example.demo.pasports.NewTable;
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

public class GetInfoFSVOM  implements NewTableDoc {

    @Override
    public double CreateTable(Map<String, ByteArrayOutputStream> FileInput, Map<String, ByteArrayOutputStream> SOMlist) throws IOException {
        long startTime = System.currentTimeMillis();
        this.createSOM(FileInput,SOMlist);
        System.out.println((double) System.currentTimeMillis() / 60000 - (double) startTime / 60000);
        return (double) System.currentTimeMillis() / 60000 - (double) startTime / 60000;
    }

    private void createSOM(Map<String, ByteArrayOutputStream> FileInput, Map<String, ByteArrayOutputStream> SOMList) throws IOException {
       Queue<RowfTable<String>> allTable1 = new ConcurrentLinkedQueue<>();
        List<ThreadClass> taskList = new ArrayList<>();
        for (Map.Entry<String, ByteArrayOutputStream> entry : FileInput.entrySet()) {
            InputStream in = new ByteArrayInputStream(entry.getValue().toByteArray());
            taskList.add(new ThreadClass(entry.getKey(), in, allTable1,SOMList));
            in.close();
        }

        ExecutorService newTask = Executors.newFixedThreadPool(8);
        try {
            newTask.invokeAll(taskList);
            newTask.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            newTask.shutdown();
        }
    }


}

