package com.example.demo.pasports;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

public class Filesearch { 
    public void findfileszulul(String fPath, String z, ArrayList<String> Filenames){
        File dir = new File(fPath);
        File[] matchfiles = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return true;
            }
        });
        for (int i = 0; i < matchfiles.length; i++) {

            if  (!matchfiles[i].isDirectory()) {
                if (matchfiles[i].toString().toLowerCase().endsWith(z) && matchfiles[i].toString().toLowerCase().indexOf("~")==-1) {
                    Filenames.add(matchfiles[i].getPath());
                }
            }
            else{
                findfileszulul(matchfiles[i].toString(),z,Filenames);
            }
        }
    }
}

