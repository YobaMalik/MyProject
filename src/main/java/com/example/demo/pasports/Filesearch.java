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

    void  fserandcru(String fPath, String newP, String zulul, String filename) {
        //long time = System.currentTimeMillis();
        ArrayList<String> Filenames = new ArrayList<>();
        if (filename.length()==0){
            filename="KAMA_THE_BULLET";
        }
        try {
            findfileszulul(fPath,zulul, Filenames);
            File listoffiles=new File(newP + "\\" + filename + ".txt");
            System.out.println(newP);
            System.out.println(filename);
            listoffiles.createNewFile();
            FileWriter wr=new FileWriter(listoffiles);
            for (String fName : Filenames) {
                wr.write(fName);
                wr.append("\r\n");

            }
            wr.flush();
            wr.close();
        }
        catch(NullPointerException e){
            System.out.print("Empty");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    
   
}

