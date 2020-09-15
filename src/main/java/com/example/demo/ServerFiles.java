package com.example.demo;


import com.example.demo.Form.Form;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ServerFiles {
    private final String windowsPath = "/home/yoba/Рабочий стол/testResult/";
    //private String windowsPath="C:\\Users\\Yoba\\Desktop\\sv-va\\";

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
                    int count;
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

    public String doUpload(Model model, Form form, Map<String, ByteArrayOutputStream> fileArray) {
        MultipartFile[] fileDatas = form.getFileDatas();
        List<String> uploadedFiles = new ArrayList<>();
        List<String> failedFiles = new ArrayList<>();

        for (MultipartFile fileData : fileDatas) {
            String name = fileData.getOriginalFilename();

            if (name != null && name.length() > 0) {
                try {
                    ByteArrayOutputStream oStream = new ByteArrayOutputStream();
                    oStream.write(fileData.getBytes());
                    fileArray.put(name, oStream);
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

    public void testZu(Map<String, ByteArrayOutputStream> res) throws IOException {
        String pathname = this.windowsPath + "zp2.zip";
        try (ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(pathname))) {
            res.forEach((e, z) -> {
                ZipEntry zEntry = new ZipEntry(e);
                try {
                    zip.putNextEntry(zEntry);
                    ByteArrayInputStream iBts = new ByteArrayInputStream(z.toByteArray());
                    int count;
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

    public void uploadZu(HttpServletResponse response) throws IOException {
        String pathname = this.windowsPath + "zp2.zip";
        File tst = new File(pathname);
        if (Files.exists(tst.toPath()) && tst.length() > 0) {
            response.setContentType("application/zip");
            response.setHeader("Content-disposition", "attachment; filename=" + tst.getName());
            MultipartFile mFile = new MockMultipartFile("zp2.zip", new FileInputStream(tst));
            try (OutputStream out = response.getOutputStream();
                 InputStream fis = mFile.getInputStream()) {
                int count;
                while ((count = fis.read()) != -1) {
                    out.write(count);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void uploadResultFile(Map<String, ByteArrayOutputStream> resultFiles, String fileName) {


            try (OutputStream out = new FileOutputStream(this.windowsPath+fileName);
                 InputStream fis = new ByteArrayInputStream(resultFiles.get(fileName).toByteArray())) {
                int count;
                while ((count = fis.read()) != -1) {
                    out.write(count);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public void uploadResultDocsZip(HttpServletResponse response, Map<String, ByteArrayOutputStream> resultFiles, String fileNameResult, String fileType) throws IOException {

       try(
        OutputStream responseOutStream =response.getOutputStream();
        ZipOutputStream zip = new ZipOutputStream(responseOutStream)) {
           response.setContentType(fileType);
           response.setHeader("Content-disposition", "attachment; filename=" + fileNameResult);

           {
               resultFiles.forEach((e, z) -> {
                   ZipEntry zEntry = new ZipEntry(e);
                   try {
                       zip.putNextEntry(zEntry);
                       ByteArrayInputStream iBts = new ByteArrayInputStream(z.toByteArray());
                       int count;
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
    }



    public void writeToServer (Map<String, ByteArrayOutputStream> res,String fileName) throws IOException {

        String pathname = this.windowsPath + fileName;
        try (ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(pathname))) {
            res.forEach((e, z) -> {
                ZipEntry zEntry = new ZipEntry(e);
                try {
                    zip.putNextEntry(zEntry);
                    ByteArrayInputStream iBts = new ByteArrayInputStream(z.toByteArray());
                    int count;
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

    public void uploadToClient(OutputStream out,String fileName,String fileType) throws IOException {
        String pathname = this.windowsPath + fileName;
        File tst = new File(pathname);
        if (Files.exists(tst.toPath()) && tst.length() > 0) {
            MultipartFile mFile = new MockMultipartFile(fileName, new FileInputStream(tst));
            try (InputStream fis = mFile.getInputStream()) {
                int count;
                while ((count = fis.read()) != -1) {
                    out.write(count);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}