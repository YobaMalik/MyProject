package com.example.demo.PaspAddDoc;
import com.example.demo.Interface.IResultDocs;
import com.example.demo.Pasport.RowfTable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

@Service
public class GetInfoFSVOM  implements IResultDocs {
    ExecutorService newTask ;
    @Override
    public void createDocs(Map<String, ByteArrayOutputStream> FileInput,
                           Map<String, ByteArrayOutputStream> resultList,
                           ExecutorService newTask) throws IOException {
        this.newTask=newTask;
        this.createSOM(FileInput,resultList);
    }

    private void createSOM(Map<String, ByteArrayOutputStream> FileInput,
                           Map<String, ByteArrayOutputStream> somList) throws IOException {

        List<ThreadClass> taskList = new ArrayList<>();
        for (Map.Entry<String, ByteArrayOutputStream> entry : FileInput.entrySet()) {
            InputStream in = new ByteArrayInputStream(entry.getValue().toByteArray());
            taskList.add(new ThreadClass(entry.getKey(), in, somList));
            in.close();
        }

        try {
            newTask.invokeAll(taskList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

