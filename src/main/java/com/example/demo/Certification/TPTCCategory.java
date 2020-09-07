package com.example.demo.Certification;

import com.example.demo.Form.TPTCForm;
import com.example.demo.Pasport.TPTC032;

public class TPTCCategory implements TPTC032 {

    private String category;

    public TPTCCategory(TPTCForm form){
        String steelType = new AlloyInformation().getSteelType(form.getSteel());
        category=this.calcCategory(form.getAggrState(),form.getOperPress(),form.getDesTemp(),
                form.getMaxDn());
    }

    public String getCategory() {
        return category;
    }

}
