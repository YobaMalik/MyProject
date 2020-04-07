package com.example.demo.Form;
import com.example.demo.Interface.ResultDocs;
import com.example.demo.PaspAddDoc.GetInfoFSVOM;
import org.springframework.web.multipart.MultipartFile;

public class Form {

    private String description;
    private MultipartFile[] fileDatas;
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile[] getFileDatas() {
        return fileDatas;
    }

    public void setFileDatas(MultipartFile[] fileDatas) {
        this.fileDatas = fileDatas;
    }

}