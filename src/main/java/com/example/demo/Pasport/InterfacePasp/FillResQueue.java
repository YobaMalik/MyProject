package com.example.demo.Pasport.InterfacePasp;

import com.example.demo.Pasport.RowfTable;
import java.util.Queue;

public interface FillResQueue {
    void fillResultQueue(String aFile, Queue<RowfTable<String>> allTable);
}
