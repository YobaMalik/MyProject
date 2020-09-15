package com.example.demo.Pasport;

import java.io.IOException;
import java.io.InputStream;
import java.util.Queue;
import java.util.concurrent.Callable;

public class MyTask implements Callable<Void> {
    private InputStream fFile;
    private final String filename;
    private Queue<RowfTable<String>> allTable;
    public MyTask(String filename, InputStream fFile, Queue<RowfTable<String>> allTable1){
        this.allTable=allTable1;
        this.fFile=fFile;
        this.filename=filename;
    }
		public Void call() throws IOException {
            ExtractPasportData newDocument = new ExtractPasportData(fFile);
            newDocument.fillResultQueue(filename, allTable); //  write in (file and result arraylist) info from pasp
            System.out.println(filename + " Done");
            return null;

        }
    }

