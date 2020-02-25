package com.example.demo.MyController;
import com.example.demo.Form.Form;
import com.example.demo.OBRE.CreateOBRE;
import com.example.demo.Part45.GetInfoPart45;
import com.example.demo.PaspAddDoc.GetInfoFSVOM;
import com.example.demo.ServerFiles;
import com.example.demo.pasports.GetInfoPasp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.Files;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Controller
public class MyController {
    private Map<String,ByteArrayOutputStream> fileArray=new HashMap<>();
    private Map<String,ByteArrayOutputStream> somList=new ConcurrentHashMap<>();
    private Map<String,ByteArrayOutputStream> fileStengthCalc=new ConcurrentHashMap<>();
    private String windowsPath="C:\\Users\\Yoba\\Desktop\\свидетельства\\";
    @Inject GetInfoPasp newTable;
    @Inject  ServerFiles files;
    @Inject CreateOBRE OBRE;

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

    @RequestMapping(value = "/OBRE", method = RequestMethod.GET)
    public String oBRECreate(HttpServletRequest request) throws IOException, InterruptedException {
        this.OBRE.fillWordFile(this.fileArray);
        fileArray.clear();
        return "calcTime";
    }

    @RequestMapping(value = "/uploadFilesBystream", method = RequestMethod.POST)
    public String getFilesByStream(HttpServletRequest request) throws IOException{
        System.out.println(request.getAttribute("ADMIN"));
        files.getFilesArray(request,this.fileArray);
        return "calcTime";
    }

    @RequestMapping(value = "/res", method = RequestMethod.GET)
    public String getResTHML(HttpServletRequest request) {
        return "downloadAllResult";
    }

    @RequestMapping(value = "/calculate", method = RequestMethod.GET)
    public String calculate(HttpServletRequest request,HttpSession ses) throws IOException{
        if (this.httpSession.getId().equals(ses.getId())) {
            System.out.println(fileArray.size());
            this.newTable.getTable(this.fileArray);
            this.fileArray.clear();
        }
        return "calcTime";
    }

    @RequestMapping(value = "/createSOM", method = RequestMethod.GET)
    public String createSOM(HttpServletRequest request) throws IOException{
        GetInfoFSVOM newZipArchive=new GetInfoFSVOM();
        newZipArchive.CreateTable(this.fileArray,this.somList);
        files.createZipSertificate(this.somList);
        this.fileArray.clear();
        return "calcTime";
    }



    @RequestMapping(value = "/StrengthTest", method = RequestMethod.GET)
    public String createStrength(HttpServletRequest request) throws IOException{
        GetInfoPart45 newInfoWb=new GetInfoPart45();
        newInfoWb.getInfo(this.fileArray,this.fileStengthCalc);
        files.createZipCalcReport(this.fileStengthCalc);
        this.fileArray.clear();
        return "calcTime";
    }

    @RequestMapping(value = "/GetSom", method = RequestMethod.GET)
    public void getSom (HttpServletResponse response) throws IOException{
        files.uploadResultSertificate(response,this.somList);
    }

    @RequestMapping(value = "/getStr", method = RequestMethod.GET)
    public void getStr (HttpServletResponse response) throws IOException{
    files.uploadStrengthTestInformation(response,fileStengthCalc);
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
        files.upploadResultPaspInfo(response);
    }

    @RequestMapping (value="/downloadOBRE",method=RequestMethod.GET)
    public void oBRE( HttpServletResponse response) throws IOException {
files.uploadOBREresult(response);
    }

    @RequestMapping(value = "/uploadMultiFile", method = RequestMethod.POST)
    public String uploadMultiFileHandlerPOST(HttpServletRequest request, Model model,@ModelAttribute("form") Form form) {
        System.out.println(request.getSession().getId());
        return files.doUpload(request, model, form,this.fileArray);
    }


}

