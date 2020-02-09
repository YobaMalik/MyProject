package com.example.demo.MyController;
import com.example.demo.Form.Form;
import com.example.demo.PaspAddDoc.GetInfoFSVOM;
import com.example.demo.pasports.GetInfoPasp;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.Files;
import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Controller
public class MyController /*implements WebMvcConfigurer */{
    private HashMap<String,ByteArrayOutputStream> FileArray=new HashMap<>();
    private ConcurrentHashMap<String,ByteArrayOutputStream> SOMList=new ConcurrentHashMap<>();

    @Autowired
    private HttpSession httpSession;


    @RequestMapping(value = "/")
    public String homePage(HttpSession session, Principal principal) {
        String name = principal.getName();
        this.httpSession.setAttribute("user",name);
        return "index";
    }

    @RequestMapping(value = "/up", method = RequestMethod.POST)
    public String test123(HttpServletRequest request) throws IOException, InterruptedException {
        System.out.println(new DataInputStream(request.getInputStream()).readUTF());
        return "index";
    }

    @RequestMapping(value = "/uploadFilesBystream", method = RequestMethod.POST)
    public String getFilesByStream(HttpServletRequest request) throws IOException, ParseException, InvalidFormatException {
        System.out.println(request.getAttribute("ADMIN"));
        this.getFilesArray(request);
        return "calcTime";
    }
    @RequestMapping(value = "/calculate", method = RequestMethod.GET)
    public String Calculate(HttpServletRequest request,HttpSession ses) throws IOException, ParseException, InvalidFormatException {
        if (this.httpSession.getId()==ses.getId()) {
            GetInfoPasp tst = new GetInfoPasp();
            System.out.println(FileArray.size());
            tst.run(this.FileArray);
            System.gc();
            this.FileArray.clear();
        }
        return "calcTime";
    }

    @RequestMapping(value = "/createSOM", method = RequestMethod.GET)
    public String CreateSOM(HttpServletRequest request) throws IOException, ParseException, InvalidFormatException {
        GetInfoFSVOM tst=new GetInfoFSVOM();
        System.out.println(FileArray.size());
        tst.runS(this.FileArray,this.SOMList);
        System.gc();
        System.out.println(this.SOMList.size());
        this.FileArray.clear();
        return "calcTime";
    }

    @RequestMapping(value = "/GetSom", method = RequestMethod.GET)
    public void GetSom (HttpServletResponse response) throws IOException{
        //String pathname="/home/yoba/Рабочий стол/testResult/zp.zip";
        String pathname="C:\\Users\\Yoba\\Desktop\\свидетельства\\zp.zip";
        ZipOutputStream zip=new ZipOutputStream(new FileOutputStream(pathname));
        this.SOMList.forEach((e,z)->{

            ZipEntry zEntry=new ZipEntry(e);
            try {
                zip.putNextEntry(zEntry);
                ByteArrayInputStream iBts=new ByteArrayInputStream(z.toByteArray());
                int count=0;
                byte[] bts=new byte[8192];
                while ((count=iBts.read(bts))!=-1){
                    zip.write(bts,0,count);
                }
                iBts.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        zip.closeEntry();
        zip.close();

        File tst=new File(pathname);
        if (Files.exists(tst.toPath())&&tst.length()>0){
            response.setContentType("application/zip");
            response.setHeader("Content-disposition", "attachment; filename="+tst.getName());
            MultipartFile mFile=new MockMultipartFile("SOM.zip",new FileInputStream(tst));
            try{
                OutputStream out=response.getOutputStream();
                InputStream fis=mFile.getInputStream();
                int count=0;
                while((count=fis.read())!=-1){
                    out.write(count);
                }
                out.close();
                fis.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        this.SOMList.clear();
    }

    @RequestMapping(value = "/turnoffthemachine", method = RequestMethod.GET)
    public void offserver(HttpServletResponse response) throws IOException {

        Process prc=Runtime.getRuntime().exec(new String[]{"shutdown","now"});
        System.exit(0);

    }

    @RequestMapping(value = "/uploadMultiFile", method = RequestMethod.GET)
    public String uploadMultiFileHandler(Model model) throws Exception {
        Form form = new Form();
        model.addAttribute("form", form);
        return "uploadMultiFile";

    }


    @RequestMapping (value="/downloadresult",method=RequestMethod.GET)
    public void getFile( HttpServletResponse response) throws IOException {

        File tst=new File("C:\\Users\\Yoba\\Desktop\\свидетельства\\testres.xlsx");
     //   File tst=new File("/home/yoba/Рабочий стол/testResult/testres.xlsx");

       if (Files.exists(tst.toPath())&&tst.length()>0){
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment; filename="+tst.getName());
            MultipartFile mFile=new MockMultipartFile("result.xlsx",new FileInputStream(tst));
            try{
                OutputStream out=response.getOutputStream();
                InputStream fis=mFile.getInputStream();
                int count=0;
                while((count=fis.read())!=-1){
                    out.write(count);
                }
                out.close();
                fis.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    @RequestMapping(value = "/uploadMultiFile", method = RequestMethod.POST)
    public String uploadMultiFileHandlerPOST(HttpServletRequest request, Model model,@ModelAttribute("form") Form form) {
        System.out.println(request.getSession().getId());
        return this.doUpload(request, model, form);
    }

    private String doUpload(HttpServletRequest request, Model model, //
                            Form form) {
        MultipartFile[] fileDatas = form.getFileDatas();
        List<String> uploadedFiles = new ArrayList<>();
        List<String> failedFiles = new ArrayList<>();

        for (MultipartFile fileData : fileDatas) {
            String name = fileData.getOriginalFilename();

            if (name != null && name.length() > 0) {
                try {
                    ByteArrayOutputStream oStream=new ByteArrayOutputStream();
                    oStream.write(fileData.getBytes());
                    FileArray.put(name,oStream);
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
    private void getFilesArray(HttpServletRequest request) throws IOException {
        long size = 0;
        File file = null;
        InputStream ins = null;
        DataInputStream dins = null;
        FileOutputStream out = null;
        ByteArrayOutputStream outA = null;
        try {
            ins = request.getInputStream();
            dins = new DataInputStream(ins);
            size = dins.readLong();
            System.out.println(size);
            if (size > 0) {
                for (int i = 0; i < size; i++) {

                    byte[] bts = new byte[8192];
                    String filename = dins.readUTF();
                    long filesize = dins.readLong();
                    int count = 0;
                    //  out = new FileOutputStream(new File("/home/yoba/Рабочий стол/testUpload/" + filename));
                    outA = new ByteArrayOutputStream();
                    while ((count = ins.read(bts, 0, (int) Math.min(filesize, bts.length))) != -1 && filesize > 0) {
                        //  out.write(bts, 0, count);
                        outA.write(bts, 0, count);
                        filesize -= count;
                    }
                    this.FileArray.put(filename, outA);
                }
            }

            outA.close();
            ins.close();
            dins.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ins != null) {
                outA.close();
                ins.close();
                dins.close();
            }
        }
    }

}

