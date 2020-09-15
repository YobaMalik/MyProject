package com.example.demo.MyController;
import com.example.demo.Form.Form;
import com.example.demo.Form.TPTCForm;
import com.example.demo.OBRE.CreateOBRE;
import com.example.demo.Part45.GetInfoPart45;
import com.example.demo.PaspAddDoc.GetInfoFSVOM;
import com.example.demo.Pasport.GetInfoPasp;
import com.example.demo.ServerFiles;
import com.example.demo.TestPressure.Abstract.ModelTestPressure;
import com.example.demo.WordReplacePackage.CreateDocsFromTemplateI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
public class MyController {
    private Map<String,ByteArrayOutputStream> fileArray=new HashMap<>();
    ExecutorService newTask = Executors.newFixedThreadPool(8);

    @Inject ServerFiles files;
    @Inject CreateOBRE OBRE;
    @Inject
    CreateDocsFromTemplateI wordFiles;
    @Inject GetInfoPasp newTable;
    @Inject GetInfoPart45 newInfoWb;
    @Inject GetInfoFSVOM newZipArchive;

    @Autowired
    private HttpSession httpSession;


    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String homePage(HttpSession session, Principal principal,Model model) throws IOException {
        model.addAttribute("PressureInfoTest",new ModelTestPressure());
        model.addAttribute("TPTC",new TPTCForm());
        System.out.println(principal);
        return "index";
    }


    @PostMapping(value="/tptc")
    public double testTPTC( TPTCForm form){
        return 1;
    }



   /*
    @RequestMapping(value = "/uploadMultiFile", method = RequestMethod.POST)
    public String uploadMultiFileHandlerPOST(HttpServletRequest request, Model model,@ModelAttribute("form") Form form) {
        return files.doUpload(request, model, form,this.fileArray);
    }
    */
    @RequestMapping(value = "/up", method = RequestMethod.GET)
    public String test123(HttpServletRequest request) throws IOException {
        System.out.println(new DataInputStream(request.getInputStream()).readUTF());
        return "index";
    }

    @RequestMapping(value = "/OBRE", method = RequestMethod.GET)
    public String oBRECreate(HttpServletRequest request) throws IOException {
        Map<String,ByteArrayOutputStream> resultList=new ConcurrentHashMap<>();
        this.OBRE.createDocs(this.fileArray,resultList,this.newTask);
        this.files.uploadResultFile(resultList,"Template.docx");
        this.fileArray.clear();
        return "calcTime";
    }

    @RequestMapping(value = "/wtfGet", method = RequestMethod.GET)
    public void wtf(HttpServletResponse response) throws IOException {
        response.setContentType("application/zip");
        response.setHeader("Content-disposition", "attachment; filename=" + "WordTemplate.zip");
        this.files.uploadToClient(response.getOutputStream(),"WordTemplate.zip","application/zip");
     //   return "calcTime";
    }

    @RequestMapping(value = "/wtf", method = RequestMethod.GET)
    public String uploadZu(HttpServletResponse request,Principal principal) throws IOException {
        Map<String,ByteArrayOutputStream> resultDocs=new ConcurrentHashMap<>();
        this.wordFiles.createDocs(this.fileArray,resultDocs,this.newTask);
        this.files.writeToServer(resultDocs,"WordTemplate.zip");
        this.fileArray.clear();

        return "calcTime";
    }


    @RequestMapping(value = "/uploadFilesBystream", method = RequestMethod.POST)
    public String getFilesByStream(HttpServletRequest request) throws IOException{
        files.getFilesArray(request,this.fileArray);
        return "calcTime";
    }

    @RequestMapping(value = "/res", method = RequestMethod.GET)
    @Secured("ROLE_ADMIN")
    public String getResTHML(HttpServletRequest request) {
        return "downloadAllResult";
    }

    @RequestMapping(value = "/calculate", method = RequestMethod.GET)
    public String calculate(HttpServletRequest request,HttpSession ses) throws IOException{
        if (this.httpSession.getId().equals(ses.getId())) {
            Map<String,ByteArrayOutputStream> resultList=new ConcurrentHashMap<>();
            this.newTable.createDocs(this.fileArray,resultList,this.newTask);
            this.files.uploadResultFile(resultList,"ResultTable.xlsx");
            this.fileArray.clear();
        }
        return "calcTime";
    }

    @RequestMapping(value = "/createSOM", method = RequestMethod.GET)
    public String createSOM(HttpServletRequest request) throws IOException{
        Map<String,ByteArrayOutputStream> resMap=new ConcurrentHashMap<>();
        this.newZipArchive.createDocs(this.fileArray,resMap,this.newTask);
        this.files.writeToServer(resMap,"zipSomReposrt.zip");
        this.fileArray.clear();
        return "calcTime";
    }

    @RequestMapping(value = "/GetSom", method = RequestMethod.GET)
    public void getSom (HttpServletResponse response) throws IOException{
        response.setContentType("application/zip");
        response.setHeader("Content-disposition", "attachment; filename=" + "zipSomReposrt.zip");
        files.uploadToClient(response.getOutputStream(),"zipSomReposrt.zip","application/zip");
    }

    @RequestMapping(value = "/clearArray", method = RequestMethod.GET)
    public void deleteUploadFile(HttpServletRequest request) {
        this.fileArray.clear();
    }


    @RequestMapping(value = "/StrengthTest", method = RequestMethod.GET)
    public String createStrength(HttpServletRequest request) throws IOException{
        Map<String,ByteArrayOutputStream> fileStengthCalc=new ConcurrentHashMap<>();
        this.newInfoWb.getInfo(this.fileArray,fileStengthCalc);
        files.writeToServer(fileStengthCalc,"MaterialInfo.zip");
        this.fileArray.clear();
        return "calcTime";
    }



    @RequestMapping(value = "/getStr", method = RequestMethod.GET)
    public void getStr (HttpServletResponse response) throws IOException{
        response.setContentType("application/zip");
        response.setHeader("Content-disposition", "attachment; filename=" + "MaterialInfo.zip");
        files.uploadToClient(response.getOutputStream(),"MaterialInfo.zip","application/zip");
    }
    @RequestMapping(value = "/turnoffthemachine", method = RequestMethod.GET)
    public void offserver(HttpServletResponse response) throws IOException {
        Process prc=Runtime.getRuntime().exec(new String[]{"shutdown","now"});
        System.exit(0);
    }

    @RequestMapping(value = "/uploadMultiFile", method = RequestMethod.GET)
    @Secured("ROLE_ADMIN")
    public String uploadMultiFileHandler(Model model) throws Exception {
        Form form = new Form();
        model.addAttribute("form", form);
        return "uploadMultiFile";
    }

    @RequestMapping (value="/downloadresult",method=RequestMethod.GET)
    public void getFile( HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=" + "ResultTable.xlsx");
        files.uploadToClient(response.getOutputStream(),"ResultTable.xlsx","application/vnd.ms-excel");
      //  files.upploadResultPaspInfo(response);
    }

    @RequestMapping (value="/downloadOBRE",method=RequestMethod.GET)
    public void oBRE( HttpServletResponse response) throws IOException {
//        files.uploadOBREresult(response);
        response.setContentType("application/msword");
        response.setHeader("Content-disposition", "attachment; filename=" + "Template.docx");
        files.uploadToClient(response.getOutputStream(), "Template.docx","application/msword");
    }

    @RequestMapping(value = "/uploadMultiFile", method = RequestMethod.POST)
    public String uploadMultiFileHandlerPOST(HttpServletRequest request, Model model,@ModelAttribute("form") Form form) {
        return files.doUpload( model, form,this.fileArray);
    }


}

