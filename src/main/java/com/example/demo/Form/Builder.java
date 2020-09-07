package com.example.demo.Form;

import com.example.demo.Form.RDForm.PipeForm;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

@Component
public class Builder {

    @ModelAttribute("pipe")
    public PipeForm test(){
        return new PipeForm();
    }
}
