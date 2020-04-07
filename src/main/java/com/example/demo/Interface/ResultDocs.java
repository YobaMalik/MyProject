package com.example.demo.Interface;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public interface ResultDocs {
        void createDocs(Map<String, ByteArrayOutputStream> fileInput, Map<String, ByteArrayOutputStream> resultList, ExecutorService newTask ) throws IOException;
}
