package com.example.demo.pasports;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface NewTable {
    double getTable(Map<String,ByteArrayOutputStream> FileInput) throws IOException;
}
