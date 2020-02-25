package com.example.demo.PaspAddDoc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface NewTableDoc {

    double CreateTable(Map<String, ByteArrayOutputStream> FileInput, Map<String, ByteArrayOutputStream> SOMlist) throws IOException;
}
