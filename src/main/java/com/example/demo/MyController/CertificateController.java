package com.example.demo.MyController;

import com.example.demo.Certification.TPTCCategory;
import com.example.demo.Form.TPTCForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CertificateController {

    @PostMapping(value="/TPTC032")
    @ResponseBody
    public TPTCCategory getTPTC032(@RequestBody TPTCForm form){
        return new TPTCCategory(form);
    }

}
