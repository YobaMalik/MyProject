package com.example.demo;

import com.example.demo.Form.Form;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ServerFiles {
    private String windowsPath = "C:\\Users\\Yoba\\Desktop\\свидетельства\\";

    public void getFilesArray(HttpServletRequest request, Map<String, ByteArrayOutputStream> fileArray) throws IOException {
        long size = 0;
        try (InputStream ins = request.getInputStream();
             DataInputStream dins = new DataInputStream(ins);
        ) {
            size = dins.readLong();
            System.out.println(size);
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    byte[] bts = new byte[8192];
                    String filename = dins.readUTF();
                    long filesize = dins.readLong();
                    int count = 0;
                    ByteArrayOutputStream outA = new ByteArrayOutputStream();
                    while ((count = ins.read(bts, 0, (int) Math.min(filesize, bts.length))) != -1 && filesize > 0) {
                        outA.write(bts, 0, count);
                        filesize -= count;
                    }
                    fileArray.put(filename, outA);
                }
            }
        }
    }


    public void createZipSertificate(Map<String, ByteArrayOutputStream> somList) throws IOException {
        String pathname = this.windowsPath + "zp.zip";
        try (
                ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(pathname))) {
            somList.forEach((e, z) -> {
                ZipEntry zEntry = new ZipEntry(e);
                try {
                    zip.putNextEntry(zEntry);
                    ByteArrayInputStream iBts = new ByteArrayInputStream(z.toByteArray());
                    int count = 0;
                    byte[] bts = new byte[8192];
                    while ((count = iBts.read(bts)) != -1) {
                        zip.write(bts, 0, count);
                    }
                    iBts.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            zip.closeEntry();
        }
    }

    public void createZipCalcReport(Map<String,ByteArrayOutputStream> fileStengthCalc) throws IOException {
        String pathname = this.windowsPath + "zp1.zip";
        try (ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(pathname))) {
            fileStengthCalc.forEach((e, z) -> {
                ZipEntry zEntry = new ZipEntry(e);
                try {
                    zip.putNextEntry(zEntry);
                    ByteArrayInputStream iBts = new ByteArrayInputStream(z.toByteArray());
                    int count = 0;
                    byte[] bts = new byte[8192];
                    while ((count = iBts.read(bts)) != -1) {
                        zip.write(bts, 0, count);
                    }
                    iBts.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            zip.closeEntry();
        }
    }

    public String doUpload(HttpServletRequest request, Model model, Form form,Map<String,ByteArrayOutputStream> fileArray) {
        MultipartFile[] fileDatas = form.getFileDatas();
        List<String> uploadedFiles = new ArrayList<>();
        List<String> failedFiles = new ArrayList<>();

        for (MultipartFile fileData : fileDatas) {
            String name = fileData.getOriginalFilename();

            if (name != null && name.length() > 0) {
                try {
                    ByteArrayOutputStream oStream=new ByteArrayOutputStream();
                    oStream.write(fileData.getBytes());
                    fileArray.put(name,oStream);
                    oStream.close();
                    uploadedFiles.add(name);
                    System.out.println("Write file: " + name);
                } catch (Exception e) {
                    System.out.println("Error Write file: " + name);
                    failedFiles.add(name);
                }
            }
        }

        // model.addAttribute("description", description);
        model.addAttribute("uploadedFiles", uploadedFiles);
        model.addAttribute("failedFiles", failedFiles);
        return "uploadResult";
    }
public void uploadResultSertificate(HttpServletResponse response,Map<String,ByteArrayOutputStream> somList) throws IOException {
    String pathname = this.windowsPath + "zp.zip";
    File tst = new File(pathname);
    if (Files.exists(tst.toPath()) && tst.length() > 0) {
        response.setContentType("application/zip");
        response.setHeader("Content-disposition", "attachment; filename=" + tst.getName());
        MultipartFile mFile = new MockMultipartFile("SOM.zip", new FileInputStream(tst));
        try (OutputStream out = response.getOutputStream();
             InputStream fis = mFile.getInputStream()) {
            int count = 0;
            while ((count = fis.read()) != -1) {
                out.write(count);
            }
        }
    }
   somList.clear();
}

public void uploadStrengthTestInformation(HttpServletResponse response,Map<String,ByteArrayOutputStream> fileStengthCalc) throws IOException {
    //String pathname="/home/yoba/������� ����/testResult/zp.zip";
    String pathname=this.windowsPath+"zp1.zip";
    File tst=new File(pathname);
    if (Files.exists(tst.toPath())&&tst.length()>0){
        response.setContentType("application/zip");
        response.setHeader("Content-disposition", "attachment; filename="+tst.getName());
        MultipartFile mFile=new MockMultipartFile("zp1.zip",new FileInputStream(tst));
        try(OutputStream out=response.getOutputStream();
            InputStream fis=mFile.getInputStream())
        {
            int count=0;
            while((count=fis.read())!=-1){
                out.write(count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    fileStengthCalc.clear();
}

public void upploadResultPaspInfo(HttpServletResponse response) throws IOException {

    File tst=new File(this.windowsPath+"testres.xlsx");

    if (Files.exists(tst.toPath())&&tst.length()>0){
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment; filename="+tst.getName());
        MultipartFile mFile=new MockMultipartFile("result.xlsx",new FileInputStream(tst));
        try(OutputStream out=response.getOutputStream();
            InputStream fis=mFile.getInputStream()){
            int count=0;
            while((count=fis.read())!=-1){
                out.write(count);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

public void uploadOBREresult(HttpServletResponse response) throws IOException {

    File tst=new File(this.windowsPath+"12.docx");

    if (Files.exists(tst.toPath())&&tst.length()>0){
        response.setContentType("application/msword");
        response.setHeader("Content-disposition", "attachment; filename="+tst.getName());
        MultipartFile mFile=new MockMultipartFile("result.docx",new FileInputStream(tst));
        try( OutputStream out=response.getOutputStream();
             InputStream fis=mFile.getInputStream())
        {
            int count=0;
            while((count=fis.read())!=-1){
                out.write(count);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
}
