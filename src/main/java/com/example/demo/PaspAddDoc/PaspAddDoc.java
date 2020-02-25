package com.example.demo.PaspAddDoc;

import com.example.demo.pasports.CategoryCalc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PaspAddDoc extends  CategoryCalc{
    private SvOmont certificate=new SvOmont();
    public void testsom(String example, Map<String, ByteArrayOutputStream> somList) throws IOException {
        certificate.setDesingPress(this.getDesignTemp());
        certificate.setLinelist(this.getPipeline());
        certificate.setDesingTemp(this.getDesignTemp());
        certificate.setFluidCode(this.getFluidcode());
        certificate.setPipingName(this.getpipename());
        certificate.setDWGlist(this.getDWGs());
        certificate.setKatGOST(this.getgroupGOST()+" "+this.getkatGOST());
        certificate.setWeldIfno(this.getweldInfo());
        certificate.setNameTit(this.getnameTitul());
        certificate.setHeatTreatment(this.getheatTreatment());
        certificate.createSOM(example,somList);
    }
}
