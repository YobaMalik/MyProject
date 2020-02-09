package com.example.demo.MyController;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public interface ZipFile {
    public default ZipOutputStream Zip(ConcurrentHashMap<String, ByteArrayOutputStream> SOMList) throws FileNotFoundException {
        ZipOutputStream zip=new ZipOutputStream(new FileOutputStream("C:\\Users\\Yoba\\Desktop\\свидетельства\\zp.zip"));


        SOMList.forEach((e,z)->{
            ZipEntry zEntry=new ZipEntry(e);
            try {
                zip.putNextEntry(zEntry);
                zip.write(z.toByteArray());
                zip.closeEntry();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        return zip;

    }
}
